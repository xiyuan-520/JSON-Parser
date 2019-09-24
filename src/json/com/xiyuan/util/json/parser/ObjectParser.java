package com.xiyuan.util.json.parser;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
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

        StringBuilder sb = new StringBuilder().append(JsonLexer.BRACE_L);
        Class<?> cls = obj.getClass();
        List<Field> fieldList = getFieldListDeep(cls);
        for (Field field : fieldList)
        {
            // TODO 以后使用注解获字段取别名，Annotations.getFieldName(field);
            String name = field.getName();
            Object value = null;
            try
            {
                if (!field.isAccessible())
                    field.setAccessible(true);

                value = field.get(obj);
            }
            catch (Exception e)
            {}

            sb.append(JsonLexer.DB_QUOTE).append(name).append(JsonLexer.DB_QUOTE).append(JsonLexer.COLON)
                .append(value == null ? JsonLexer.NULL : lexer.getParser(value.getClass()).toString(value)).append(JsonLexer.COMMA);
        }

        if (sb.length() > 1)
            sb.setLength(sb.length() - 1);

        sb.append(JsonLexer.BRACE_R);
        return sb.toString();
    }

    @Override
    public Object toObject(Class<?> cls)
    {
        Object obj = newInstance(cls);
        if (obj == null)
            return null;

        Map<String, Field> filedMap = getMapFieldDeep(cls);

        Field key = null;
        Object value = null;
        int scope = lexer.scope();
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符

            if (lexer.isColon() || lexer.isComma())
                continue;// 冒号或者逗号跳过

            if (key != null)
            {// TODO:后续半段泛型类
                Class<?> type = key.getType();
                value = lexer.getParser(type).toObject(type);//
                setValue(obj, key, value);
                key = null;
                value = null;
            }
            else
                key = filedMap.get(JsonLexer.removeStartEndQuotation(lexer.value()));

            value = null;
            continue;
        }

        if (key != null)
            setValue(obj, key, null);

        key = null;
        return obj;
    }

    private void setValue(Object obj, Field key, Object value)
    {
        if (!key.isAccessible())
            key.setAccessible(true);

        try
        {
            key.set(obj, value);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }
    }
}
