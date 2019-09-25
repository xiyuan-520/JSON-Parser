/*
 * 版权所有 (C) 2015 知启蒙(ZHIQIM) 保留所有权利。
 * 
 * 指定登记&发行网站： https://www.zhiqim.com/ 欢迎加盟知启蒙，[编程有你，知启蒙一路随行]。
 * 
 * 本文采用《知启蒙许可证》，除非符合许可证，否则不可使用该文件！
 * 1、您可以免费使用、修改、合并、出版发行和分发，再授权软件、软件副本及衍生软件；
 * 2、您用于商业用途时，必须在原作者指定的登记网站，按原作者要求进行登记；
 * 3、您在使用、修改、合并、出版发行和分发时，必须包含版权声明、许可声明，及保留原作者的著作权、商标和专利等知识产权；
 * 4、您在互联网、移动互联网等大众网络下发行和分发再授权软件、软件副本及衍生软件时，必须在原作者指定的发行网站进行发行和分发；
 * 5、您可以在以下链接获取一个完整的许可证副本。
 * 
 * 许可证链接：http://zhiqim.org/licenses/zhiqim_register_publish_license.htm
 * 
 * 除非法律需要或书面同意，软件由原始码方式提供，无任何明示或暗示的保证和条件。详见完整许可证的权限和限制。
 */
package com.xiyuan.util.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.xiyuan.util.json.parser.ArrayParser;
import com.xiyuan.util.json.parser.BaseParser;
import com.xiyuan.util.json.parser.DateParser;
import com.xiyuan.util.json.parser.ListParser;
import com.xiyuan.util.json.parser.MapParser;
import com.xiyuan.util.json.parser.ObjectParser;

/**
 * 
 * json 分析器
 * 
 * @version v1.0.0 @author lgz 2019-9-22 新建与整理
 */
public final class JsonLexer
{
    
    /** 默认类型 -1 token */
    // public final static byte T_DEFAULT = -1;
    
    public final static byte T_UNKNOWN = 0;
    /** 左大括号类型 1 = { */
    public final static byte T_BRACE_L = 1;// "{"
    /** 右大括号类型 2 = } */
    public final static byte T_BRACE_R = 2;// "}"
    /** 左中括号类型 3 = [ */
    public final static byte T_BRACKET_L = 3;// "["
    /** 右中括号类型 4 = ] */
    public final static byte T_BRACKET_R = 4;// "]"
    /** 冒号类型 5 = : */
    public final static byte T_COLON = 5;// ":"
    /** 逗号类型 6 = , */
    public final static byte T_COMMA = 6;// ","
    /** 字符类型 7 */
    public final static byte T_STRING = 7;// String值
    /** 结束类型 8 = EOF **/
    public final static byte T_EOF = 8;// 结束
    
    /** 左大括号 { */
    public final static char BRACE_L = '{';
    /** 右大括号 } */
    public final static char BRACE_R = '}';
    /** 左中括号 [ */
    public final static char BRACKET_L = '[';
    /** 右中括号 ] */
    public final static char BRACKET_R = ']';
    /** 冒号 : */
    public final static char COLON = ':';
    /** 逗号 , */
    public final static char COMMA = ',';
    /** 单引号 ' */
    public final static char QUOTE = '\'';
    /** 双引号 " */
    public final static char DB_QUOTE = '"';
    
    /** String单引号 ' */
    public final static String QUOTE_S = String.valueOf(QUOTE);
    /** String双引号 " */
    public final static String DB_QUOTE_S = String.valueOf(DB_QUOTE);
    /** String左大括号 { */
    public final static String BRACE_L_S = String.valueOf(BRACE_L);
    /** String右大括号 } */
    public final static String BRACE_R_S = String.valueOf(BRACE_R);
    /** String左中括号 [ */
    public final static String BRACKET_L_S = String.valueOf(BRACKET_L);
    /** String右中括号 ] */
    public final static String BRACKET_R_S = String.valueOf(BRACKET_R);
    /** String冒号 : */
    public final static String COLON_S = String.valueOf(COLON);
    /** String逗号 , */
    public final static String COMMA_S = String.valueOf(COMMA);
    /** String"null" */
    public final static String NULL = "null";
    /** "true" */
    public final static String TRUE = "true";
    /** "false" */
    public final static String FALSE = "false";
    /** 空对象 {} */
    public final static String EMPTY_OBJ = "{}";
    /** 空数组 [] */
    public final static String EMPTY_ARR = "[]";
    
    /**************************************************/
    // 八大基本类型&封装类&数组&封装数组的class的哈希吗
    /**************************************************/
    
