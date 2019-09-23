package com.xiyuan.util.json.parser;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicLongArray;

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
            String str = Jsons.addEscapeChar((String) obj, JsonLexer.DB_QUOTE);
            return new StringBuilder().append(Jsons.DB_QUOTE).append(str).append(Jsons.DB_QUOTE).toString();
        }
        
        return String.valueOf(obj);
    }
    
    public static boolean booleanValue(JsonLexer lexer)
    {
        String value = Jsons.removeStartEndQuotation(lexer.value());
        return Boolean.parseBoolean(value) ? true : ("1".equals(value) ? true : false);
    }
    
    public static byte byteValue(JsonLexer lexer)
    {
        String value = Jsons.removeStartEndQuotation(lexer.value());
        return Byte.parseByte(!JsonLexer.NULL.equals(value) ? value : (lexer.tokenType() == JsonLexer.T_COMMA ? String.valueOf(JsonLexer.COMMA) : value));
    }
    
    public static char charValue(JsonLexer lexer)
    {
        String value = Jsons.removeStartEndQuotation(lexer.value());
        return value == null ? (char) 0 : value.charAt(0);
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
        if (lexer.tokenType() == JsonLexer.T_BRACE_L || lexer.tokenType() == JsonLexer.T_BRACKET_L)
        {// 对象行
            int scope = lexer.scope();
            int pos = lexer.pos();
            while (lexer.hasNext())
            {
                lexer.naxtToken();
                if (lexer.scope() < scope)
                    break;// 碰到结束符
            }
            return JsonLexer.removeEscapeChar(JsonLexer.removeStartEndQuotation(lexer.string(pos, lexer.pos()+1)));
        }
        else
        {
            // 去除引号&字符串要求删除转义 然后返回
            return JsonLexer.removeEscapeChar(JsonLexer.removeStartEndQuotation((lexer.value() == null) ? null : lexer.value()));
        }
    }
    
    @Override
    public Object toObject(JsonLexer lexer, Class<?> cls)
    {
        if (cls == boolean.class)
            return booleanValue(lexer);
        else if (cls == Boolean.class)
            return Boolean.valueOf(booleanValue(lexer));
        else if (cls == byte.class)
            return byteValue(lexer);
        else if (cls == Byte.class)
            return Byte.valueOf(byteValue(lexer));
        else if (cls == char.class)
            return charValue(lexer);
        else if (cls == Character.class)
            return Character.valueOf(charValue(lexer));
        else if (cls == short.class)
            return shortValue(lexer);
        else if (cls == Short.class)
            return Short.valueOf(shortValue(lexer));
        else if (cls == int.class)
            return intValue(lexer);
        else if (cls == Integer.class)
            return Integer.valueOf(intValue(lexer));
        else if (cls == long.class)
            return longValue(lexer);
        else if (cls == Long.class)
            return Long.valueOf(longValue(lexer));
        else if (cls == float.class)
            return floatValue(lexer);
        else if (cls == Float.class)
            return Float.valueOf(floatValue(lexer));
        else if (cls == double.class)
            return doubleValue(lexer);
        else if (cls == Double.class)
            return Double.valueOf(doubleValue(lexer));
        else if (cls == String.class)
            return stringValue(lexer);
        
        return null;
    }
}
