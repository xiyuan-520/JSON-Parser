package com.xiyuan.util.json;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/***
 * JSON 主程序，对象转换成JSON和JSON转换成对象
 * 
 * @version v1.0.0 @author lgz 2019年8月31日 新建与整理
 */
public final class Jsons implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    public final static String INTEGER = "^(0|[\\+\\-]?[1-9]\\d*)$";// 整数，支持正负数
    public final static String FLOAT = "^(0|[\\+\\-]?[1-9]\\d*)(\\.\\d+)?$";// 浮点值，支持多位小数点，支持正负数
    /**
     * 增加JSON中的转义字符，使用双引号时，单引号不转义，使用单引号时双引号不转义，不使用引号时都转义
     * 
     * @param str 原字符串
     * @param quotation 使用的引号 =0表示未使用,='表示单引号,="表示双引号
     * @return 对字符中需要转义的字符，增加转义符
     */
    public static String addEscapeChar(String str, char quotation)
    {
        return JsonLexer.addEscapeChar(str, quotation);
    }
    
    /***
     * 去除JSON中的转义字符
     * 
     * @param str 原字符串
     * @return 去除成对引号之后的字符串
     */
    public static String removeEscapeChar(String str)
    {
        return JsonLexer.removeEscapeChar(str);
    }
    
    /***
     * 去除JSON键和值的前后成对引号
     * 
     * @param str 原字符串
     * @return 去除成对引号之后的字符串
     */
    public static String removeStartEndQuotation(String str)
    {
        return JsonLexer.removeStartEndQuotation(str);
    }
    
    /*****************************************************************************/
    // 以下是json解析或转json相关方法
    /*****************************************************************************/
    
    /**
     * 对象转换成JSON字符串
     * 
     * @param obj 对象
     * @return JSON字符串
     */
    public static String toString(Object obj)
    {
        if (obj == null)
            return JsonLexer.NULL;
        return new JsonLexer(null).getParser(obj.getClass()).toString(obj);
    }
    
    /**
     * JSON字符串转换成对象
     * 
     * @param json JSON字符串
     * @param clazz 转换成对象的类
     * @return obj对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T toObject(String json, Class<T> clazz)
    {
        if (json == null || clazz == null)
            return null;
        
        if (JsonLexer.NULL.equals(json))
            return null;
        
        JsonLexer lexer = new JsonLexer(json);
        lexer.naxtToken();
        T t = (T) lexer.getParser(clazz).toObject(clazz);
        lexer = null;
        return t;
    }
    
    /**
     * 提供泛型，得到列表
     * 
     * @param json JSON字符串
     * @param resultClass 泛型
     * @return List<T>
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> toList(String json, Class<T> resultClass)
    {
        boolean isList = false;
        JsonLexer lexer = new JsonLexer(json);
        while (lexer.hasNext())
        {
            if (lexer.naxtToken().tokenType() == JsonLexer.T_BRACKET_L)
            {
                isList = true;
                break;
            }
        }
        
        if (!isList)
            return new ArrayList<T>(0);
        
        if (JsonLexer.isPrimitiveBase(resultClass))// 如果是基本类型 则使用基本类型的包装类
            resultClass = JsonLexer.getPrimitiveBase(resultClass);
        // 先转成数组再转list
        T[] arr = (T[]) lexer.ArrayParser().toObject(Array.newInstance(resultClass, 0).getClass());
        if (arr == null)
            return new ArrayList<T>(0);
        List<T> ls = new ArrayList<T>(arr.length);
        for (int i = 0; i < arr.length; i++)
            ls.add(arr[i]);
        
        lexer = null;
        return ls;
    }
    
    /**
     * 获取字段对应的数组字符串值，得到结果为[]开头和结束
     * 
     * @param json JSON字符串
     * @param field 字段名
     * @return 得到的字符串值，[]开头和结束
     */
    public static String getArray(String json, String field)
    {//
        String arrText = getString(json, field);
        if (arrText == null || arrText.length() <= 1 || arrText.charAt(0) != JsonLexer.BRACKET_L || arrText.charAt(arrText.length() - 1) != JsonLexer.BRACKET_R)
            return null;
        
        return arrText;
    }
    
    /**
     * 获取字段对应的数组字符串值，得到结果为[]开头和结束
     * 
     * @param <T>
     * @param json JSON字符串
     * @param field 字段名
     * @return 得到的字符串值，[]开头和结束
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] getArray(String json, String field, Class<T> resultClass)
    {//
        if (resultClass == null)
            return null;
        
        String arrJson = getString(json, field);
        if (arrJson == null || arrJson.length() <= 1 || arrJson.charAt(0) != JsonLexer.BRACKET_L || arrJson.charAt(arrJson.length() - 1) != JsonLexer.BRACKET_R)
            return null;
        
        return (T[]) toList(arrJson, resultClass).toArray();
    }
    
    /**
     * 获取boolean 如果 值为1，结果也返回true
     */
    public static boolean getBoolean(String json, String field)
    {
        String value = getString(json, field);
        return JsonLexer.TRUE.equalsIgnoreCase(value) ? true : "1".equals(value);
    }
    
    /**
     * 指定字段获取int值，若没有对应值则返回0
     */
    public static int getInt(String json, String field)
    {
        String value = getString(json, field);
        if (Pattern.matches(INTEGER, value))
        {
            if (value.length() > 11)
                return 0;
            long v = Long.parseLong(value);
            if (v > Long.valueOf(Integer.MAX_VALUE) || v < Long.valueOf(Integer.MIN_VALUE))
                return 0;
            else
                return Integer.parseInt(value);
        }
        else
            return 0;
    }
    
    /***
     * 指定json字段获取一个long值
     * 
     * @param json 源json字符串
     * @param field 指定字段名
     * @return
     */
    public static long getLong(String json, String field)
    {
        String value = getString(json, field);
        if (Pattern.matches(INTEGER, value))
        {
            boolean isNegative = false;
            if (value.startsWith("+") || value.startsWith("-"))
            {
                if (value.startsWith("-"))
                    isNegative = true;
                
                // 剔除负号
                if (value.length() >= 2)
                    value = value.substring(1);
            }
            
            // 如果是数字
            String longMaxStr = Long.toString(Long.MAX_VALUE);
            String longMinStr = String.valueOf(Long.toString(Long.MIN_VALUE)).substring(1);// 去掉负号
            if (value.length() >= longMaxStr.length())
            {// 比较每一位是否超出最大限制
            
                for (int i = 0; i < value.length(); i++)
                {
                    char val = value.charAt(i);
                    
                    char max = 0;
                    if (isNegative)
                        max = longMinStr.charAt(i);
                    else
                        max = longMaxStr.charAt(i);
                    
                    if (val > max)
                        return 0;
                }
                
                return Long.parseLong(value);
            }
            else
                return Long.parseLong(value);
        }
        else
            return 0;
        
    }
    
    /**
     * 从一个对象或MAP的json中增加或更新指定的一个KEY
     * 
     * @param json 对象或MAP对应的JSON字符串
     * @param key 对象的字段或MAP的键
     * @param value 值
     * @return 增加或更新KEY之后的json
     */
    public static String toStringAddOrUpdate(String json, String key, Object value)
    {
        // if (json == null)
        // return null;
        // if (isEmptyBlank(key) || json.length() < 2)
        // return json;// json 字符串必须大于2个字符
        //
        // // token.getElements()[0] = field的上一个token，token.getElements()[1] =
        // // field 的value
        // Token token = getTokens(json, key);
        // if (token == null || token.size() < 2)
        // {// 找不到字段
        // int pos = 0;
        // for (; pos < json.length(); pos++)
        // {
        // char ch = json.charAt(pos);
        // if (!isWhitespace(ch) && ch != BRACE_L)
        // return json;
        //
        // if (ch == BRACE_L)
        // break;
        // }
        // key = addEscapeChar(key, DB_QUOTE);
        // String val = toString(value);
        // StringBuilder sb = new StringBuilder(json.length() + key.length() + 3
        // + val.length());
        // sb.append(json.substring(0, pos + 1));// 添加根token
        // sb.append(DB_QUOTE).append(key).append(DB_QUOTE).append(COLON).append(val).append(COMMA);//
        // 添加键值;
        // sb.append(json.substring(pos + 1));
        // return sb.toString();
        // }
        // else
        // {// 找到字段了
        // StringBuilder sb = new StringBuilder(json.length());
        // sb.append(json.substring(0, token.getElements()[1].begin()));
        // sb.append(toString(value));// 更新值
        // sb.append(json.substring(token.getElements()[1].end() + 1));
        // return sb.toString();
        // }
        
        return null;
    }
    
    /**
     * 提供两个键值生成JSON字符串，对应MAP的两次put
     * 
     * @param key 第一个键
     * @param value 第一个值
     * @return 生成后的JSON字符串
     */
    public static String toString(String key, Object value)
    {
        if (isEmptyBlank(key))
            return JsonLexer.NULL;
        
        return new StringBuilder().append(JsonLexer.BRACE_L).append(toString(trim(key))).append(JsonLexer.COLON).append(toString(value)).append(JsonLexer.BRACE_R).toString();
    }
    
    /**
     * 从json字符串中删除字段 当前只删除 一级字段<br>
     * 
     * @param json 源json字符串
     * @param field 指定删除字段
     * @retun 返回删除后的字符串
     */
    public static String remove(String json, String key)
    {
        
        // Token token = getTokens(json, key);
        // if (token == null || token.size() < 2)
        // return json;
        //
        // // token.getElements()[0] = field的上一个token，token.getElements()[1] =
        // // field 的value
        // Token[] tokens = token.getElements();
        // StringBuilder sb = new StringBuilder(json.length());
        // sb.append(json.substring(0, (tokens[0] != null && tokens[0].type() ==
        // Token.COMMA) ? tokens[0].begin() : tokens[1].begin()));
        // sb.append(json.substring(tokens[1].end() + 1));
        // return sb.toString();
        return null;
    }
    
    /**
     * 指定字段查找json字符串的数据
     * 
     * @param json JSON字符串
     * @param field 字段名
     * @return 得到的字符串值
     */
    public static String getString(String json, String field)
    {//
     // if (json == null)
     // return null;
     // Token token = getTokens(json, field);
     // if (token == null || token.size() < 2)
        return null;
        
        // return removeStartEndQuotation(field == null ? token.toString(json) :
        // token.getElements()[1].toString(json));
    }
    
    /**************************************************************************/
    // 以下提供验证方法
    /**************************************************************************/
    /**
     * 验证是否为空白
     * 
     * @param ch
     * @return
     */
    public static boolean isWhitespace(char ch)
    {// 中文空格 =12288=0x3000, BOM空格 =65279=0xFEFF，英文空格 = 32
        return ch <= 32 || ch == 12288 || ch == 65279;
    }
    
    /**
     * 判断类结构，是否实现指定的接口（含本类）
     * 
     * @param cls 类结构
     * @param iface 接口
     * @return =true表示实现,=false表示未实现
     */
    public static boolean isImplement(Class<?> cls, Class<?> iface)
    {
        if (iface == null || cls == null || cls == Object.class || isPrimitiveBase(cls) || !iface.isInterface())
            return false;
        
        if (cls == iface)
            return true;
        
        // 先判断接口是否实现，或实现的接口是否继承
        for (Class<?> c : cls.getInterfaces())
        {
            while (c != iface)
            {// 如果未实现，再继承查找，直到没有继承
                Class<?>[] cs = c.getInterfaces();
                if (cs.length == 0)
                    break;
                
                c = cs[0];
            }
            
            // 最后判断是否实现
            if (c == iface)
                return true;
        }
        
        // 再判断继承是否实现接口，有可能cls本身是接口
        Class<?> c = cls.getSuperclass();
        while (c != null && c != Object.class)
        {
            if (isImplement(c, iface))
                return true;
            
            c = c.getSuperclass();
        }
        
        return false;
    }
    
    /**
     * 是否八种基本原型基本类型，仅支持byte/short/int/long/float/double/boolean/char
     * 
     * @param clazz 类结构
     * @return =true/=false
     */
    public static boolean isPrimitiveBase(Class<?> clazz)
    {
        return JsonLexer.isPrimitiveBase(clazz);
    }
    
    /**
     * 判断是否为空
     * 
     * @param s
     * @return
     */
    public static boolean isEmpty(String s)
    {
        return (s == null || s.length() == 0);
    }
    
    public static boolean isEmptyBlank(String s)
    {
        return JsonLexer.isEmptyBlank(s);
    }
    
    public static String trim(String s)
    {
        return JsonLexer.trim(s);
    }
}
