package com.xiyuan.util.json.parser;

import java.io.Serializable;

import com.xiyuan.util.json.JsonLexer;
import com.xiyuan.util.json.JsonParser;

/***
 * 基本类型适配器，8种基本类型&字符串
 * 
 * @version v1.0.0 @author lgz 2019-8-28 新建与整理
 */
public final class BaseParser extends JsonParser implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public BaseParser(JsonLexer lexer)
    {
        super(lexer);
    }
    
    public String toString(Object obj)
    {
        if (obj == null)
            return null;
        
        if (obj instanceof Long)
        {// 对long型进行判断，超过JS支持的最大值时行字符串处理，防止JS数值不准
            long l = (Long) obj;
            if (l > JS_MAX_LONG_VALUE)
                return new StringBuilder().append(JsonLexer.DB_QUOTE).append(l).append(JsonLexer.DB_QUOTE).toString();
        }
        else if (obj instanceof String)
        {// 要对引号进行增加转义
            String str = JsonLexer.addEscapeChar((String) obj, JsonLexer.DB_QUOTE);
            return new StringBuilder().append(JsonLexer.DB_QUOTE).append(str).append(JsonLexer.DB_QUOTE).toString();
        }
        
        return String.valueOf(obj);
    }
    
    @Override
    public Object toObject(Class<?> cls)
    {
        
        switch (cls.getName().hashCode())
        {
            case JsonLexer.BOOL_CLS_HASH:
                return booleanValue(lexer);
            case JsonLexer.BYTE_CLS_HASH:
                return byteValue(lexer);
            case JsonLexer.CHAR_CLS_HASH:
                return charValue(lexer);
            case JsonLexer.SHORT_CLS_HASH:
                return shortValue(lexer);
            case JsonLexer.INT_CLS_HASH:
                return intValue(lexer);
            case JsonLexer.LONG_CLS_HASH:
                return longValue(lexer);
            case JsonLexer.FLOAT_CLS_HASH:
                return floatValue(lexer);
            case JsonLexer.DOUBLE_CLS_HASH:
                return doubleValue(lexer);
            case JsonLexer.BOOL_OBJ_CLS_HASH:
                return booleanValue(lexer) ? Boolean.TRUE : Boolean.FALSE;
            case JsonLexer.BYTE_OBJ_CLS_HASH:
                return Byte.valueOf(byteValue(lexer));
            case JsonLexer.CHAR_OBJ_CLS_HASH:
                return Character.valueOf(charValue(lexer));
            case JsonLexer.SHORT_OBJ_CLS_HASH:
                return Short.valueOf(shortValue(lexer));
            case JsonLexer.INT_OBJ_CLS_HASH:
                return Integer.valueOf(intValue(lexer));
            case JsonLexer.LONG_OBJ_CLS_HASH:
                return Long.valueOf(longValue(lexer));
            case JsonLexer.FLOAT_OBJ_CLS_HASH:
                return Float.valueOf(floatValue(lexer));
            case JsonLexer.DOUBLE_OBJ_CLS_HASH:
                return Double.valueOf(doubleValue(lexer));
            case JsonLexer.STRING_CLS_HASH:
                return stringValue(lexer);
            default:
                return null;
        }
    }
    
    public static boolean booleanValue(JsonLexer lexer)
    {
        String value = JsonLexer.removeStartEndQuotation(lexer.value());
        return Boolean.parseBoolean(value) ? true : ("1".equals(value) ? true : false);
    }
    
    public static byte byteValue(JsonLexer lexer)
    {
        return Byte.parseByte((lexer.prevIsColon() && lexer.ch() == JsonLexer.COMMA) ? JsonLexer.COMMA_S : JsonLexer.removeStartEndQuotation(lexer.value()));
    }
    
    public static char charValue(JsonLexer lexer)
    {
        String value = JsonLexer.removeStartEndQuotation(lexer.value());
        return value == null ? (char) 0 : (((lexer.prevIsColon() && lexer.novalue()) ? (char) 0 : JsonLexer.removeStartEndQuotation(lexer.value()).charAt(0)));
    }
    
    public static short shortValue(JsonLexer lexer)
    {
        return Short.parseShort((lexer.prevIsColon() && lexer.novalue()) ? JsonLexer.COMMA_S : JsonLexer.removeStartEndQuotation(lexer.value()));
    }
    
    public static int intValue(JsonLexer lexer)
    {
        return Integer.parseInt((lexer.prevIsColon() && lexer.novalue()) ? JsonLexer.COMMA_S : JsonLexer.removeStartEndQuotation(lexer.value()));
    }
    
    public static long longValue(JsonLexer lexer)
    {
        return Long.parseLong((lexer.prevIsColon() && lexer.novalue()) ? JsonLexer.COMMA_S : JsonLexer.removeStartEndQuotation(lexer.value()));
    }
    
    public static float floatValue(JsonLexer lexer)
    {
        return Float.parseFloat((lexer.prevIsColon() && lexer.novalue()) ? JsonLexer.COMMA_S : JsonLexer.removeStartEndQuotation(lexer.value()));
    }
    
    public static double doubleValue(JsonLexer lexer)
    {
        return Double.parseDouble((lexer.prevIsColon() && lexer.novalue()) ? JsonLexer.COMMA_S : JsonLexer.removeStartEndQuotation((lexer.value() == null) ? null : lexer.value()));
    }
    
    public static String stringValue(JsonLexer lexer)
    {
        // 去掉前后可能的引号
        if (lexer.curType() == JsonLexer.T_BRACE_L || lexer.curType() == JsonLexer.T_BRACKET_L)
        {// 对象行
            int pos = lexer.pos();
            int scope = lexer.scope();
            while (lexer.hasNext())
            {
                lexer.naxtToken();
                if (lexer.scope() < scope || lexer.isEOF())
                    break;// 碰到结束符
            }
            return JsonLexer.removeEscapeChar(JsonLexer.removeStartEndQuotation(lexer.string(pos, lexer.pos() + 1)));
        }
        else
        {
            // 去除引号&字符串要求删除转义 然后返回
            return JsonLexer.removeEscapeChar(JsonLexer.removeStartEndQuotation((lexer.value() == null) ? null : lexer.value()));
        }
    }
    
}
