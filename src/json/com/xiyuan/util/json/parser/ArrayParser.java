package com.xiyuan.util.json.parser;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.xiyuan.util.json.JsonParser;
import com.xiyuan.util.json.Jsons;
import com.xiyuan.util.json.Token;

/***
 * 数组解析器,8种基本类型数组、字符串数组和对象数组
 *
 * @version v1.0.0 @author lgz 2019-8-28 新建与整理
 */
public final class ArrayParser extends JsonParser implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public String toString(Object obj)
    {
        if (obj == null)
            return null;
        
        Object[] arr = toArray(obj);
        StringBuilder sb = new StringBuilder().append(Jsons.BRACKET_L);
        for (int i = 0; i < arr.length; i++)
        {
            if (i > 0)
                sb.append(Jsons.COMMA);
            
            Object o = arr[i];
            sb.append(o == null ? Jsons.NULL : Jsons.getParser(o.getClass()).toString(o));
        }
        sb.append(Jsons.BRACKET_R);
        return sb.toString();
    }
    
    public static boolean[] fromJsonBoolean(Token[] values, String json)
    {
        
        boolean[] val = new boolean[values.length];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.booleanValue(values[i], json);
        return val;
    }
    
    public static Boolean[] fromJsonBooleanObj(Token[] values, String json)
    {
        Boolean[] val = new Boolean[values.length];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.booleanValue(values[i], json) ? Boolean.TRUE : Boolean.FALSE;
        return val;
    }
    
    public static byte[] fromJsonByte(Token[] values, String json)
    {
        byte[] val = new byte[values.length];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.byteValue(values[i], json);
        
        return val;
    }
    
    public static Byte[] fromJsonByteObj(Token[] values, String json)
    {
        Byte[] val = new Byte[values.length];
        for (int i = 0; i < val.length; i++)
            val[i] = Byte.valueOf(BaseParser.byteValue(values[i], json));
        
        return val;
    }
    
    public static char[] fromJsonChar(Token[] values, String json)
    {
        char[] val = new char[values.length];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.charValue(values[i], json);
        
        return val;
    }
    
    public static Character[] fromJsonCharObj(Token[] values, String json)
    {
        Character[] val = new Character[values.length];
        for (int i = 0; i < val.length; i++)
            val[i] = Character.valueOf(BaseParser.charValue(values[i],json));
        
        return val;
    }
    
    public static short[] fromJsonShort(Token[] values, String json)
    {
        short[] val = new short[values.length];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.shortValue(values[i], json);
        return val;
    }
    
    public static Short[] fromJsonShortObj(Token[] values, String json)
    {
        Short[] val = new Short[values.length];
        for (int i = 0; i < val.length; i++)
            val[i] = Short.valueOf(BaseParser.shortValue(values[i], json));
        return val;
    }
    
    public static int[] fromJsonInt(Token[] values, String json)
    {
        int[] val = new int[values.length];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.intValue(values[i], json);
        return val;
    }
    
    public static Integer[] fromJsonIntObj(Token[] values, String json)
    {
        Integer[] val = new Integer[values.length];
        for (int i = 0; i < val.length; i++)
            val[i] = Integer.valueOf(BaseParser.intValue(values[i], json));
        return val;
    }
    
    public static long[] fromJsonLong(Token[] values, String json)
    {
        long[] val = new long[values.length];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.longValue(values[i], json);
        
        return val;
    }
    
    public static Long[] fromJsonLongObj(Token[] values, String json)
    {
        Long[] val = new Long[values.length];
        for (int i = 0; i < val.length; i++)
            val[i] = Long.valueOf(BaseParser.longValue(values[i], json));
        return val;
    }
    
    public static float[] fromJsonFloat(Token[] values, String json)
    {
        float[] val = new float[values.length];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.floatValue(values[i], json);
        
        return val;
    }
    
    public static Float[] fromJsonFloatObj(Token[] values, String json)
    {
        Float[] val = new Float[values.length];
        for (int i = 0; i < val.length; i++)
            val[i] = Float.valueOf(BaseParser.floatValue(values[i], json));
        
        return val;
    }
    
    public static double[] fromJsonDouble(Token[] values, String json)
    {
        double[] val = new double[values.length];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.doubleValue(values[i], json);
        
        return val;
    }
    
    public static Double[] fromJsonDoubleObj(Token[] values, String json)
    {
        Double[] val = new Double[values.length];
        for (int i = 0; i < val.length; i++)
            val[i] = Double.valueOf(BaseParser.doubleValue(values[i], json));
        
        return val;
    }
    
    public static String[] fromJsonString(Token[] values, String json)
    {
        String[] val = new String[values.length];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.stringValue(values[i], json);
        return val;
    }
    
    @Override
    public Object toObject(String json, Token token, Class<?> cls)
    {
        if (isPrimitiveArray(cls) || isPrimitiveObjArray(cls))
        {// 基础类型 和 String型
            Token[] values = (token == null || token.type() != Token.BRACKET_L) ? new Token[0] : token.getStringElements();
            
            if (cls == boolean[].class || cls == Boolean[].class)
                return fromJsonBooleanObj(values, json);
            else if (cls == byte[].class || cls == Byte[].class)
                return fromJsonByteObj(values, json);
            else if (cls == char[].class || cls == Character[].class)
                return fromJsonCharObj(values, json);
            else if (cls == int[].class || cls == Integer[].class)
                return fromJsonIntObj(values, json);
            else if (cls == long[].class || cls == Long[].class)
                return fromJsonLongObj(values, json);
            else if (cls == short[].class ||cls == Short[].class)
                return fromJsonShortObj(values, json);
            else if (cls == float[].class || cls == Float[].class)
                return fromJsonFloatObj(values, json);
            else if (cls == double[].class || cls == Double[].class)
                return fromJsonDoubleObj(values, json);
            
            return null;
        }
        else if (cls == String[].class)
        {
            Token[] values = (token == null || token.type() != Token.BRACKET_L) ? new Token[0] : token.getElements(Token.COMMA);
            return fromJsonString(values, json);
        }
        else
        {
            Class<?> type = cls.getComponentType();
            if (token == null || token.type() != Token.BRACKET_L)
                return (Object[]) Array.newInstance(type, 0);// 不是 数组类型
                
            Token[] values = token.getElements(Token.COMMA);// 过滤逗号token
            Object[] objs = (Object[]) Array.newInstance(type, values.length);
            for (int i = 0; i < objs.length; i++)
                objs[i] = Jsons.getParser(type).toObject(json, values[i], type);
            
            return objs;
        }
    }
}