    /**************************************************/
    // 基本类型
    /**************************************************/
    /**
     * boolean.class.getName().hashCode()<br>
     * = 64711720;
     */
    public final static int BOOL_CLS_HASH = 64711720;// boolean.class.getName().hashCode();
    /**
     * Boolean.class.getName().hashCode()<br>
     * = 344809556
     */
    public final static int BOOL_OBJ_CLS_HASH = 344809556;// Boolean.class.getName().hashCode();
    /**
     * byte.class.getName().hashCode()<br>
     * = 3039496
     */
    public final static int BYTE_CLS_HASH = 3039496;// byte.class.getName().hashCode();
    /**
     * Byte.class.getName().hashCode()<br>
     * = 398507100
     */
    public final static int BYTE_OBJ_CLS_HASH = 398507100;// Byte.class.getName().hashCode();
    /**
     * char.class.getName().hashCode()<br>
     * = 3052374
     */
    public final static int CHAR_CLS_HASH = 3052374;// char.class.getName().hashCode();
    /**
     * Character.class.getName().hashCode()<br>
     * = 155276373
     */
    public final static int CHAR_OBJ_CLS_HASH = 155276373;// Character.class.getName().hashCode();
    /**
     * short.class.getName().hashCode()<br>
     * = 109413500
     */
    public final static int SHORT_CLS_HASH = 109413500;// short.class.getName().hashCode();
    /**
     * Short.class.getName().hashCode()<br>
     * = -515992664
     */
    public final static int SHORT_OBJ_CLS_HASH = -515992664;// Short.class.getName().hashCode();
    /**
     * int.class.getName().hashCode()<br>
     * = 104431
     */
    public final static int INT_CLS_HASH = 104431;// int.class.getName().hashCode();
    /**
     * Integer.class.getName().hashCode()<br>
     * = -2056817302
     */
    public final static int INT_OBJ_CLS_HASH = -2056817302;// Integer.class.getName().hashCode();
    /**
     * long.class.getName().hashCode()<br>
     * = 3327612
     */
    public final static int LONG_CLS_HASH = 3327612;// long.class.getName().hashCode();
    /**
     * Long.class.getName().hashCode()<br>
     * = 398795216
     */
    public final static int LONG_OBJ_CLS_HASH = 398795216;// Long.class.getName().hashCode();
    /**
     * float.class.getName().hashCode()<br>
     * = 97526364
     */
    public final static int FLOAT_CLS_HASH = 97526364;// float.class.getName().hashCode();
    /**
     * Float.class.getName().hashCode()<br>
     * = -527879800
     */
    public final static int FLOAT_OBJ_CLS_HASH = -527879800;// Float.class.getName().hashCode();
    /**
     * double.class.getName().hashCode()<br>
     * = -1325958191
     */
    public final static int DOUBLE_CLS_HASH = -1325958191;// double.class.getName().hashCode();
    /**
     * Double.class.getName().hashCode()<br>
     * = 761287205
     */
    public final static int DOUBLE_OBJ_CLS_HASH = 761287205;// Double.class.getName().hashCode();
    
    /**************************************************/
    // 基本类型[]
    /**************************************************/
    /**
     * double[].class.getName().hashCode()<br>
     * = 2889
     */
    public final static int DOUBLE_ARR_CLS_HASH = 2889;// double[].class.getName().hashCode();
    /**
     * Double[].class.getName().hashCode()<br>
     * = 175516795
     */
    public final static int DOUBLE_OBJ_ARR_CLS_HASH = -175516795;// Double[].class.getName().hashCode();
    /**
     * float[].class.getName().hashCode()<br>
     * = 2891
     */
    public final static int FLOAT_ARR_CLS_HASH = 2891;// float[].class.getName().hashCode();
    /**
     * Float[].class.getName().hashCode()<br>
     * = 48646404
     */
    public final static int FLOAT_OBJ_ARR_CLS_HASH = 48646404;// Float[].class.getName().hashCode();
    /**
     * long[].class.getName().hashCode()<br>
     * = 2895
     **/
    public final static int LONG_ARR_CLS_HASH = 2895;// long[].class.getName().hashCode();
    /**
     * Long[].class.getName().hashCode()<br>
     * = -2071011078
     */
    public final static int LONG_OBJ_ARR_CLS_HASH = -2071011078;// Long[].class.getName().hashCode();
    /**
     * int[].class.getName().hashCode()<br>
     * = 2894
     */
    public final static int INT_ARR_CLS_HASH = 2894;// int[].class.getName().hashCode();
    /**
     * Integer[].class.getName().hashCode()<br>
     * = -1935445726
     */
    public final static int INT_OBJ_ARR_CLS_HASH = -1935445726;// Integer[].class.getName().hashCode();
    /**
     * short[].class.getName().hashCode()<br>
     * = 2904
     */
    public final static int SHORT_ARR_CLS_HASH = 2904;// short[].class.getName().hashCode();
    /**
     * Short[].class.getName().hashCode()<br>
     * = 417147620
     */
    public final static int SHORT_OBJ_ARR_CLS_HASH = 417147620;// Short[].class.getName().hashCode();
    /**
     * char[].class.getName().hashCode()<br>
     * = 2888
     */
    public final static int CHAR_ARR_CLS_HASH = 2888;// char[].class.getName().hashCode();
    /**
     * Character[].class.getName().hashCode()<br>
     * = -1378128041
     */
    public final static int CHAR_OBJ_ARR_CLS_HASH = -1378128041;// Character[].class.getName().hashCode();
    /**
     * byte[].class.getName().hashCode()<br>
     * = 2887
     */
    public final static int BYTE_ARR_CLS_HASH = 2887;// byte[].class.getName().hashCode();
    /**
     * Byte[].class.getName().hashCode()<br>
     * = 2079942674
     */
    public final static int BYTE_OBJ_ARR_CLS_HASH = -2079942674;// Byte[].class.getName().hashCode();
    /**
     * boolean[].class.getName().hashCode()<br>
     * = 2911
     */
    public final static int BOOL_ARR_CLS_HASH = 2911;// boolean[].class.getName().hashCode();
    /**
     * Boolean[].class.getName().hashCode()<br>
     * = -499457160
     */
    public final static int BOOL_OBJ_ARR_CLS_HASH = -499457160;// Boolean[].class.getName().hashCode();
    
