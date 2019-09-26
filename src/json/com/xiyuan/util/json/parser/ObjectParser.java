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
        //假设每个字段为6字符+4个引号+1冒号+4(null)，则str默认length = fields.size()*(6+4+1+4) = fields.size()*(15)
        StringBuilder sb = new StringBuilder(fields.size()*15).append(JsonLexer.BRACE_L);
        Object value = null;
        for (Field field : fields)
        {
            // TODO 以后使用注解获字段取别名，Annotations.getFieldName(field);
            String name = field.getName();
            try
            {
                if (!field.isAccessible())
                    field.setAccessible(true);

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
   
    @Override
    public Object toObject(Class<?> cls)
    {
        Object obj = null;
        obj = newInstance(cls);
        if (obj == null || !lexer.isObj())
            return null;
        
        Map<String, Field> filedMap = getFieldMapDeep(cls);
        Field key = null;
        Class<?> keytype = null;
        int scope = lexer.scope();
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符

            if (lexer.isColon() || lexer.isComma())
                continue;// 冒号或者逗号跳过

            if (key != null)
            {
                keytype = key.getType();//TODO 这里要获取泛型参数
                setValue(obj, key, lexer.getParser(keytype).toObject(keytype));
                key = null;
            }
            else
                key = filedMap.get(JsonLexer.removeStartEndQuotation(lexer.value()));

            continue;
        }
        
        if (key != null)
            setValue(obj, key, null);

        key = null;
        return obj;
    }

    private void setValue(Object obj, Field key, Object value)
    {
        if (obj == null)
            return;
        
        if (!key.isAccessible())
            key.setAccessible(true);

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
