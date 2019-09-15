package com.xiyuan.util.json.parser;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.xiyuan.util.json.JsonParser;
import com.xiyuan.util.json.JsonUtil;
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
        StringBuilder sb = new StringBuilder().append(JsonUtil.BRACKET_L);
        for (int i = 0; i < arr.length; i++)
        {
            if (i > 0)
                sb.append(JsonUtil.COMMA);
            
            Object o = arr[i];
            sb.append(o == null ? JsonUtil.NULL : JsonUtil.getParser(o.getClass()).toString(o));
        }
        sb.append(JsonUtil.BRACKET_R);
        return sb.toString();
    }
    
    public static boolean[] fromJsonBoolean(List<Token> values, String json)
    {
        
        boolean[] val = new boolean[values.size()];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.booleanValue(values.get(i), json);
        return val;
    }
    
    public static Boolean[] fromJsonBooleanObj(List<Token> values, String json)
    {
        Boolean[] val = new Boolean[values.size()];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.booleanValue(values.get(i), json) ? Boolean.TRUE : Boolean.FALSE;
        return val;
    }
    
    public static byte[] fromJsonByte(List<Token> values, String json)
    {
        byte[] val = new byte[values.size()];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.byteValue(values.get(i), json);
        
        return val;
    }
    
    public static Byte[] fromJsonByteObj(List<Token> values, String json)
    {
        Byte[] val = new Byte[values.size()];
        for (int i = 0; i < val.length; i++)
            val[i] = Byte.valueOf(BaseParser.byteValue(values.get(i), json));
        
        return val;
    }
    
    public static char[] fromJsonChar(List<Token> values, String json)
    {
        char[] val = new char[values.size()];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.charValue(values.get(i), json);
        
        return val;
    }
    
    public static Character[] fromJsonCharObj(List<Token> values, String json)
    {
        Character[] val = new Character[values.size()];
        for (int i = 0; i < val.length; i++)
            val[i] = Character.valueOf(BaseParser.charValue(values.get(i),json));
        
        return val;
    }
    
    public static short[] fromJsonShort(List<Token> values, String json)
    {
        short[] val = new short[values.size()];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.shortValue(values.get(i), json);
        return val;
    }
    
    public static Short[] fromJsonShortObj(List<Token> values, String json)
    {
        Short[] val = new Short[values.size()];
        for (int i = 0; i < val.length; i++)
            val[i] = Short.valueOf(BaseParser.shortValue(values.get(i), json));
        return val;
    }
    
    public static int[] fromJsonInt(List<Token> values, String json)
    {
        int[] val = new int[values.size()];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.intValue(values.get(i), json);
        return val;
    }
    
    public static Integer[] fromJsonIntObj(List<Token> values, String json)
    {
        Integer[] val = new Integer[values.size()];
        for (int i = 0; i < val.length; i++)
            val[i] = Integer.valueOf(BaseParser.intValue(values.get(i), json));
        return val;
    }
    
    public static long[] fromJsonLong(List<Token> values, String json)
    {
        long[] val = new long[values.size()];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.longValue(values.get(i), json);
        
        return val;
    }
    
    public static Long[] fromJsonLongObj(List<Token> values, String json)
    {
        Long[] val = new Long[values.size()];
        for (int i = 0; i < val.length; i++)
            val[i] = Long.valueOf(BaseParser.longValue(values.get(i), json));
        return val;
    }
    
    public static float[] fromJsonFloat(List<Token> values, String json)
    {
        float[] val = new float[values.size()];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.floatValue(values.get(i), json);
        
        return val;
    }
    
    public static Float[] fromJsonFloatObj(List<Token> values, String json)
    {
        Float[] val = new Float[values.size()];
        for (int i = 0; i < val.length; i++)
            val[i] = Float.valueOf(BaseParser.floatValue(values.get(i), json));
        
        return val;
    }
    
    public static double[] fromJsonDouble(List<Token> values, String json)
    {
        double[] val = new double[values.size()];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.doubleValue(values.get(i), json);
        
        return val;
    }
    
    public static Double[] fromJsonDoubleObj(List<Token> values, String json)
    {
        Double[] val = new Double[values.size()];
        for (int i = 0; i < val.length; i++)
            val[i] = Double.valueOf(BaseParser.doubleValue(values.get(i), json));
        
        return val;
    }
    
    public static String[] fromJsonString(List<Token> values, String json)
    {
        String[] val = new String[values.size()];
        for (int i = 0; i < val.length; i++)
            val[i] = BaseParser.stringValue(values.get(i), json);
        return val;
    }
    
    @Override
    public Object toObject(String json, Token token, Class<?> cls)
    {
        if (isPrimitiveArray(cls) || isPrimitiveObjArray(cls))
        {// 基础类型 和 String型
            List<Token> values = (token == null || token.type() != JsonUtil.T_BRACKET_L) ? new ArrayList<Token>() : token.getStringElements();
            
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
            List<Token> values = (token == null || token.type() != JsonUtil.T_BRACKET_L) ? new ArrayList<Token>() : token.getElements(JsonUtil.T_COMMA);
            return fromJsonString(values, json);
        }
        else
        {
            Class<?> type = cls.getComponentType();
            if (token == null || token.type() != JsonUtil.T_BRACKET_L)
                return (Object[]) Array.newInstance(type, 0);// 不是 数组类型
                
            List<Token> values = token.getElements(JsonUtil.T_COMMA);// 过滤逗号token
            Object[] objs = (Object[]) Array.newInstance(type, values.size());
            for (int i = 0; i < objs.length; i++)
                objs[i] = JsonUtil.getParser(type).toObject(json, values.get(i), type);
            
            return objs;
        }
    }
}
