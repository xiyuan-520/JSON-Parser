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

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

import javax.swing.JEditorPane;

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
    
    public final static char _C_0_ = '0';
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
    public final static String MAX_LONG = "" + Long.MAX_VALUE;
    public final static String MIN_LONG = "" + Long.MIN_VALUE;
    /**************************************************/
    // 数据类型class的哈希吗
    /**************************************************/
    
    // 基本类型
    /**
     * boolean.class.getName().hashCode()<br>
     * = 64711720;
     */
    public final static int BOOLEAN = 64711720;
    /**
     * byte.class.getName().hashCode()<br>
     * = 3039496;
     */
    public final static int BYTE = 3039496;
    /**
     * char.class.getName().hashCode()<br>
     * = 3052374;
     */
    public final static int CHAR = 3052374;
    /**
     * short.class.getName().hashCode()<br>
     * = 109413500;
     */
    public final static int SHORT = 109413500;
    /**
     * int.class.getName().hashCode()<br>
     * = 104431;
     */
    public final static int INT = 104431;
    /**
     * long.class.getName().hashCode()<br>
     * = 3327612;
     */
    public final static int LONG = 3327612;
    /**
     * float.class.getName().hashCode()<br>
     * = 97526364;
     */
    public final static int FLOAT = 97526364;
    /**
     * double.class.getName().hashCode()<br>
     * = -1325958191;
     */
    public final static int DOUBLE = -1325958191;
    
    // 基本封装类型
    /**
     * java.lang.Boolean.class.getName().hashCode()<br>
     * = 344809556;
     */
    public final static int BOOLEAN_O = 344809556;
    /**
     * java.lang.Byte.class.getName().hashCode()<br>
     * = 398507100;
     */
    public final static int BYTE_O = 398507100;
    /**
     * java.lang.Character.class.getName().hashCode()<br>
     * = 155276373;
     */
    public final static int CHAR_O = 155276373;
    /**
     * java.lang.Short.class.getName().hashCode()<br>
     * = -515992664;
     */
    public final static int SHORT_O = -515992664;
    /**
     * java.lang.Integer.class.getName().hashCode()<br>
     * = -2056817302;
     */
    public final static int INT_O = -2056817302;
    /**
     * java.lang.Long.class.getName().hashCode()<br>
     * = 398795216;
     */
    public final static int LONG_O = 398795216;
    /**
     * java.lang.Float.class.getName().hashCode()<br>
     * = -527879800;
     */
    public final static int FLOAT_O = -527879800;
    /**
     * java.lang.Double.class.getName().hashCode()<br>
     * = 761287205;
     */
    public final static int DOUBLE_O = 761287205;
    
    // 基本数组类型
    /**
     * boolean.class.getName().hashCode()<br>
     * = 2911;
     */
    public final static int ARR_BOOLEAN = 2911;
    /**
     * byte.class.getName().hashCode()<br>
     * = 2887;
     */
    public final static int ARR_BYTE = 2887;
    /**
     * char.class.getName().hashCode()<br>
     * = 2888;
     */
    public final static int ARR_CHAR = 2888;
    /**
     * short.class.getName().hashCode()<br>
     * = 2904;
     */
    public final static int ARR_SHORT = 2904;
    /**
     * int.class.getName().hashCode()<br>
     * = 2894;
     */
    public final static int ARR_INT = 2894;
    /**
     * long.class.getName().hashCode()<br>
     * = 2895;
     */
    public final static int ARR_LONG = 2895;
    /**
     * float.class.getName().hashCode()<br>
     * = 2891;
     */
    public final static int ARR_FLOAT = 2891;
    /**
     * double.class.getName().hashCode()<br>
     * = 2889;
     */
    public final static int ARR_DOUBLE = 2889;
    
    // 基本封装类型数组
    /**
     * java.lang.Boolean.class.getName().hashCode()<br>
     * = -499457160;
     */
    public final static int ARR_BOOLEAN_O = -499457160;
    /**
     * java.lang.Byte.class.getName().hashCode()<br>
     * = -2079942674;
     */
    public final static int ARR_BYTE_O = -2079942674;
    /**
     * java.lang.Character.class.getName().hashCode()<br>
     * = -1378128041;
     */
    public final static int ARR_CHAR_O = -1378128041;
    /**
     * java.lang.Short.class.getName().hashCode()<br>
     * = 417147620;
     */
    public final static int ARR_SHORT_O = 417147620;
    /**
     * java.lang.Integer.class.getName().hashCode()<br>
     * = -1935445726;
     */
    public final static int ARR_INT_O = -1935445726;
    /**
     * java.lang.Long.class.getName().hashCode()<br>
     * = -2071011078;
     */
    public final static int ARR_LONG_O = -2071011078;
    /**
     * java.lang.Float.class.getName().hashCode()<br>
     * = 48646404;
     */
    public final static int ARR_FLOAT_O = 48646404;
    /**
     * java.lang.Double.class.getName().hashCode()<br>
     * = -175516795;
     */
    public final static int ARR_DOUBLE_O = -175516795;
    
    // String型&数组
    /**
     * java.lang.String.class.getName().hashCode()<br>
     * = 1195259493;
     */
    public final static int STRING = 1195259493;
    /**
     * java.lang.String.class.getName().hashCode()<br>
     * = 392722245;
     */
    public final static int ARR_STRING = 392722245;
    
    // 哈希表
    /**
     * java.util.Map.class.getName().hashCode()<br>
     * = -1383349348;
     */
    public final static int MAP = -1383349348;
    /**
     * java.util.HashMap.class.getName().hashCode()<br>
     * = -1402722386;
     */
    public final static int HASH_MAP = -1402722386;
    /**
     * java.util.concurrent.ConcurrentMap.class.getName().hashCode()<br>
     * = -1419705938;
     */
    public final static int CONCURRENT_CONCURRENT_MAP = -1419705938;
    /**
     * java.util.concurrent.ConcurrentHashMap.class.getName().hashCode()<br>
     * = 577244352;
     */
    public final static int CONCURRENT_CONCURRENT_HASH_MAP = 577244352;
    /**
     * java.util.Hashtable.class.getName().hashCode()<br>
     * = 639525312;
     */
    public final static int HASHTABLE = 639525312;
    /**
     * java.util.LinkedHashMap.class.getName().hashCode()<br>
     * = 1258621781;
     */
    public final static int LINKED_HASH_MAP = 1258621781;
    /**
     * java.util.TreeMap.class.getName().hashCode()<br>
     * = 1131064094;
     */
    public final static int TREE_MAP = 1131064094;
    
    // 链表
    /**
     * java.util.List.class.getName().hashCode()<br>
     * = 65821278;
     */
    public final static int LIST = 65821278;
    /**
     * java.util.ArrayList.class.getName().hashCode()<br>
     * = -1114099497;
     */
    public final static int ARRAY_LIST = -1114099497;
    /**
     * java.util.LinkedList.class.getName().hashCode()<br>
     * = -1899270121;
     */
    public final static int LINKED_LIST = -1899270121;
    /**
     * java.util.Set.class.getName().hashCode()<br>
     * = -1383343454;
     */
    public final static int SET = -1383343454;
    /**
     * java.util.HashSet.class.getName().hashCode()<br>
     * = -1402716492;
     */
    public final static int HASH_SET = -1402716492;
    
    // 通用Object型&数组
    /**
     * java.lang.Object.class.getName().hashCode()<br>
     * = 1063877011;
     */
    public final static int OBJECT = 1063877011;
    /**
     * java.lang.Object.class.getName().hashCode()<br>
     * = 614832599;
     */
    public final static int ARR_OBJECT = 614832599;
    
    // 时间
    /**
     * java.util.Calendar.class.getName().hashCode()<br>
     * = -861027074;
     */
    public final static int CALENDAR = -861027074;
    /**
     * java.util.Date.class.getName().hashCode()<br>
     * = 65575278;
     */
    public final static int DATE = 65575278;
    /**
     * java.sql.Time.class.getName().hashCode()<br>
     * = 1088242009;
     */
    public final static int SQL_TIME = 1088242009;
    /**
     * java.sql.Date.class.getName().hashCode()<br>
     * = 1087757882;
     */
    public final static int SQL_DATE = 1087757882;
    /**
     * java.sql.Timestamp.class.getName().hashCode()<br>
     * = 1252880906;
     */
    public final static int SQL_TIMESTAMP = 1252880906;
    
    // java.util.concurrent.atomic
    /**
     * java.util.concurrent.atomic.AtomicBoolean.class.getName().hashCode()<br>
     * = -1210542319;
     */
    public final static int CONCURRENT_ATOMIC_ATOMIC_BOOLEAN = -1210542319;
    /**
     * java.util.concurrent.atomic.AtomicInteger.class.getName().hashCode()<br>
     * = 682798119;
     */
    public final static int CONCURRENT_ATOMIC_ATOMIC_INTEGER = 682798119;
    /**
     * java.util.concurrent.atomic.AtomicLong.class.getName().hashCode()<br>
     * = -968709069;
     */
    public final static int CONCURRENT_ATOMIC_ATOMIC_LONG = -968709069;
    /**
     * java.util.concurrent.atomic.AtomicIntegerArray.class.getName().hashCode()<br>
     * = 1047484018;
     */
    public final static int CONCURRENT_ATOMIC_ATOMIC_INTEGER_ARRAY = 1047484018;
    /**
     * java.util.concurrent.atomic.AtomicLongArray.class.getName().hashCode()<br>
     * = -1353089562;
     */
    public final static int CONCURRENT_ATOMIC_ATOMIC_LONG_ARRAY = -1353089562;
    
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
        return clazz.isPrimitive();
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
            case BOOLEAN:// = 64711720;
                cls = (Class<T>) Boolean.class;
                break;
            case BYTE:// = 3039496;
                cls = (Class<T>) Byte.class;
                break;
            case CHAR:// = 3052374;
                cls = (Class<T>) Character.class;
                break;
            case SHORT:// = 109413500;
                cls = (Class<T>) Short.class;
                break;
            case INT:// = 104431;
                cls = (Class<T>) Integer.class;
                break;
            case LONG:// = 3327612;
                cls = (Class<T>) Long.class;
                break;
            case FLOAT:// = 97526364;
                cls = (Class<T>) Float.class;
                break;
            case DOUBLE:// = -1325958191;
                cls = (Class<T>) Double.class;
                break;
            default:
                cls = clazz;
                break;
        }
        
        return cls;
    }
    
    /**
     * 判断是否为空 只判断 s = null 或者 s.length = 0
     * @param s
     */
    public static boolean isEmpty(String s)
    {
        return (s == null || s.length() == 0);
    }
    
    /****
     * 判断是否全部为空白，当s = null 或者 s.length = 0 或者 全部是空白 时返回true
     * @param s 原字符串
     * @return true表示全部是空白
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
     * @param s 原字符串
     * @return 去除空白后的字符串
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
                    if (quotation == DB_QUOTE)
                        strb.append("\\\"");
                    else
                        strb.append(c);
                    break;
                case QUOTE:
                    
                    if (quotation == QUOTE)
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
    public static String removeEscapeChar(String str, boolean isJsonString)
    {
        if(isJsonString)
            return str;
        
        if (str == null)
            return null;
        
        StringBuilder strb = new StringBuilder(str.length());
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
                    case '\'':
                    case '\"':
                    {
                        strb.append(c);
                        break;
                    }
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
     * 去除JSON键和值的前后成对引号 注意：字符串首尾不能 有空格
     * @param str 原字符串
     * @return 去除成对引号之后的字符串
     */
    public static String removeStartEndQuotation(String str)
    {
        if (str == null)
            return null;
        int end = str.length() - 1;
        if (str.length() >= 2 && str.charAt(0) == DB_QUOTE && str.charAt(end) == DB_QUOTE)
            return str.substring(1, end);// 有双引号删除退出
            
        // 没有双引号则判断单引号
        if (str.length() >= 2 && str.charAt(0) == QUOTE && str.charAt(end) == QUOTE)
            return str.substring(1, end);// 有双引号删除退出
            
        return str;
    }
    
    /*******************************************/
    // 以下两个静态方法是 获取静态泛型具体类型的方法
    /*******************************************/
    
    /**
     * 获取泛型类型具体类型的方法
     * @param type 字段类
     * @param i 泛型索引位置
     */
    @SuppressWarnings("rawtypes")
    public static Class getClass(Type type, int i)
    {
        if (type instanceof ParameterizedType)
            return getGenericClass((ParameterizedType) type, i);
        else if (type instanceof TypeVariable)
            return (Class) getClass(((TypeVariable) type).getBounds()[0], 0);
        else
            return (Class) type;
    }
    
    /**
     * 获取泛型类型具体类型的方法
     * @param parameterizedType 字段类
     * @param i 泛型索引位置
     */
    @SuppressWarnings("rawtypes")
    public static Class<?> getGenericClass(ParameterizedType parameterizedType, int i)
    {
        Object genericClass = parameterizedType.getActualTypeArguments()[i];
        if (genericClass instanceof ParameterizedType)
            return (Class) ((ParameterizedType) genericClass).getRawType();
        else if (genericClass instanceof GenericArrayType)
            return (Class) ((GenericArrayType) genericClass).getGenericComponentType();
        else if (genericClass instanceof TypeVariable)
            return (Class) getClass(((TypeVariable) genericClass).getBounds()[0], 0);
        else
            return (Class) genericClass;
    }
    
    /***********************************************************************/
    // 以下是类的定义及对象的调用方法
    /***********************************************************************/
    private BaseParser baseParser;
    private ArrayParser arrayParser;
    private ListParser listParser;
    private MapParser mapParser;
    private DateParser dateParser;
    private ObjectParser objParser;
    
    private int pos = -1;
    private String json = null;
    private List<Byte> contextLs = new ArrayList<Byte>();// 当前token 所在的作用范围类型
    private byte curType = T_UNKNOWN;// 当前token 作用域类型
    private byte prevType = T_UNKNOWN;// 当前上一个token作用域类型
    private char ch;// 循环的当前字符
    private String value = NULL;
    
    private int scopeIndex = -1;
    private char quote = 0;// 字符串开始的引号值
    
    // private int length = 0;//当前token 字符串长度
    
    public JsonLexer(String input, int level)
    {
        this.json = trim(input);
        this.value = null;
        this.baseParser = new BaseParser(this, level);
        this.arrayParser = new ArrayParser(this, level);
        this.listParser = new ListParser(this, level);
        this.mapParser = new MapParser(this, level);
        this.dateParser = new DateParser(this, level);
        this.objParser = new ObjectParser(this, level);
        initParserMap();
    }
    
    private HashMap<Class<?>, JsonParser> parserMap = new HashMap<Class<?>, JsonParser>(200);
    
    private void initParserMap()
    {
        
        // 八大基本类型+封装类+String
        parserMap.put(boolean.class, baseParser);
        parserMap.put(byte.class, baseParser);
        parserMap.put(char.class, baseParser);
        parserMap.put(short.class, baseParser);
        parserMap.put(int.class, baseParser);
        parserMap.put(long.class, baseParser);
        parserMap.put(float.class, baseParser);
        parserMap.put(double.class, baseParser);
        parserMap.put(Boolean.class, baseParser);
        parserMap.put(Byte.class, baseParser);
        parserMap.put(Character.class, baseParser);
        parserMap.put(Short.class, baseParser);
        parserMap.put(Integer.class, baseParser);
        parserMap.put(Long.class, baseParser);
        parserMap.put(Float.class, baseParser);
        parserMap.put(Double.class, baseParser);
        parserMap.put(String.class, baseParser);
        parserMap.put(AtomicBoolean.class, baseParser);
        parserMap.put(AtomicInteger.class, baseParser);
        parserMap.put(AtomicLong.class, baseParser);
        
        parserMap.put(boolean[].class, arrayParser);
        parserMap.put(byte[].class, arrayParser);
        parserMap.put(char[].class, arrayParser);
        parserMap.put(short[].class, arrayParser);
        parserMap.put(int[].class, arrayParser);
        parserMap.put(long[].class, arrayParser);
        parserMap.put(float[].class, arrayParser);
        parserMap.put(double[].class, arrayParser);
        parserMap.put(Boolean[].class, arrayParser);
        parserMap.put(Byte[].class, arrayParser);
        parserMap.put(Character[].class, arrayParser);
        parserMap.put(Short[].class, arrayParser);
        parserMap.put(Integer[].class, arrayParser);
        parserMap.put(Long[].class, arrayParser);
        parserMap.put(Float[].class, arrayParser);
        parserMap.put(Double[].class, arrayParser);
        parserMap.put(String[].class, arrayParser);
        parserMap.put(Object[].class, arrayParser);
        parserMap.put(AtomicIntegerArray.class, arrayParser);
        parserMap.put(AtomicLongArray.class, arrayParser);
        // 哈希表
        parserMap.put(Map.class, mapParser);
        parserMap.put(HashMap.class, mapParser);
        parserMap.put(ConcurrentMap.class, mapParser);
        parserMap.put(ConcurrentHashMap.class, mapParser);
        parserMap.put(Hashtable.class, mapParser);
        parserMap.put(LinkedHashMap.class, mapParser);
        parserMap.put(TreeMap.class, mapParser);
        
        // 链表
        parserMap.put(Collection.class, listParser);
        parserMap.put(List.class, listParser);
        parserMap.put(ArrayList.class, listParser);
        parserMap.put(LinkedList.class, listParser);
        parserMap.put(Set.class, listParser);
        parserMap.put(HashSet.class, listParser);
        parserMap.put(TreeSet.class, listParser);
        
        // 时间
        parserMap.put(Calendar.class, dateParser);
        parserMap.put(Date.class, dateParser);
        parserMap.put(java.sql.Date.class, dateParser);
        parserMap.put(java.sql.Time.class, dateParser);
        parserMap.put(java.sql.Timestamp.class, dateParser);
        
        // 通用 object
        parserMap.put(Object.class, objParser);
    }
    
    public BaseParser BaseParser()
    {
        return this.baseParser;
    }
    
    public ArrayParser ArrayParser()
    {
        return this.arrayParser;
    }
    
    public ListParser ListParser()
    {
        return this.listParser;
    }
    
    public MapParser MapParser()
    {
        return this.mapParser;
    }
    
    public DateParser DateParser()
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
    
    private void setCurrentType(byte type)
    {
        this.prevType = this.curType;
        this.curType = type;
    }
    
    public JsonLexer naxtToken()
    {
        this.value = NULL;
        if (!hasNext())
        {
            this.setCurrentType(T_EOF);
            this.scopeIndex = -1;
            this.ch = 0;
            return this;
        }
        
        this.pos = nextPos();
        this.ch = json.charAt(this.pos);
        if (isWhitespace(ch))
            return naxtToken();// 空白字符开头
            
        this.setCurrentType(curType());// 设置当前为空类型,
        
        // json 数据格式只有两种，1.对象使用{} 括起，2.数组[] 括起。所以非 // {或者[开头的数据
        // if (pos == 0 && BRACE_L != ch && ch != BRACKET_L)
        // return naxtToken();
        byte contextType = getContextType();
        
        if (this.pos != -1 && contextType == T_UNKNOWN && ch != BRACE_L && ch != BRACKET_L)// 作用域栈已经全部退出了,
            throw new JsonException("Json 数据必须包含在'{}'或者'[]'里面，pos:" + pos);
        
        if (contextType == T_UNKNOWN && ch != BRACE_L && ch != BRACKET_L)
            throw new JsonException("Json 数据必须以 '{' 或者 '['开头，pos:" + pos);
        
        switch (ch)
        {
            case BRACE_L:
            {// { 开头 上一个token类型必须为,空，‘{’，‘[’,‘,’,‘:’
            
                this.value = String.valueOf(ch);
                this.setContextType(T_BRACE_L);// 设置作用域类型
                this.setCurrentType(T_BRACE_L);// 设置当前类型
                break;
            }
            case BRACE_R:
            {
                
                if (contextType == T_BRACKET_L)// 判断当前作用域为'[' 但接数符 为'}'
                    throw new JsonException("Json 数组字符串必须以 ']' 结束，pos:" + pos);
                
                removeScope();// 设置结束当前context
                contextType = getContextType();
                this.value = String.valueOf(ch);
                setCurrentType(T_BRACE_R);// 设置当前为结束 } 类型
                break;
            }
            case BRACKET_L:
            {// [ 开头 上一个token类型必须为,空，‘{’，‘[’,‘,’,‘:’
            
                value = String.valueOf(ch);
                setContextType(T_BRACKET_L);
                setCurrentType(T_BRACKET_L);
                break;
            }
            case BRACKET_R:
            {
                
                if (contextType == T_BRACE_L)// 判断当前作用域为'{' 但接数符 为']'
                    throw new JsonException("Json 数组字符串必须以 '}' 结束，pos:" + pos);
                
                removeScope();// 设置结束当前context
                contextType = getContextType();
                value = String.valueOf(ch);
                setCurrentType(T_BRACE_R);// 设置当前为结束 } 类型
                
                break;
            }
            case COMMA:// 上一个token 不能为,
            {// ‘,’ 前面token 必须是 对象结束 或者 数组结束 或者字符串
            
                if (prevIsComma())
                {// 上一个是逗号
                    naxtToken();
                    return this;// 上一个token类型是逗号
                }
                
                // 处理上一个是 冒号 当前为逗号，则当前值为null 列：{dd:,} 被整理 后为 {dd:null,}
                if (prevIsColon())
                {
                    this.setCurrentType(T_STRING);
                    this.pos--;// 设置下一个token起点位置为当前位置-1
                }
                else
                {
                    this.setCurrentType(T_COMMA);// 设置当前为‘，’类型
                    this.value = String.valueOf(ch);
                }
                break;
            }
            case COLON:
            {
                
                if (prevIsString() || prevIsEndArr() || prevIsEndObj())
                {// 上一个token是String 或者 ]、}， 则当前是冒号,说明这一段数据是已 键值对形式数据，支持数组，对象类型做key
                    this.setCurrentType(T_COLON);// 设置当前为‘：’类型
                    this.value = String.valueOf(ch);
                }
                else
                {// 否则当string值处理
                    this.setCurrentType(T_STRING);// 设置当前为string类型
                    this.buildStringToken(contextType);
                }
                
                break;
            }
            default:
            {
                this.setCurrentType(T_STRING);// 设置当前为string类型
                this.buildStringToken(contextType);
                break;
            }
        }
        return this;
    }
    
    /**
     * 生成从当前位置开始的 一个字符串token
     * @param contextType
     */
    private void buildStringToken(byte contextType)
    {
        int end = 0;
        int start = 0;
        this.quote = (char) 0;
        if (contextType == T_UNKNOWN)// 当前没有作用域 则视为String字符 一直到json末尾
        {
            start = this.pos;
            this.pos = json.length();
        }
        
        for (; this.pos < json.length(); this.pos++)
        {
            this.ch = json.charAt(pos);
            if (quote == (char) 0 && JsonLexer.isWhitespace(this.ch))
                continue;// 字符串未开始,并且为空白
                
            if (this.quote == (char) 0)
            {// 非空白字符开始
                this.quote = ch;// 记录开始符
                start = pos;
                continue;
            }
            
            // 非 单双引号开始 则必须
            if (quote != DB_QUOTE && quote != QUOTE)
            {//
                // 1. 碰到逗号必须结束
                if (ch == COMMA || ch == COLON)
                {
                    end = pos;// string结束不包含当前字符
                    this.pos--;// 当前结束符不记录到string值
                    break;
                }
                
                // 2. 当前作用域为 { 且碰到 '}'则结束
                if (contextType == T_BRACE_L && ch == BRACE_R)
                {
                    end = pos;// string结束不包含当前字符
                    this.pos--;// 当前结束符不记录到string值
                    break;
                }
                
                // 3. 当前作用域为 [ 且碰到 ']'则结束
                if (contextType == T_BRACKET_L && ch == T_BRACKET_R)
                {// 当前作用域为 [ 且碰到 ']'则结束
                
                    end = pos;// string结束不包含当前字符
                    this.pos--;// 当前结束符不记录到string值
                    break;
                }
            }
            else
            {// 单双引号开始
             // 查找结束符，引号开头&当前是引号 & 上一个字符不是不是转义符
                if ((ch == QUOTE || ch == DB_QUOTE) && ch == quote && json.charAt(pos - 1) != '\\')
                {
                    end = pos+1;
                    break;
                }
            }
        }
        
        // 需要需要去空格
        for (; end > 0; end--)
        {
            if (!isWhitespace(json.charAt(end - 1)))
                break;
        }
        if (end == start)
            return;
       
        this.value = json.substring(start, end);
        
    }
    
    /**
     * 获取当前作用于 注意：第一个开始符返回0，最后一个返回-1表示结束
     */
    public int scope()
    {
        return scopeIndex;
    }
    
    public byte getContextType()
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
    public void setContextType(Byte scope)
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
        parser = parserMap.get(clazz);
        if (parser != null)
            return parser;
        
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
            
            parserMap.put(clazz, parser);
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
     * 获取Json数据源
     * @return
     */
    public String getSource()
    {
        return json;
    }
}
