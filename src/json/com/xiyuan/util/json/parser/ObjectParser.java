package com.xiyuan.util.json.parser;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.xiyuan.util.json.JsonLexer;
import com.xiyuan.util.json.JsonParser;

/**
 * 对象适配器，不包括基本类型、数组类型
 * 
 * @version v1.0.0 @author lgz 2019年8月31日 新建与整理
 */
public final class ObjectParser extends JsonParser implements Serializable
{
    private static final long serialVersionUID = 1L;

    public ObjectParser(JsonLexer lexer)
    {
        super(lexer);
    }

    public String toString(Object obj)
    {
        if (obj == null)
            return null;

        List<Field> fields = getFieldListDeep(obj.getClass());
        // 假设每个字段为6字符+4个引号+1冒号+4(null)，则str默认length = fields.size()*(6+4+1+4) =
        // fields.size()*(15)
        StringBuilder sb = new StringBuilder(fields.size() * 15).append(JsonLexer.BRACE_L);
        Object value = null;
        for (Field field : fields)
        {
            // TODO 以后使用注解获字段取别名，Annotations.getFieldName(field);
            String name = field.getName();
            try
            {
                value = field.get(obj);
            }
            catch (Exception e)
            {}

            sb.append(JsonLexer.DB_QUOTE).append(name).append(JsonLexer.DB_QUOTE);
            sb.append(JsonLexer.COLON);
            sb.append(value == null ? JsonLexer.NULL : lexer.getParser(value.getClass()).toString(value)).append(JsonLexer.COMMA);
        }

        if (sb.length() > 1)
            sb.setLength(sb.length() - 1);

        sb.append(JsonLexer.BRACE_R);
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object toObject(Class<?> cls)
    {

        Object obj = null;

        obj = newInstance(cls);
        if (obj == null || !lexer.isObj())
            return null;
        boolean isValue = false;
        Field key = null;
        Class<?> valueType;
        Map<String, Field> filedMap = getFieldMapDeep(cls);
        int scope = lexer.scope();
        filedMap = getFieldMapDeep(cls);

        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符

            if (lexer.isColon() || lexer.isComma())
            {
                if (lexer.isColon() && key != null)
                    isValue = true;//已经找到key 当前是冒号，说明下一个token为值
                continue;// 冒号或者逗号跳过
            }

            if (key != null && isValue)
            {
                valueType = key.getType();
                JsonParser parser = lexer.getParser(valueType);
                if (parser == lexer.ListParser())
                    setValue(obj, key, ((ListParser) lexer.ListParser()).toObject(valueType, JsonLexer.getClass(key.getGenericType(), 0)));
                else if (parser == lexer.MapParser())
                    setValue(obj, key, ((MapParser) lexer.MapParser()).toObject(valueType, JsonLexer.getClass(key.getGenericType(), 0), JsonLexer.getClass(key.getGenericType(), 1)));
                else
                    setValue(obj, key, parser.toObject(valueType));
                key = null;
            }
            else
                key = filedMap.get(JsonLexer.removeStartEndQuotation(lexer.value()));
            
            isValue = false;
        }

        if (key != null)
            setValue(obj, key, null);

        key = null;
        isValue = false;
        return obj;
    }

    private void setValue(Object obj, Field key, Object value)
    {
        if (obj == null)
            return;

        try
        {
            if (value != null && value instanceof String && JsonLexer.NULL.equals(value))
                value = null;
            key.set(obj, value);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }
    }
}
