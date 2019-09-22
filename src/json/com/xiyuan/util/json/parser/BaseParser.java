
package com.xiyuan.util.json.parser;

import java.io.Serializable;

import com.xiyuan.util.json.JsonLexer;
import com.xiyuan.util.json.JsonParser;
import com.xiyuan.util.json.Jsons;

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
                return new StringBuilder().append(Jsons.DB_QUOTE).append(l).append(Jsons.DB_QUOTE).toString();
        }
        else if (obj instanceof String)
        {// 要对引号进行增加转义
            String str = Jsons.addEscapeChar((String)obj, JsonLexer.DB_QUOTE);
            return new StringBuilder().append(Jsons.DB_QUOTE).append(str).append(Jsons.DB_QUOTE).toString();
        }

        return String.valueOf(obj);
    }

    public static boolean booleanValue(JsonLexer lexer)
    {
        String value = Jsons.removeStartEndQuotation(lexer.value());
        return Boolean.parseBoolean(value) || "1".equals(value) ? true : false;
    }

    public static byte byteValue(JsonLexer lexer)
    {
        String value = Jsons.removeStartEndQuotation(lexer.value());
        return Byte.parseByte(!JsonLexer.NULL.equals(value) ? value : (lexer.tokenType() == JsonLexer.T_COMMA ? String.valueOf(JsonLexer.COMMA) : value));
    }

    public static char charValue(JsonLexer lexer)
    {
        String value = Jsons.removeStartEndQuotation(lexer.value());
        return value == null ? (char)0 : value.charAt(0);
    }
    
    public static short shortValue(JsonLexer lexer)
    {
        String value = Jsons.removeStartEndQuotation(lexer.value());
        return Short.parseShort(!JsonLexer.NULL.equals(value) ? value : (lexer.tokenType() == JsonLexer.T_COMMA ? String.valueOf(JsonLexer.COMMA) : value));
    }
    
    public static int intValue(JsonLexer lexer)
    {
        String value = Jsons.removeStartEndQuotation(lexer.value());
        return Integer.parseInt(!JsonLexer.NULL.equals(value) ? value : (lexer.tokenType() == JsonLexer.T_COMMA ? String.valueOf(JsonLexer.COMMA) : value));
    }
    
    public static long longValue(JsonLexer lexer)
    {
        String value = Jsons.removeStartEndQuotation(lexer.value());
        return Long.parseLong(!JsonLexer.NULL.equals(value) ? value : (lexer.tokenType() == JsonLexer.T_COMMA ? String.valueOf(JsonLexer.COMMA) : value));
    }
    
    public static float floatValue(JsonLexer lexer)
    {
        String value = Jsons.removeStartEndQuotation(lexer.value());
        return Float.parseFloat(!JsonLexer.NULL.equals(value) ? value : (lexer.tokenType() == JsonLexer.T_COMMA ? String.valueOf(JsonLexer.COMMA) : value));
    }
    
    public static double doubleValue(JsonLexer lexer)
    {
        String value = Jsons.removeStartEndQuotation((lexer.value() == null) ? null : lexer.value());
        return Double.parseDouble(!JsonLexer.NULL.equals(value) ? value : (lexer.tokenType() == JsonLexer.T_COMMA ? String.valueOf(JsonLexer.COMMA) : value));
    }
    
    public static String stringValue(JsonLexer lexer)
    {
        // 去掉前后可能的引号
        String value = Jsons.removeStartEndQuotation((lexer.value() == null) ? null : lexer.value());
        return Jsons.removeEscapeChar(value);// 字符串要求删除转义
    }

    @Override
    public Object toObject(JsonLexer lexer, Class<?> cls)
    {
        if (cls == boolean.class || cls == Boolean.class)
            return booleanValue(lexer);
        else if (cls == byte.class || cls == Byte.class)
            return booleanValue(lexer);
        else if (cls == char.class || cls == Character.class)
            return charValue(lexer);
        else if (cls == short.class || cls == Short.class)
            return shortValue(lexer);
        else if (cls == int.class || cls == Integer.class)
            return intValue(lexer);
        else if (cls == long.class || cls == Long.class)
            return longValue(lexer);
        else if (cls == float.class || cls == Float.class)
            return floatValue(lexer);
        else if (cls == double.class || cls == Double.class)
            return doubleValue(lexer);
        else if (cls == String.class)
            return stringValue(lexer);
        return null;
    }
}
