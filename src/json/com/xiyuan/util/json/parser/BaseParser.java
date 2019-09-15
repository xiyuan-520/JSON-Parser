
package com.xiyuan.util.json.parser;

import java.io.Serializable;

import com.xiyuan.util.json.JsonParser;
import com.xiyuan.util.json.JsonUtil;
import com.xiyuan.util.json.Token;

/***
 * 基本类型适配器，8种基本类型&字符串
 *
 * @version v1.0.0 @author lgz 2019-8-28 新建与整理
 */
public final class BaseParser extends JsonParser implements Serializable
{
    private static final long serialVersionUID = 1L;

    public String toString(Object obj)
    {
        if (obj == null)
            return null;
        
        if (obj instanceof Long)
        {// 对long型进行判断，超过JS支持的最大值时行字符串处理，防止JS数值不准
            long l = (Long) obj;
            if (l > JS_MAX_LONG_VALUE)
                return new StringBuilder().append(JsonUtil.DB_QUOTE).append(l).append(JsonUtil.DB_QUOTE).toString();
        }
        else if (obj instanceof String)
        {// 要对引号进行增加转义
            String str = JsonUtil.addEscapeChar((String) obj, JsonUtil.DB_QUOTE);
            return new StringBuilder().append(JsonUtil.DB_QUOTE).append(str).append(JsonUtil.DB_QUOTE).toString();
        }

        return String.valueOf(obj);
    }

    public static boolean booleanValue(Token token, String json)
    {
        String value = JsonUtil.removeStartEndQuotation((token == null || json == null) ? null : token.toString(json));
        return Boolean.parseBoolean(json) || "1".equals(value) ? true : false;
    }

    public static byte byteValue(Token token, String json)
    {
        String value = JsonUtil.removeStartEndQuotation((token == null || json == null) ? null : token.toString(json));
        return Byte.parseByte(value);
    }

    public static char charValue(Token token, String json)
    {
        String value = JsonUtil.removeStartEndQuotation((token == null || json == null) ? null : token.toString(json));
        return value == null ? (char)0 : value.charAt(0);
    }
    
    public static short shortValue(Token token, String json)
    {
        String value = JsonUtil.removeStartEndQuotation((token == null || json == null) ? null : token.toString(json));
        return Short.parseShort(value);
    }
    
    public static int intValue(Token token, String json)
    {
        String value = JsonUtil.removeStartEndQuotation((token == null || json == null) ? null : token.toString(json));
        return Integer.parseInt(value);
    }
    
    public static long longValue(Token token, String json)
    {
        String value = JsonUtil.removeStartEndQuotation((token == null || json == null) ? null : token.toString(json));
        return Long.parseLong(value);
    }
    
    public static float floatValue(Token token, String json)
    {
        String value = JsonUtil.removeStartEndQuotation((token == null || json == null) ? null : token.toString(json));
        return Float.parseFloat(value);
    }
    
    public static double doubleValue(Token token, String json)
    {
        String value = JsonUtil.removeStartEndQuotation((token == null || json == null) ? null : token.toString(json));
        return Double.parseDouble(value);
    }
    
    public static String stringValue(Token token, String json)
    {
        // 去掉前后可能的引号
        String value = JsonUtil.removeStartEndQuotation((token == null || json == null) ? null : token.toString(json));
        return JsonUtil.removeEscapeChar(value);// 字符串要求删除转义
    }

    @Override
    public Object toObject(String json, Token token, Class<?> cls)
    {
        if (cls == boolean.class || cls == Boolean.class)
            return booleanValue(token, json);
        else if (cls == byte.class || cls == Byte.class)
            return booleanValue(token, json);
        else if (cls == char.class || cls == Character.class)
            return charValue(token, json);
        else if (cls == short.class || cls == Short.class)
            return shortValue(token, json);
        else if (cls == int.class || cls == Integer.class)
            return intValue(token, json);
        else if (cls == long.class || cls == Long.class)
            return longValue(token, json);
        else if (cls == float.class || cls == Float.class)
            return floatValue(token, json);
        else if (cls == double.class || cls == Double.class)
            return doubleValue(token, json);
        else if (cls == String.class)
            return stringValue(token, json);
        return null;
    }
}