    /**************************************************/
    // String值相关
    /**************************************************/
    /**
     * String.class.getName().hashCode()<br>
     * = 1195259493
     */
    public final static int STRING_CLS_HASH = 1195259493;// String.class.getName().hashCode();
    /**
     * String[].class.getName().hashCode()<br>
     * = 392722245
     */
    public final static int STRING_ARR_CLS_HASH = 392722245;// String[].class.getName().hashCode();
    
    /**************************************************/
    // 哈希表
    /**************************************************/
    /** Map.class.getName().hashCode() = 1383349348 */
    public final static int MAP_CLS_HASH = -1383349348;// Map.class.getName().hashCode();
    /**
     * HashMap.class.getName().hashCode()<br>
     * = -1402722386
     */
    public final static int HASHMAP_CLS_HASH = -1402722386;// HashMap.class.getName().hashCode();
    /**
     * ConcurrentMap.class.getName().hashCode()<br>
     * = -1419705938
     */
    public final static int CONCURRENTMAP_CLS_HASH = -1419705938;// ConcurrentMap.class.getName().hashCode();
    /**
     * ConcurrentHashMap.class.getName().hashCode()<br>
     * = 577244352
     */
    public final static int CONCURRENTHASHMAP_CLS_HASH = 577244352;// ConcurrentHashMap.class.getName().hashCode();
    /**
     * Hashtable.class.getName().hashCode()<br>
     * = 639525312
     */
    public final static int HASHTABLE_CLS_HASH = 639525312;// Hashtable.class.getName().hashCode();
    /**
     * LinkedHashMap.class.getName().hashCode()<br>
     * = 1258621781
     */
    public final static int LINKEDHASHMAP_CLS_HASH = 1258621781;// LinkedHashMap.class.getName().hashCode();
    /**
     * TreeMap.class.getName().hashCode()<br>
     * = 1131064094
     */
    public final static int TREEMAP_CLS_HASH = 1131064094;// TreeMap.class.getName().hashCode();
    
    /**************************************************/
    // 链表
    /**************************************************/
    /**
     * List.class.getName().hashCode()<br>
     * = 65821278
     */
    public final static int LIST_CLS_HASH = 65821278;// List.class.getName().hashCode();
    /**
     * ArrayList.class.getName().hashCode()<br>
     * = -1114099497
     */
    public final static int ARRAYLIST_CLS_HASH = -1114099497;// ArrayList.class.getName().hashCode();
    /**
     * LinkedList.class.getName().hashCode()<br>
     * = -1899270121
     */
    public final static int LINKEDLIST_CLS_HASH = -1899270121;// LinkedList.class.getName().hashCode();
    /**
     * Set.class.getName().hashCode()<br>
     * = -1383343454
     */
    public final static int SET_CLS_HASH = -1383343454;// Set.class.getName().hashCode();
    /**
     * HashSet.class.getName().hashCode()<br>
     * = -1402716492
     */
    public final static int HASHSET_CLS_HASH = -1402716492;// HashSet.class.getName().hashCode();
    
    /**************************************************/
    // Object值相关
    /**************************************************/
    /**
     * Object.class.getName().hashCode()<br>
     * = 1063877011
     */
    public final static int OBJECT_CLS_HASH = 1063877011;// Object.class.getName().hashCode();
    /**
     * Object[].class.getName().hashCode()<br>
     * = 614832599
     */
    public final static int OBJECT_ARR_CLS_HASH = 614832599;// Object[].class.getName().hashCode();
    
