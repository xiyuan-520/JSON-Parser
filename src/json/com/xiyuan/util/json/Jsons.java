package com.xiyuan.util.json;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/***
 * JSON 主程序，对象转换成JSON和JSON转换成对象<br>
 * 本json工具不做任何编码转换，如需切换编码则需要手动将json字符串进行编码
 * @version v1.0.0 @author lgz 2019年8月31日 新建与整理
 */
public final class Jsons implements Serializable
{
    private static final long serialVersionUID = 1L;
    
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
            if (lexer.naxtToken().isArr())
            {
                isList = true;
                break;
            }
            else
                return new ArrayList<T>(0);// 第一个token不是数组
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
     * @param <T>
     * @param json JSON字符串
     * @param field 字段名
     * @return 得到的字符串值，[]开头和结束
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] getArray(String json, String key, Class<T> resultClass)
    {//
        T[] arr = (T[]) getObject(json, key, Array.newInstance(resultClass, 0).getClass());
        if (arr == null)
            return (T[]) Array.newInstance(resultClass, 0);
        return (T[]) arr;
    }
    
    /**
     * 获取boolean 如果 值为1，结果也返回true
     */
    public static boolean getBoolean(String json, String key)
    {
        return getObject(json, key, boolean.class);
    }
    
    /**
     * 指定字段获取int值，若没有对应值则返回0
     */
    public static int getInt(String json, String key)
    {
        return getObject(json, key, int.class);
    }
    
    /***
     * 指定json字段获取一个long值
     * 
     * @param json 源json字符串
     * @param field 指定字段名
     * @return
     */
    public static long getLong(String json, String key)
    {
        return getObject(json, key, long.class);
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
        
        int start = 0;
        boolean isValue = false, findKey = false;
        String k = null;
        int scope = 0;
        JsonLexer lexer = new JsonLexer(json);
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (!findKey && !lexer.isString())
                continue;// 不是String 型的key
                
            if (lexer.isString() && !findKey && key.equals(JsonLexer.removeStartEndQuotation(lexer.value())))
            {
                findKey = true;// 找到key
                start = lexer.pos();
                k = lexer.value();
                scope = lexer.scope();
                continue;
            }
            
            if (!isValue && findKey && !lexer.isColon())
            {// 找到key 但是下一个token不是 冒号 & 没有标记value开始
                findKey = false;
                continue;
            }
            
            if (!isValue && lexer.isColon())
            {// 没有标记开始 但是当前是冒号表示 下一个是值
                isValue = true;
                continue;
            }
            
            if (isValue && findKey)
            { // start是最后的引号值
            
                lexer.BaseParser().toObject(String.class);// 设置value值得结束位置
                if (lexer.hasNext())
                    lexer.naxtToken();
                
                int end = lexer.pos();// 设置value结束位置,当前位置可能是 子object的结束符
                if (lexer.scope() == scope)
                    end++;// 相同深度的与key 相同深度value 时 指向下一个token
                    
                start = start + 1 - k.length();
                
                if (end >= json.length())
                    return json.substring(0, start);
                else
                {
                    String startStr = JsonLexer.trim(json.substring(0, start));
                    String endStr = JsonLexer.trim(json.substring(end));
                    if ((endStr.charAt(0) == JsonLexer.BRACE_R || endStr.charAt(0) == JsonLexer.BRACKET_R) && startStr.endsWith(","))
                        startStr = startStr.substring(0, startStr.length() - 1);
                    return startStr + endStr;
                }
            }
        }
        
        return json;
    }
    
    /**
     * 从一个对象或MAP的json中增加或更新指定的一个KEY<br>
     * 注意：<br>
     * 1.只在对象前添加；举例：json = {"aa":"a1", "bb":"b1"}, key = cc, value = c1, 则添加后为 {"cc":"c1","aa":"a1", "bb":"b1"}<br>
     * 2.在做增加 字段室 要求json字符串包含{}，否则不插入<br>
     * @param json 对象或MAP对应的JSON字符串
     * @param key 对象的字段或MAP的键
     * @param value 值
     * @return 增加或更新KEY之后的json
     */
    public static String toStringAddOrUpdate(String json, String key, Object value)
    {
        boolean isValue = false, findKey = false;
        int start = 0;
        int scope = 0;
        JsonLexer lexer = new JsonLexer(json);
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (!findKey && !lexer.isString())
                continue;// 不是String 型的key
                
            if (lexer.isString() && !findKey && key.equals(JsonLexer.removeStartEndQuotation(lexer.value())))
            {
                findKey = true;// 找到key
                scope = lexer.scope();
                continue;
            }
            
            if (!isValue && findKey && !lexer.isColon())
            {// 找到key 但是下一个token不是 冒号 & 没有标记value开始
                findKey = false;
                continue;
            }
            
            if (!isValue && lexer.isColon())
            {// 没有标记开始 但是当前是冒号表示 下一个是值
                isValue = true;
                start = lexer.pos() + 1;
                continue;
            }
            
            if (isValue && findKey)
            { // start是最后的引号值
                lexer.BaseParser().toObject(String.class);// 设置value值得结束位置
                if (lexer.hasNext())
                    lexer.naxtToken();
                
                int end = lexer.pos();// 设置value结束位置,当前位置可能是 子object的结束符
                if (lexer.scope() == scope)
                    end++;// 相同深度的与key 相同深度value 时 指向下一个token
                    
                String val = value == null ? JsonLexer.NULL : lexer.getParser(value.getClass()).toString(value);
                String startStr = json.substring(0, start);
                if (end >= json.length())
                    return startStr + val;
                else
                    return startStr + val + JsonLexer.trim(json.substring(end));
            }
        }
        
        // 找不到字段则插入
        boolean findScope = false;
        lexer = new JsonLexer(json);
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.isObj())
            {
                findScope = true;
                break;
            }
        }
        
        if (findScope)
        {
            key = lexer.BaseParser().toString(key);
            String val = value == null ? JsonLexer.NULL : lexer.getParser(value.getClass()).toString(value);
            StringBuilder sb = new StringBuilder(val.length() + json.length() + key.length() + 6).append(json.substring(0, lexer.pos() + 1));// 添加原始字符串
            sb.append(key).append(JsonLexer.COLON).append(val);// 新增值
            if (lexer.pos() + 1 < json.length())
            {
                if ((json.charAt(lexer.pos() + 1) != JsonLexer.BRACE_R && (json.charAt(lexer.pos() + 1) != JsonLexer.BRACKET_R)))
                    sb.append(JsonLexer.COMMA);
                sb.append(json.substring(lexer.pos() + 1));// 添加剩余值
            }
            return sb.toString();
        }
        
        return json;
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
     * 指定字段查找json字符串的数据
     * 
     * @param json JSON字符串
     * @param field 字段名
     * @return 得到的字符串值
     */
    public static String getString(String json, String key)
    {//
        return getObject(json, key, String.class);
    }
    
    /***
     * 指定字段查找及对应的类型
     * @param json json 字符串
     * @param field 指定字段名
     * @param cls 指定结果类
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getObject(String json, String key, Class<T> cls)
    {//
        if (json == null || isEmptyBlank(key))
            return null;
        int ind = json.indexOf(key);
        if (ind == -1)
            return null;
        
        JsonLexer lexer = new JsonLexer(json);
        boolean isValue = false, findKey = false;
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.pos() < ind)
                continue;// 未找到到到指定索引
                
            if (!findKey && !lexer.isString())
                continue;// 不是String 型的key
                
            if (lexer.isString() && !findKey && key.equals(JsonLexer.removeStartEndQuotation(lexer.value())))
            {
                findKey = true;// 找到key
                continue;
            }
            
            if (findKey && !isValue && !lexer.isColon())
            {// 找到key 但是下一个token不是 冒号 & 没有标记value开始
                findKey = false;
                continue;
            }
            
            if (!isValue && lexer.isColon())
            {// 没有标记开始 但是当前是冒号表示 下一个是值
                isValue = true;
                continue;
            }
            
            if (isValue)
                return (T) lexer.getParser(cls).toObject(cls);
        }
        return null;
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
