
package com.xiyuan.util.json.parser;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiyuan.util.json.JsonParser;
import com.xiyuan.util.json.JsonUtil;
import com.xiyuan.util.json.Token;

/**
 * 对象适配器，不包括基本类型、数组类型
 *
 * @version v1.0.0 @author lgz 2019年8月31日 新建与整理
 */
public final class ObjectParser extends JsonParser implements Serializable
{
    private static final long serialVersionUID = 1L;

    public String toString(Object obj)
    {
        if (obj == null)
            return null;
        
        StringBuilder sb = new StringBuilder().append(JsonUtil.BRACE_L);
        Class<?> cls = obj.getClass();
        List<Field> fieldList = new ArrayList<Field>();
        getFieldListDeep(cls, fieldList);
        for (Field field : fieldList)
        {
            String name = field.getName();// TODO 以后使用 注解获字段取别名，Annotations.getFieldName(field);
            Object value = null;
            try
            {
                if (!field.isAccessible())
                    field.setAccessible(true);

                value = field.get(obj);
            }
            catch (Exception e)
            {}
            
            sb.append(JsonUtil.DB_QUOTE).append(name).append(JsonUtil.DB_QUOTE).append(JsonUtil.COLON).append(value == null ? JsonUtil.NULL : JsonUtil.getParser(value.getClass()).toString(value)).append(JsonUtil.COMMA);
        }

        if (sb.length() > 1)
            sb.setLength(sb.length() - 1);

        sb.append(JsonUtil.BRACE_R);
        return sb.toString();
    }

    @Override
    public Object toObject(String json, Token token, Class<?> cls)
    {
        Object obj = newInstance(cls);
        if (obj == null)
            return null;

        // 设置对象
        List<Field> fieldList = new ArrayList<Field>();
        getFieldListDeep(cls, fieldList);
        Map<String, Field> filedMap = new HashMap<String, Field>();
        for (Field field : fieldList)
        {//
            String name = field.getName();// TODO 以后添加注解获取别名 Annotations.getFieldName(field);
            filedMap.put(name, field);
        }
        
        
        Token[] ls = (token == null || token.type() != JsonUtil.T_BRACE_L) ? new Token[0] : token.getElements();
        boolean isValue = false;
        Field field = null;
        for (Token vt : ls)
        {
            if (vt.type() == JsonUtil.T_COMMA)
                continue;

            if (vt.type() == JsonUtil.T_COLON && field != null)
            {
                isValue = true;
                continue;
            }

            if (isValue && field != null)
            {
               
                // JSON转化为对象
                Class<?> type = field.getType();
                Object vo = JsonUtil.getParser(type).toObject(json, vt, type);// jsonMain.toObject(value, type, false);
               
                if (!field.isAccessible())
                    field.setAccessible(true);
                try
                {
                    field.set(obj, vo);
                }
                catch (Exception e)
                {
                    throw new IllegalArgumentException(e);
                }
                
                isValue = false;
                field = null;
            }
            else
            {//尝试获取字段
                String name = JsonUtil.removeStartEndQuotation(vt.toString(json));// 去除引号
                if (filedMap.containsKey(name))
                    field = filedMap.get(name);
            }
        }
        
        return obj;
    }
}