    // 时间
    /**
     * java.util.Calendar.class.getName().hashCode()<br>
     * = -861027074
     */
    public final static int CALENDAR_CLS_HASH = -861027074;// java.util.Calendar.class.getName().hashCode();
    /**
     * java.util.Date.class.getName().hashCode()<br>
     * = 65575278
     */
    public final static int DATE_CLS_HASH = 65575278;// java.util.Date.class.getName().hashCode();
    /**
     * java.sql.Date.class.getName().hashCode()<br>
     * = 1087757882
     */
    public final static int SQL_DATE_CLS_HASH = 1087757882;// java.sql.Date.class.getName().hashCode();
    /**
     * java.sql.Time.class.getName().hashCode()<br>
     * = 1088242009
     */
    public final static int SQL_TIME_CLS_HASH = 1088242009;// java.sql.Time.class.getName().hashCode();
    /**
     * java.sql.Timestamp.class.getName().hashCode()<br>
     * = 1252880906
     */
    public final static int SQL_TIMESTAMP_CLS_HASH = 1252880906;// java.sql.Timestamp.class.getName().hashCode();
    
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
        if (clazz == null)
            return false;
        switch (clazz.getName().hashCode())
        {
            case JsonLexer.BOOL_CLS_HASH:
            case JsonLexer.BYTE_CLS_HASH:
            case JsonLexer.CHAR_CLS_HASH:
            case JsonLexer.SHORT_CLS_HASH:
            case JsonLexer.INT_CLS_HASH:
            case JsonLexer.LONG_CLS_HASH:
            case JsonLexer.FLOAT_CLS_HASH:
            case JsonLexer.DOUBLE_CLS_HASH:
                return true;
            default:
                return false;
        }
    }
    
    /**
     * 获取八种基本类型的包装类，仅支持byte/short/int/long/float/double/boolean/char
     * @param <T>
     * 
     * @param clazz 类结构
     * @return =true/=false
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getPrimitiveBase(Class<T> clazz)
    {
        Class<T> cls = null;
        switch (clazz.getName().hashCode())
        {
            case BOOL_CLS_HASH:// = 64711720;//boolean.class.getName().hashCode();
                cls = (Class<T>) Boolean.class;
                break;
            case BYTE_CLS_HASH:// = 3039496;//byte.class.getName().hashCode();
                cls = (Class<T>) Byte.class;
                break;
            case CHAR_CLS_HASH:// :// = 3052374;// char.class.getName().hashCode();
                cls = (Class<T>) Character.class;
                break;
            case SHORT_CLS_HASH:// = 109413500;//short.class.getName().hashCode();
                cls = (Class<T>) Short.class;
                break;
            case INT_CLS_HASH:// = 104431;//int.class.getName().hashCode();
                cls = (Class<T>) Integer.class;
                break;
            case LONG_CLS_HASH:// = 3327612;//long.class.getName().hashCode();
                cls = (Class<T>) Long.class;
                break;
            case FLOAT_CLS_HASH:// = 97526364;//float.class.getName().hashCode();
                cls = (Class<T>) Float.class;
                break;
            case DOUBLE_CLS_HASH:// =
                                 // -1325958191;//double.class.getName().hashCode();
                cls = (Class<T>) Double.class;
                break;
            default:
                cls = clazz;
                break;
        }
        
        return cls;
    }
    
    /**
     * 判断是否为空
     * 只判断 s = null 或者 s.length = 0
     * @param s
     */
    public static boolean isEmpty(String s)
    {
        return (s == null || s.length() == 0);
    }
    
    /****
     * 判断是否全部为空白，当s = null 或者 s.length = 0 或者 全部是空白 时返回true
     * @param s     原字符串
     * @return      true表示全部是空白
     */
    public static boolean isEmptyBlank(String s)
    {
        if (s == null || s.length() == 0)
            return true;
        
        for (char ch : s.toCharArray())
        {
            if (!isWhitespace(ch))
                return false;
        }
        return true;
    }
    
    /**
     * 去除两边空白
     * @param s     原字符串
     * @return      去除空白后的字符串
     */
    public static String trim(String s)
    {
        if (s == null)
            return null;
        
        int i = 0, j = s.length() - 1;
        for (; i < s.length(); i++)
        {
            if (!isWhitespace(s.charAt(i)))
                break;
        }
        
        for (; j > 0; j--)
        {
            if (!isWhitespace(s.charAt(j)))
                break;
        }
        
        if (j >= i && i < s.length())
            return s.substring(i, j + 1);
        else
            return "";
    }
    
    /**
     * 增加JSON中的转义字符，使用双引号时，单引号不转义，使用单引号时双引号不转义，不使用引号时都转义
     * 
     * @param str 原字符串
     * @param quotation 使用的引号 =0表示未使用,='表示单引号,="表示双引号
     * @return 对字符中需要转义的字符，增加转义符
     */
    public static String addEscapeChar(String str, char quotation)
    {
        if (str == null)
            return null;
        
        StringBuilder strb = new StringBuilder();
        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            switch (c)
            {
                case '\\':
                    strb.append("\\\\");
                    break;
                case DB_QUOTE:
                    if (quotation == 0 || quotation == DB_QUOTE)
                        strb.append("\\\"");
                    else
                        strb.append(c);
                    break;
                case QUOTE:
                    if (quotation == 0 || quotation == QUOTE)
                        strb.append("\\\'");
                    else
                        strb.append(c);
                    break;// 单引号或无引号时单引号要转义，双引号下的单引号无需处理
                case '\b':
                    strb.append("\\b");
                    break;
                case '\f':
                    strb.append("\\f");
                    break;
                case '\r':
                    strb.append("\\r");
                    break;
                case '\n':
                    strb.append("\\n");
                    break;
                case '\t':
                    strb.append("\\t");
                    break;
                case '/':
                    strb.append("\\/");
                    break;
                default:
                    strb.append(c);
                    break;
            }
        }
        
        return strb.toString();
    }
    
    /***
     * 去除JSON中的转义字符
     * 
     * @param str 原字符串
     * @return 去除成对引号之后的字符串
     */
    public static String removeEscapeChar(String str)
    {
        if (str == null)
            return null;
        
        StringBuilder strb = new StringBuilder();
        boolean isEscape = false;// 是否前一字符是转义字符
        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            if (!isEscape)
            {// 未转义
                if (c == '\\')
                    isEscape = true;// 设为有转义
                else
                    strb.append(c);
            }
            else
            {// 有转义
                switch (c)
                {
                    case '\\':
                        strb.append('\\');
                        break;
                    case '\"':
                        strb.append('\"');
                        break;
                    case QUOTE:
                        strb.append(QUOTE);
                        break;
                    case 'b':
                        strb.append('\b');
                        break;
                    case 'f':
                        strb.append('\f');
                        break;
                    case 'n':
                        strb.append('\n');
                        break;
                    case 'r':
                        strb.append('\r');
                        break;
                    case 't':
                        strb.append('\t');
                        break;
                    case '/':
                        strb.append('/');
                        break;
                    default:
                        strb.append("\\").append(c);
                        break;// 如果未找到匹配,则返原值
                }
                isEscape = false;// 重置转义为结束
            }
        }
        
        if (isEscape)
        {// 最后一个字符是\
            strb.append("\\");
        }
        
        return strb.toString();
    }
    
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
    
    /***
     * 去除JSON键和值的前后成对引号
     * 
     * @param str 原字符串
     * @return 去除成对引号之后的字符串
     */
    public static String removeStartEndQuotation(String str)
    {
        if (str == null)
            return null;
        
        if (str.length() >= 2 && str.startsWith(DB_QUOTE_S) && str.endsWith(DB_QUOTE_S))
        {// 有双引号删除退出
            str = str.substring(1, str.length() - 1);
            return str;
        }
        
        // 没有双引号则判断单引号
        if (str.length() >= 2 && str.startsWith(QUOTE_S) && str.endsWith(QUOTE_S))
            str = str.substring(1, str.length() - 1);
        
        return str;
    }
    
    /***********************************************************************/
    // 以下是类的定义及对象的调用方法
    /***********************************************************************/
    private JsonParser baseParser;
    private JsonParser arrayParser;
    private JsonParser listParser;
    private JsonParser mapParser;
    private JsonParser dateParser;
    private JsonParser objParser;
    
    private int pos = -1;
    private String json = null;
    private List<Byte> contextLs = new ArrayList<Byte>();// 当前token 所在的作用范围类型
    private byte curType = T_UNKNOWN;// 当前token 的类型
    private byte prevType = T_UNKNOWN;// 当前token 的类型
    private char ch;// 循环的当前字符
    private int objNum = 0;
    private int arrNum = 0;
    private String value = NULL;
    private boolean novalue = false;
    private int scopeIndex = -1;
    private char quote = 0;// 字符串开始的引号值
    private int length = 0;
    
    public JsonLexer(String input)
    {
        this.json = trim(input);
        this.value = null;
        this.baseParser = new BaseParser(this);
        this.arrayParser = new ArrayParser(this);
        this.listParser = new ListParser(this);
        this.mapParser = new MapParser(this);
        this.dateParser = new DateParser(this);
        this.objParser = new ObjectParser(this);
    }
    
    public JsonParser BaseParser()
    {
        return this.baseParser;
    }
    
    public JsonParser ArrayParser()
    {
        return this.arrayParser;
    }
    
    public JsonParser ListParser()
    {
        return this.listParser;
    }
    
    public JsonParser MapParser()
    {
        return this.mapParser;
    }
    
    public JsonParser DateParser()
    {
        return this.dateParser;
    }
    
    public JsonParser ObjectParser()
    {
        return this.objParser;
    }
    
    public String value()
    {
        return this.value;
    }
    
    public boolean hasNext()
    {
        if (json == null)
            return false;
        
        return this.curType != T_EOF && nextPos() < json.length();
    }
    
    private int nextPos()
    {
        return this.pos + 1;
    }
    
    /**
     * 获取当前字符的索引
     * @return
     */
    public int pos()
    {
        return this.pos;
    }
    
    public byte curType()
    {
        return this.curType;
    }
    
    public byte prevType()
    {
        return this.prevType;
    }
    
    private void setType(byte type)
    {
        this.prevType = this.curType;
        this.curType = type;
    }
    
    public JsonLexer naxtToken()
    {
        this.value = NULL;
        this.novalue = false;
        if (!hasNext())
        {
            setType(T_EOF);
            this.scopeIndex = -1;
            this.ch = 0;
            this.novalue = false;
            return this;
        }
        
        this.pos = nextPos();
        this.ch = json.charAt(this.pos);
        if (isWhitespace(ch))
            return naxtToken();// 空白字符开头
            
        // json 数据格式只有两种，1.对象使用{} 括起，2.数组[] 括起。所以非 // {或者[开头的数据
        if (pos == 0 && BRACE_L != ch && ch != BRACKET_L)
            return naxtToken();
        
        switch (ch)
        {
            case BRACE_L:
            {
                objNum++;
                value = String.valueOf(ch);
                setScope(T_BRACE_L);
                setType(T_BRACE_L);
                break;
            }
            case BRACE_R:
            {
                if (objNum > 0)
                {
                    objNum--;
                    if (!contextLs.isEmpty() && scopeType() == T_BRACE_L)
                        removeScope();// 设置结束当前context
                        
                    value = String.valueOf(ch);
                    setType(scopeType());
                }
                else
                    pos = buildStringToken(pos, scopeType(), curType);
                
                break;
            }
            case BRACKET_L:
            {
                
                arrNum++;
                value = String.valueOf(ch);
                setScope(T_BRACKET_L);
                setType(T_BRACKET_L);
                break;
            }
            case BRACKET_R:
            {
                if (arrNum > 0)
                {
                    arrNum--;
                    if (!contextLs.isEmpty() && scopeType() == T_BRACKET_L)
                        removeScope();// 设置结束当前context
                        
                    value = String.valueOf(ch);
                    setType(scopeType());
                }
                else
                    pos = buildStringToken(pos, scopeType(), curType);
                
                break;
            }
            case COMMA:// 上一个token 不能为,
            {// 前面token 必须是 对象结束 或者 数组结束 或者字符串
            
                if (curType == T_COMMA)
                {
                    naxtToken();
                    return this;// 上一个token类型是逗号
                }
                
                // 处理上一个是 冒号 当前为逗号，则当前值为null 列：{dd:,} 被整理 后为 {dd:null,}
                if (curType == T_COLON)
                {
                    setType(T_STRING);
                    this.novalue = true;// 设置没有值标记
                    pos--;// 设置当前为值
                }
                else
                {
                    setType(T_COMMA);
                    value = String.valueOf(ch);
                }
                break;
            }
            case COLON:
            {
                
                if (curType == JsonLexer.T_STRING)
                {// 上一个token是String 则当前是冒号
                    setType(T_COLON);
                    value = String.valueOf(ch);
                }
                else
                    pos = buildStringToken(pos, scopeType(), curType);
                
                break;
            }
            default:
            {
                pos = buildStringToken(pos, scopeType(), curType);
                break;
            }
        }
        return this;
    }
    
    private int buildStringToken(int pos, int scope, byte prevType)
    {
        getStringTokenLength(pos, scopeType(), curType);
        if (length > 0)
        {
            this.value = json.substring(pos, pos + length);
            pos += (length - 1);
        }
        
        setType(T_STRING);
        return pos;
    }
    
    /**
     * 生成从当前位置开始的 一个字符串token
     * 
     * @param pos 读取位置
     * @param scope token所属范围类型
     * @param prevToken 上一个token的类型
     * @return 返回String token 的字符长度
     */
    private int getStringTokenLength(int pos, byte contextType, byte prevType)
    {
        length = 0;
        quote = 0;
        if (scope() < 0)// 当前没有作用域 则视为String字符 一直到json末尾
            return length = json.length() - pos;//
            
        for (; pos < json.length(); pos++ ,length++)
        {
            ch = json.charAt(pos);
            if (quote == 0)
            {
                quote = ch;// 记录开始符
                continue;
            }
            
            // 查找结束符， 非引号字符串结束的字符
            if (quote > 0 && quote != DB_QUOTE && quote != QUOTE && (ch == COLON || ch == COMMA || ch == BRACE_R || ch == BRACKET_R))
            {
                if (contextType == T_BRACKET_L && ch == COLON)// 如果是数组当前是冒号&所属范围是数组，则当前冒号为值
                    continue;
                
                // 当前为冒号上一个token的类型为冒号，则档前为值
                if (ch == COLON && prevType == JsonLexer.T_COLON)
                    continue;// {a::sss:sdcsdcs, b:wwww} 其中 sss:sdcsdcs 为值
                else
                    return length;// 非引号开始 并且有结束负号&作用域不是-1
            }
            
            // 查找结束符，引号开头&当前是引号 & 上一个字符不是不是转义符
            if ((ch == QUOTE || ch == DB_QUOTE) && ch == quote && json.charAt(pos - 1) != '\\')
                return ++length;// 包含当前字符
        }
        return length;
    }
    
    /**
     * 获取当前作用于 注意：第一个开始符返回0，最后一个返回-1表示结束
     */
    public int scope()
    {
        return scopeIndex;
    }
    
    /**
     * 是否有值
     * 注意：这里只有当 上一个token 为冒号&当前为逗号，则当前没值
     */
    public boolean novalue()
    {
        return this.novalue;
    }
    
    public byte scopeType()
    {
        if (contextLs.isEmpty())
            return T_UNKNOWN;
        else
            return contextLs.get(scopeIndex);
    }
    
    public char ch()
    {
        return ch;
    }
    
    /***
     * 添加context
     */
    public void setScope(Byte scope)
    {
        contextLs.add(scope);
        scopeIndex++;
    }
    
    /** 删除当前context */
    public int removeScope()
    {
        if (scopeIndex < 0)
            return scopeIndex;
        
        contextLs.remove(scopeIndex);
        if (contextLs.isEmpty())
            return this.scopeIndex = -1;
        else
            return --scopeIndex;
    }
    
    /** 根据类获取解析器 */
    public JsonParser getParser(Class<?> clazz)
    {
        JsonParser parser = null;
        switch (clazz.getName().hashCode())
        {
        
        // 八大基本类型+封装类+String
            case BOOL_CLS_HASH:// 64711720 = boolean.class.getName().hashCode();
            case BYTE_CLS_HASH:// 3039496 = byte.class.getName().hashCode();
            case CHAR_CLS_HASH:// 3052374 = char.class.getName().hashCode();
            case SHORT_CLS_HASH:// 109413500 = short.class.getName().hashCode();
            case INT_CLS_HASH:// 104431 = int.class.getName().hashCode();
            case LONG_CLS_HASH:// 3327612 = long.class.getName().hashCode();
            case FLOAT_CLS_HASH:// 97526364 = float.class.getName().hashCode();
            case DOUBLE_CLS_HASH:// -1325958191 = double.class.getName().hashCode();
                
            case DOUBLE_OBJ_CLS_HASH:// 761287205 =
                                     // Double.class.getName().hashCode();
            case FLOAT_OBJ_CLS_HASH:// -527879800 =
                                    // Float.class.getName().hashCode();
            case LONG_OBJ_CLS_HASH:// 398795216 = Long.class.getName().hashCode();
            case INT_OBJ_CLS_HASH:// -2056817302 =
                                  // Integer.class.getName().hashCode();
            case SHORT_OBJ_CLS_HASH:// -515992664 =
                                    // Short.class.getName().hashCode();
            case CHAR_OBJ_CLS_HASH:// 155276373 =
                                   // Character.class.getName().hashCode();
            case BYTE_OBJ_CLS_HASH:// 398507100 = Byte.class.getName().hashCode();
            case BOOL_OBJ_CLS_HASH:// 344809556 =
                                   // Boolean.class.getName().hashCode();
                
            case STRING_CLS_HASH:// 1195259493 = String.class.getName().hashCode();
                parser = baseParser;
                break;
            
            // 八大基本类型+封装类+String 的数组
            case BOOL_ARR_CLS_HASH:// 2911; boolean[].class.getName().hashCode();
            case BYTE_ARR_CLS_HASH:// 2887; byte[].class.getName().hashCode();
            case CHAR_ARR_CLS_HASH:// 2888; char[].class.getName().hashCode();
            case SHORT_ARR_CLS_HASH:// 2904; short[].class.getName().hashCode();
            case INT_ARR_CLS_HASH:// 2894; int[].class.getName().hashCode();
            case LONG_ARR_CLS_HASH:// 2895; long[].class.getName().hashCode();
            case FLOAT_ARR_CLS_HASH:// 2891; float[].class.getName().hashCode();
            case DOUBLE_ARR_CLS_HASH:// 2889; double[].class.getName().hashCode();
                
            case BOOL_OBJ_ARR_CLS_HASH:// -499457160;
                                       // Boolean[].class.getName().hashCode();
            case BYTE_OBJ_ARR_CLS_HASH:// -2079942674;
                                       // Byte[].class.getName().hashCode();
            case CHAR_OBJ_ARR_CLS_HASH:// -1378128041;
                                       // Character[].class.getName().hashCode();
            case SHORT_OBJ_ARR_CLS_HASH:// 417147620;
                                        // Short[].class.getName().hashCode();
            case INT_OBJ_ARR_CLS_HASH:// -1935445726;
                                      // Integer[].class.getName().hashCode();
            case LONG_OBJ_ARR_CLS_HASH:// -2071011078;
                                       // Long[].class.getName().hashCode();
            case FLOAT_OBJ_ARR_CLS_HASH:// 48646404;
                                        // Float[].class.getName().hashCode();
            case DOUBLE_OBJ_ARR_CLS_HASH:// -175516795;
                                         // Double[].class.getName().hashCode();
                
            case STRING_ARR_CLS_HASH:// 392722245;
                                     // String[].class.getName().hashCode();
            case OBJECT_ARR_CLS_HASH:// 614832599;
                                     // Object[].class.getName().hashCode();
                parser = arrayParser;
                break;
            
            // 哈希表
            case MAP_CLS_HASH:// -1383349348; Map.class.getName().hashCode();
            case HASHMAP_CLS_HASH:// -1402722386;
                                  // HashMap.class.getName().hashCode();
            case CONCURRENTMAP_CLS_HASH:// =-1419705938;
                                        // ConcurrentMap.class.getName().hashCode();
            case CONCURRENTHASHMAP_CLS_HASH:// 577244352;
                                            // ConcurrentHashMap.class.getName().hashCode();
            case HASHTABLE_CLS_HASH:// 639525312;
                                    // Hashtable.class.getName().hashCode();
            case LINKEDHASHMAP_CLS_HASH:// 1258621781;
                                        // LinkedHashMap.class.getName().hashCode();
            case TREEMAP_CLS_HASH:// 1131064094; TreeMap.class.getName().hashCode();
                parser = mapParser;
                break;
            
            // 链表
            case LIST_CLS_HASH:// 65821278; List.class.getName().hashCode();
            case ARRAYLIST_CLS_HASH:// -1114099497;
                                    // ArrayList.class.getName().hashCode();
            case LINKEDLIST_CLS_HASH:// -1899270121;
                                     // LinkedList.class.getName().hashCode();
            case SET_CLS_HASH:// =-1383343454; Set.class.getName().hashCode();
            case HASHSET_CLS_HASH:// =-1402716492;
                                  // HashSet.class.getName().hashCode();
                parser = listParser;
                break;
            
            // 时间
            case CALENDAR_CLS_HASH:// -861027074;
                                   // java.util.Calendar.class.getName().hashCode();
            case DATE_CLS_HASH:// 65575278;
                               // java.util.Date.class.getName().hashCode();
            case SQL_DATE_CLS_HASH:// 1087757882;
                                   // java.sql.Date.class.getName().hashCode();
            case SQL_TIME_CLS_HASH:// 1088242009;
                                   // java.sql.Time.class.getName().hashCode();
            case SQL_TIMESTAMP_CLS_HASH:// 1252880906;
                                        // java.sql.Timestamp.class.getName().hashCode();
                parser = dateParser;
                break;
            
            // 通用 object
            case OBJECT_CLS_HASH:// 1063877011; Object.class.getName().hashCode();
                parser = objParser;
                break;
        }
        
        if (parser == null)
        {
            if (isImplement(clazz, Collection.class))
                parser = listParser;
            else if (isImplement(clazz, Map.class))
                parser = mapParser;
            else if (clazz.isArray())
                parser = arrayParser;
            else
                parser = objParser;
        }
        
        return parser;
    }
    
    /***
     * 判断当前是否结束
     */
    public boolean isEOF()
    {
        return curType() == JsonLexer.T_EOF;
    }
    
    /***
     * 判断当前是否为 数组（开始符）
     */
    public boolean isArr()
    {
        return curType() == JsonLexer.T_BRACKET_L;
    }
    
    /**
     * 判断当前是否为 数组（结束符）
     */
    public boolean isEndArr()
    {
        return curType() == JsonLexer.T_BRACKET_R;
    }
    
    /***
     * 判断当前是否为 数组（开始符）
     */
    public boolean isObj()
    {
        return curType() == JsonLexer.T_BRACE_L;
    }
    
    /**
     * 判断当前是否为 数组（结束符）
     */
    public boolean isEndObj()
    {
        return curType() == JsonLexer.T_BRACE_R;
    }
    
    /** 判断当前token是否为string型 */
    public boolean isString()
    {
        return this.curType() == T_STRING;
    }
    
    /** 判断当前token是否为逗号型 */
    public boolean isComma()
    {
        return this.curType() == T_COMMA;
    }
    
    /** 判断当前token是否为冒号型 */
    public boolean isColon()
    {
        return this.curType() == T_COLON;
    }
    
    /***
     * 判断上一个Token类型是否为 数组（开始符）
     */
    public boolean prevIsArr()
    {
        return this.prevType == JsonLexer.T_BRACKET_L;
    }
    
    /**
     * 判断上一个Token是否为 数组（结束符）
     */
    public boolean prevIsEndArr()
    {
        return this.prevType == JsonLexer.T_BRACKET_R;
    }
    
    /***
     * 判断上一个Token是否为 数组（开始符）
     */
    public boolean prevIsObj()
    {
        return this.prevType == JsonLexer.T_BRACE_L;
    }
    
    /**
     * 判断上一个Token是否为 数组（结束符）
     */
    public boolean prevIsEndObj()
    {
        return this.prevType == JsonLexer.T_BRACE_R;
    }
    
    /** 判断上一个Token是否为string型 */
    public boolean prevIsString()
    {
        return this.prevType == T_STRING;
    }
    
    /** 判断上一个Token是否为逗号型 */
    public boolean prevIsComma()
    {
        return this.prevType == T_COMMA;
    }
    
    /** 判断上一个Token是否为冒号型 */
    public boolean prevIsColon()
    {
        return this.prevType == T_COLON;
    }
    
    /**
     * json字符串截取
     * @param pos 开始索引
     * @param endPos 结束索引
     * @return
     */
    public String string(int pos, int endPos)
    {
        if (pos < 0 || json == null || pos > json.length() || endPos < pos)
            return null;
        
        return json.substring(pos, endPos > json.length() ? json.length() : endPos);
    }
}
