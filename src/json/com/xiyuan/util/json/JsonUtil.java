
package com.xiyuan.util.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.xiyuan.util.json.parser.ArrayParser;
import com.xiyuan.util.json.parser.BaseParser;
import com.xiyuan.util.json.parser.DateParser;
import com.xiyuan.util.json.parser.ListParser;
import com.xiyuan.util.json.parser.MapParser;
import com.xiyuan.util.json.parser.ObjectParser;

/***
 * JSON 主程序，对象转换成JSON和JSON转换成对象
 *
 * @version v1.0.0 @author lgz 2019年8月31日 新建与整理
 */
public final class JsonUtil implements Serializable
{

    private static final long serialVersionUID = 1L;
    public final static byte ZERO       = 0;
    /** 左大括号 0 = { */
    public final static byte T_BRACE_L = 0;// "{"
    /** 右大中括号 1 = } */
    public final static byte T_BRACE_R = 1;// "}"
    /** 左中括号 2 = [ */
    public final static byte T_BRACKET_L = 2;// "["
    /** 右中括号 3 = ] */
    public final static byte T_BRACKET_R = 3;// "]"
    /** 冒号 4 = : */
    public final static byte T_COLON = 4;// ":"
    /** 逗号 5 = , */
    public final static byte T_COMMA = 5;// ","
    /** 字符串键 6 */
    public final static byte T_VALUE = 6;// String值

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

    /** null " */
    public final static String NULL = "null";
    /** "true" */
    public final static String TRUE = "true";
    /** "false" */
    public final static String FALSE = "false";
    /** 空对象 {} */
    public final static String EMPTY_OBJ = "{}";
    /** 空数组 [] */
    public final static String EMPTY_ARR = "[]";

    public final static String INTEGER = "^(0|[\\+\\-]?[1-9]\\d*)$";// 整数，支持正负数
    public final static String FLOAT = "^(0|[\\+\\-]?[1-9]\\d*)(\\.\\d+)?$";// 浮点值，支持多位小数点，支持正负数

    private static JsonParser baseParser = new BaseParser();
    private static JsonParser arrarParser = new ArrayParser();
    private static JsonParser listParser = new ListParser();
    private static JsonParser mapParser = new MapParser();
    private static JsonParser dateParser = new DateParser();
    private static JsonParser objParser = new ObjectParser();
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

    /** 根据类获取解析器 */
    public static JsonParser getParser(Class<?> clazz)
    {
        JsonParser parser = null;
        switch (clazz.getName().hashCode())
        {

            // 八大基本类型+封装类+String
            case BOOL_CLS_HASH:// = 64711720;//boolean.class.getName().hashCode();
            case BOOL_OBJ_CLS_HASH:// = 344809556;//Boolean.class.getName().hashCode();
            case BYTE_CLS_HASH:// = 3039496;//byte.class.getName().hashCode();
            case BYTE_OBJ_CLS_HASH:// = 398507100;//Byte.class.getName().hashCode();
            case CHAR_CLS_HASH:// :// = 3052374;// char.class.getName().hashCode();
            case CHAR_OBJ_CLS_HASH:// = 155276373;//Character.class.getName().hashCode();
            case SHORT_CLS_HASH:// = 109413500;//short.class.getName().hashCode();
            case SHORT_OBJ_CLS_HASH:// = -515992664;//Short.class.getName().hashCode();
            case INT_CLS_HASH:// = 104431;//int.class.getName().hashCode();
            case INT_OBJ_CLS_HASH:// = -2056817302;//Integer.class.getName().hashCode();
            case LONG_CLS_HASH:// = 3327612;//long.class.getName().hashCode();
            case LONG_OBJ_CLS_HASH:// = 398795216;//Long.class.getName().hashCode();
            case FLOAT_CLS_HASH:// = 97526364;//float.class.getName().hashCode();
            case FLOAT_OBJ_CLS_HASH:// = -527879800;//Float.class.getName().hashCode();
            case DOUBLE_CLS_HASH:// = -1325958191;//double.class.getName().hashCode();
            case DOUBLE_OBJ_CLS_HASH:// = 761287205;//Double.class.getName().hashCode();
            case STRING_CLS_HASH:// = 1195259493;//String.class.getName().hashCode();
                parser = baseParser;
                break;

            // 八大基本类型+封装类+String 的数组
            case DOUBLE_ARR_CLS_HASH:// = 2889;//double[].class.getName().hashCode();
            case DOUBLE_OBJ_ARR_CLS_HASH:// = -175516795;//Double[].class.getName().hashCode();
            case FLOAT_ARR_CLS_HASH:// = 2891;//float[].class.getName().hashCode();
            case FLOAT_OBJ_ARR_CLS_HASH:// = 48646404;//Float[].class.getName().hashCode();
            case LONG_ARR_CLS_HASH:// = 2895;//long[].class.getName().hashCode();
            case LONG_OBJ_ARR_CLS_HASH:// = -2071011078;//Long[].class.getName().hashCode();
            case INT_ARR_CLS_HASH:// = 2894;//int[].class.getName().hashCode();
            case INT_OBJ_ARR_CLS_HASH:// = -1935445726;//Integer[].class.getName().hashCode();
            case SHORT_ARR_CLS_HASH:// = 2904;//short[].class.getName().hashCode();
            case SHORT_OBJ_ARR_CLS_HASH:// = 417147620;//Short[].class.getName().hashCode();
            case CHAR_ARR_CLS_HASH:// = 2888;//char[].class.getName().hashCode();
            case CHAR_OBJ_ARR_CLS_HASH:// = -1378128041;//Character[].class.getName().hashCode();
            case BYTE_ARR_CLS_HASH:// = 2887;//byte[].class.getName().hashCode();
            case BYTE_OBJ_ARR_CLS_HASH:// = -2079942674;//Byte[].class.getName().hashCode();
            case BOOL_ARR_CLS_HASH:// = 2911;//boolean[].class.getName().hashCode();
            case BOOL_OBJ_ARR_CLS_HASH:// = -499457160;//Boolean[].class.getName().hashCode();
            case STRING_ARR_CLS_HASH:// = 392722245;//String[].class.getName().hashCode();
            case OBJECT_ARR_CLS_HASH:// = 614832599;// Object[].class.getName().hashCode();
                parser = arrarParser;
                break;

            // 哈希表
            case MAP_CLS_HASH:// = -1383349348;// Map.class.getName().hashCode();
            case HASHMAP_CLS_HASH:// = -1402722386;// HashMap.class.getName().hashCode();
            case CONCURRENTMAP_CLS_HASH:// =-1419705938;// ConcurrentMap.class.getName().hashCode();
            case CONCURRENTHASHMAP_CLS_HASH:// = 577244352;// ConcurrentHashMap.class.getName().hashCode();
            case HASHTABLE_CLS_HASH:// = 639525312;// Hashtable.class.getName().hashCode();
            case LINKEDHASHMAP_CLS_HASH:// = 1258621781;// LinkedHashMap.class.getName().hashCode();
            case TREEMAP_CLS_HASH:// = 1131064094;// TreeMap.class.getName().hashCode();
                parser = mapParser;
                break;

            // 链表
            case LIST_CLS_HASH:// = 65821278;// List.class.getName().hashCode();
            case ARRAYLIST_CLS_HASH:// = -1114099497;// ArrayList.class.getName().hashCode();
            case LINKEDLIST_CLS_HASH:// = -1899270121;// LinkedList.class.getName().hashCode();
            case SET_CLS_HASH:// =-1383343454;// Set.class.getName().hashCode();
            case HASHSET_CLS_HASH:// =-1402716492;// HashSet.class.getName().hashCode();
                parser = listParser;
                break;

            // 时间
            case CALENDAR_CLS_HASH:// = -861027074;// java.util.Calendar.class.getName().hashCode();
            case DATE_CLS_HASH:// = 65575278;// java.util.Date.class.getName().hashCode();
            case SQL_DATE_CLS_HASH:// = 1087757882;// java.sql.Date.class.getName().hashCode();
            case SQL_TIME_CLS_HASH:// = 1088242009;// java.sql.Time.class.getName().hashCode();
            case SQL_TIMESTAMP_CLS_HASH:// = 1252880906;// java.sql.Timestamp.class.getName().hashCode();
                parser = dateParser;
                break;

            // 通用 object
            case OBJECT_CLS_HASH:// = 1063877011;// Object.class.getName().hashCode();
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
                parser = arrarParser;
            else
                parser = objParser;
        }

        return parser;
    }

    /**
     * 解析字符串并生成token并json字符串的首个token<br>
     * 举例：字符串{"a":"a1", "b":[1,2,3]} 则返回 { 对应的token<br>
     * 备注：其中每个token可能有多个子token<br>
     * token { 的子token 有 "a"， :， "a1"， , ，"b" : , [<br>
     * 其中 1，,，2，3为 token 的子token<br>
     * ] 为当前作用范围 [ 的结束token<br>
     * } 为当前作用范围 { 的结束token<br>
     * 
     * @param json json字符串
     * @return 返回json字符串的首个token 值
     */

    public static Token getTokens(String json)
    {
        return getTokens(json, null);
    }

    /**
     * 解析字符串并生成token并json字符串的首个token<br>
     * 举例：字符串{"a":"a1", "b":[1,2,3]} 则返回 { 对应的token<br>
     * 备注：其中每个token可能有多个子token<br>
     * token { 的子token 有 "a"， :， "a1"， , ，"b" : , [<br>
     * 其中 1，,，2，3为 token 的子token<br>
     * ] 为当前作用范围 [ 的结束token<br>
     * } 为当前作用范围 { 的结束token<br>
     * 
     * @param json  json字符串
     * @param field 指定字段
     * @return 返回json字符串的首个token 值,如果是指定字段 token.getElements()[0] = root,token.getElements()[1] = field的上一个token，token.getElements()[2] = field 的value <br>
     *         举例：json = {a:{s:b}, cc:ss} <br>
     *         1. field = a时 token.getElements()[0] = '{' token.getElements()[1] = {s:b} <br>
     *         1. field = cc:ss时 token.getElements()[0] = ',' token.getElements()[1] = ss
     */
    private static Token getTokens(String json, String field)
    {
        int maxLenth = json == null ? 0 : json.length();
        for (; maxLenth > 0;)
        {// 剔除右边空白
            char ch = json.charAt(maxLenth - 1);
            if (!isWhitespace(ch))
                break;

            maxLenth--;
        }
        if (maxLenth <= 0)
            return null;

        // 1.arrNum 数组数量，
        // 2.objNum 对象数量，
        // 3.scope当前token所属范围，-1=初始时，0=在对象{}里，2 =在数组[]里;
        // 4.valueType当前token所属范围，-1=初始时，参见 tokenType，
        // 5.root 跟节点，prevToken 上一个token, parent 所在范围的token,keyScope=字段所在token域
        int arrNum = 0, objNum = 0, valueType = -1;
        char end_char = json.charAt(maxLenth - 1);
        Token root = null, current = null, prevToken = null, parent = null, keyScope = null;// , keyToken = null; keyToken 不需要了因为 既然ke所在的keyScope与范围都找到了跟定keyToken 也找到了
        if (field != null && json.indexOf(field) == -1)
            return null;

        List<Token> starts = new ArrayList<Token>();
        for (int pos = 0; pos < maxLenth; pos++)
        {
            char ch = json.charAt(pos);
            if (root == null && isWhitespace(ch))
                continue;// 空白字符开头
            if (root == null && BRACE_L != ch && ch != BRACKET_L)
                continue;// json 数据格式只有两种，1.对象使用{} 括起，2.数组[] 括起。所以非 {或者[开头的数据

            if (current != null)
                current = null;

            switch (ch)
            {
                case BRACE_L:
                {
                    current = Token.newToken(JsonUtil.T_BRACE_L, pos);
                    objNum++;

                    starts.add(current);
                    if (root == null)
                    {
                        root = current;
                        parent = root;
                    }
                    else
                    {
                        parent.addToken(current, true);
                        parent = current;
                    }

                    // if (field != null && keyToken != null && valueType == -1)
                    if (field != null && keyScope != null && valueType == -1)
                        valueType = T_BRACE_L; // 指定字段值为对象是且当前为结束对象

                    if (root.type() == T_BRACE_L && end_char != BRACE_R)
                        return root;

                    break;
                }
                case BRACE_R:
                {
                    if (objNum > 0)
                    {
                        current = Token.newToken(T_BRACE_R, pos);
                        objNum--;
                        int ind = starts.size() - 1;
                        if (!starts.isEmpty() && starts.get(ind).type() == T_BRACE_L)
                            starts.remove(ind);

                        parent.end(pos);// 设置结束位置
                        if (starts.isEmpty())
                            parent = root;
                        else
                            parent = starts.get(starts.size() - 1);

                        // if (field != null && keyScope != null && keyToken != null && parent.equals(keyScope))
                        if (field != null && keyScope != null && keyScope != null && parent.equals(keyScope))
                            return parent; // 指定字段值为对象是且当前为结束对象
                    }
                    break;
                }
                case BRACKET_L:
                {
                    current = Token.newToken(T_BRACKET_L, pos);
                    arrNum++;
                    starts.add(current);
                    if (root == null)
                    {// 第一个 token
                        root = current;
                        parent = root;
                    }
                    else
                    {// 添加子元素
                        parent.addToken(current, true);
                        parent = current;
                    }

                    // if (field != null && keyToken != null && valueType == -1)
                    if (field != null && keyScope != null && valueType == -1)
                        valueType = T_BRACKET_L; // 指定字段值类型数组

                    if (root.type() == T_BRACKET_L && end_char != BRACKET_R)
                        return root;

                    break;
                }
                case BRACKET_R:
                {
                    if (arrNum > 0)
                    {
                        current = Token.newToken(T_BRACKET_R, pos);
                        arrNum--;
                        int ind = starts.size() - 1;
                        if (!starts.isEmpty() && starts.get(ind).type() == T_BRACKET_L)
                            starts.remove(ind);

                        parent.end(pos);// 设置结束位置
                        if (starts.isEmpty())
                            parent = root;
                        else
                            parent = starts.get(starts.size() - 1);

                        // if (field != null && keyScope != null && keyToken != null && parent.equals(keyScope))
                        if (field != null && keyScope != null && parent.equals(keyScope))
                            return parent; // 指定字段值为数组是且当前为结束数组
                    }
                    break;
                }
                case COMMA:// 上一个token 不能为,
                {// 前面token 必须是 对象结束 或者 数组结束 或者字符串
                    if (prevToken != null && prevToken.type() == T_COMMA)
                        continue;

                    current = Token.newToken(T_COMMA, pos);
                    current.end(pos);
//                    parent.end(pos);// 设置结束位置
                    break;
                }
                case COLON:
                {
                    if (prevToken != null && (prevToken.type() == T_COMMA || prevToken.type() == T_COLON))
                    {// 如果上一个token为 逗号，则当前 冒号为键的开始部分 列{a::ss:sdcsdcs} 其中 :ss:sdcsdcs 为值

                        int length = getStringTokenLength(json, pos, parent.type(), prevToken);
                        current = length == 0 ? null : Token.newToken(T_VALUE, pos);// getStringToken(json, pos, scope, prevToken);
                        pos += (length - 1);
                        if (current != null)
                            current.end(pos);// 设置结束位置
                    }
                    else if (prevToken != null && prevToken.type() != T_COLON && prevToken.type() != T_BRACE_L && prevToken.type() != T_BRACKET_L)
                    {
                        current = Token.newToken(T_COLON, pos);
                        current.end(pos);// 设置结束位置
                    }

                    break;
                }
                default:
                {
                    if (isWhitespace(ch))
                        continue;

                    int length = getStringTokenLength(json, pos, parent.type(), prevToken);
                    current = length == 0 ? null : Token.newToken(T_VALUE, pos);// getStringToken(json, pos, scope, prevToken);
                    pos += length - 1;
                    current.end(pos);// 设置结束位置
                }
            }

            if (prevToken != null && current != null && prevToken.type() == current.type() && prevToken.type() == T_COMMA)
                continue;// 上一个是是逗号 当前也是逗号

            if (prevToken != null && current != null && prevToken.type() == T_COLON && current.type() == T_COMMA)
            {// 处理上一个是 冒号 当前为逗号，则当前值为null 列：{dd:,} 被整理 后为 {dd:null,}
                prevToken = current;

                if (parent != null && field == null)
                {// 非指定字段查询放入父级token列表节省资源开销
                    current = Token.newToken(T_VALUE, pos);// 上一个是 冒号 当前是逗号 则当前是空值
                    parent.addToken(current, true);
                    parent.addToken(prevToken, true);
                    current.end(-1);// 小于0 表示该值为 null
                }
                
                if (field != null && keyScope != null)
                {//找到字段
                    keyScope.addToken(current, false);
                    return keyScope;
                }
            }
            else
            {
                // prevToken = current;//自地方注释因为指定字段是要保存字段的前一个token所以放到每个括号赋值
                if (parent == null || current == null)
                {
                    prevToken = current;
                    continue;
                }

                if (!(current.type() == T_COLON || current.type() == T_COMMA || current.type() == T_VALUE))
                {
                    prevToken = current;
                    continue;
                }

                if (field == null)
                {// 非指定字段查找{
                    prevToken = current;
                    parent.addToken(current, true);
                    continue;
                }

                /********************************/
                // 以下是查找字段&字段值
                /********************************/

                // 一下是指定字段相关操作
                if (keyScope == null && current.type() != T_VALUE)
                {
                    prevToken = current;
                    continue;// 没找到字段 并且当前不是字符串
                }

                if (keyScope == null && prevToken.type() != T_COLON && field.equals(removeStartEndQuotation(current.toString(json))))
                {// 找到字段，但是上一个token 不是冒号
                    keyScope = parent;
                    keyScope.addToken(prevToken, false);
                    prevToken = current;
                    continue;
                }

                prevToken = current;
                if (keyScope != null && current.type() == T_VALUE)
                {// 已经找到字段

                    if (valueType == T_BRACE_L || valueType == T_BRACKET_L)
                    {// 值类型是对象或者数组
                        parent.addToken(current, true);
                        continue;
                    }

                    // if (valueType == -1 && current.type() == T_COLON)
                    // continue;//{arr:{dddd:ccc}} 字段 = arr 当前 = ：

                    parent.addToken(current, true);
                    return parent;
                }
            }
        }

        if (root != null)
            root.end(json.length() - 1);
        return field == null ? root : keyScope;
    }

    /**
     * 生成从当前位置开始的 一个字符串token
     * 
     * @param json      json 字符串
     * @param pos       读取位置
     * @param scope     token所属范围0=初始值,1=obj,2=arr
     * @param prevToken 上一个token的类型
     * @return 返回String token 的字符长度
     */

    private static int getStringTokenLength(String json, int pos, int scope, Token prevToken)
    {
        int length = 0;
        char quote = 0;// 字符串开始的引号值
        for (; pos < json.length(); pos++, length++)
        {
            char ch = json.charAt(pos);
            if (quote == 0)
            {
                quote = ch;// 记录开始符
                continue;
            }

            // 查找结束符， 非引号字符串结束的字符
            if (quote > 0 && quote != DB_QUOTE && quote != QUOTE && (ch == COLON || ch == COMMA || ch == BRACE_R || ch == BRACKET_R))
            {
                if (scope == 2 && ch == COLON)// 如果是数组当前是冒号&所属范围是数组，则当前冒号为值
                    continue;

                if (ch == COLON && prevToken != null && prevToken.type() == T_COLON)// 当前为冒号 上一个token的类型为冒号，则档前为值
                    continue;// {a::sss:sdcsdcs, b:wwww} 其中 sss:sdcsdcs 为值
                else
                    return length;// 非引号开始 并且有结束负号
            }

            // 查找结束符，引号开头&当前是引号 & 上一个字符不是不是转义符
            if ((ch == QUOTE || ch == DB_QUOTE) && ch == quote && json.charAt(pos - 1) != '\\')
                return ++length;// 包含当前字符
        }

        if (pos == json.length())
            return --length;// 遍历结束索引json最大索引
        else
            return length;
    }

    /**
     * 增加JSON中的转义字符，使用双引号时，单引号不转义，使用单引号时双引号不转义，不使用引号时都转义
     * 
     * @param str       原字符串
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

        if (str.length() >= 2 && str.startsWith("\"") && str.endsWith("\""))
        {// 有双引号删除退出
            str = str.substring(1, str.length() - 1);
            return str;
        }

        // 没有双引号则判断单引号
        if (str.length() >= 2 && str.startsWith("\'") && str.endsWith("\'"))
            str = str.substring(1, str.length() - 1);

        return str;
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
            return JsonUtil.NULL;
        return JsonUtil.getParser(obj.getClass()).toString(obj);
    }

    /**
     * JSON字符串转换成对象
     * 
     * @param json  JSON字符串
     * @param clazz 转换成对象的类
     * @return obj对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T toObject(String json, Class<T> clazz)
    {
        if (json == null || clazz == null)
            return null;

        if (NULL.equals(json))
            return null;

        return (T) JsonUtil.getParser(clazz).toObject(json, getTokens(json), clazz);
    }

    /**
     * 提供泛型，得到列表
     * 
     * @param json        JSON字符串
     * @param resultClass 泛型
     * @return List<T>
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> toList(String json, Class<T> resultClass)
    {

        Token token = getTokens(json);
        if (token == null || token.type() != T_BRACKET_L)
            return new ArrayList<T>(0);

        List<Token> elems = token.getElements(T_COMMA);
        List<T> list = new ArrayList<T>(elems.size());
        for (Token t : elems)
            list.add((T) getParser(resultClass).toObject(json, t, resultClass));

        return list;
    }

    /**
     * 获取字段对应的数组字符串值，得到结果为[]开头和结束
     * 
     * @param json  JSON字符串
     * @param field 字段名
     * @return 得到的字符串值，[]开头和结束
     */
    public static String getArray(String json, String field)
    {//
        String arrText = getString(json, field);
        if (arrText == null || arrText.length() <= 1 || arrText.charAt(0) != BRACKET_L || arrText.charAt(arrText.length() - 1) != BRACKET_R)
            return null;

        return arrText;
    }

    /**
     * 获取字段对应的数组字符串值，得到结果为[]开头和结束
     * 
     * @param <T>
     * @param json  JSON字符串
     * @param field 字段名
     * @return 得到的字符串值，[]开头和结束
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] getArray(String json, String field, Class<T> resultClass)
    {//
        if (resultClass == null)
            return null;

        String arrJson = getString(json, field);
        if (arrJson == null || arrJson.length() <= 1 || arrJson.charAt(0) != BRACKET_L || arrJson.charAt(arrJson.length() - 1) != BRACKET_R)
            return null;

        return (T[]) toList(arrJson, resultClass).toArray();
    }

    /**
     * 获取boolean 如果 值为1，结果也返回true
     */
    public static boolean getBoolean(String json, String field)
    {
        String value = getString(json, field);
        return "true".equalsIgnoreCase(value) ? true : "1".equals(value);
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
     * @param json  源json字符串
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
     * @param json  对象或MAP对应的JSON字符串
     * @param key   对象的字段或MAP的键
     * @param value 值
     * @return 增加或更新KEY之后的json
     */
    public static String toStringAddOrUpdate(String json, String key, Object value)
    {
        if (json == null)
            return null;
        if (isEmptyBlank(key) || json.length() < 2)
            return json;// json 字符串必须大于2个字符

        // token.getElements()[0] = field的上一个token，token.getElements()[1] = field 的value
        Token token = getTokens(json, key);
        if (token == null || token.size() < 2)
        {// 找不到字段
            int pos = 0;
            for (; pos < json.length(); pos++)
            {
                char ch = json.charAt(pos);
                if (!isWhitespace(ch) && ch != BRACE_L)
                    return json;

                if (ch == BRACE_L)
                    break;
            }
            key = addEscapeChar(key, DB_QUOTE);
            String val = toString(value);
            StringBuilder sb = new StringBuilder(json.length() + key.length() + 3 + val.length());
            sb.append(json.substring(0, pos + 1));// 添加根token
            sb.append(DB_QUOTE).append(key).append(DB_QUOTE).append(COLON).append(val).append(COMMA);// 添加键值;
            sb.append(json.substring(pos + 1));
            return sb.toString();
        }
        else
        {// 找到字段了
            StringBuilder sb = new StringBuilder(json.length());
            sb.append(json.substring(0, token.getElements()[1].begin()));
            sb.append(toString(value));//更新值
            sb.append(json.substring(token.getElements()[1].end() + 1));
            return sb.toString();
        }
    }

    /**
     * 提供两个键值生成JSON字符串，对应MAP的两次put
     * 
     * @param key   第一个键
     * @param value 第一个值
     * @return 生成后的JSON字符串
     */
    public static String toString(String key, Object value)
    {
        if (isEmptyBlank(key))
            return NULL;

        return new StringBuilder().append(BRACE_L).append(toString(trim(key))).append(COLON).append(toString(value)).append(BRACE_R).toString();
    }

    /**
     * 从json字符串中删除字段 当前只删除 一级字段<br>
     * 
     * @param json  源json字符串
     * @param field 指定删除字段
     * @retun 返回删除后的字符串
     */
    public static String remove(String json, String key)
    {

        Token token = getTokens(json, key);
        if (token == null || token.size() < 2)
            return json;
        
        // token.getElements()[0] = field的上一个token，token.getElements()[1] = field 的value
        Token[] tokens = token.getElements();
        StringBuilder sb = new StringBuilder(json.length());
        sb.append(json.substring(0, (tokens[0] != null && tokens[0].type() == T_COMMA) ? tokens[0].begin() : tokens[1].begin()));
        sb.append(json.substring(tokens[1].end()+1));
        return sb.toString();
    }

    /**
     * 指定字段查找json字符串的数据
     * 
     * @param json  JSON字符串
     * @param field 字段名
     * @return 得到的字符串值
     */
    public static String getString(String json, String field)
    {//
        if (json == null)
            return null;
        Token token = getTokens(json, field);
        if (token == null || token.size() < 2)
            return null;

        return removeStartEndQuotation(field == null ? token.toString(json) : token.getElements()[1].toString(json));
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
     * @param cls   类结构
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

        if (clazz == int.class)
            return true;
        else if (clazz == long.class)
            return true;
        else if (clazz == boolean.class)
            return true;
        else if (clazz == byte.class)
            return true;
        else if (clazz == short.class)
            return true;
        else if (clazz == char.class)
            return true;
        else if (clazz == float.class)
            return true;
        else if (clazz == double.class)
            return true;
        else
            return false;
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
        if (s == null || s.length() == 0)
            return true;

        for (char ch : s.toCharArray())
        {
            if (!isWhitespace(ch))
                return false;
        }
        return true;
    }

    public static String trim(String s)
    {
        if (s == null)
            return null;

        StringBuilder sb = new StringBuilder(s.length());
        for (char ch : s.toCharArray())
        {
            if (isWhitespace(ch))
                continue;

            sb.append(ch);
        }
        return sb.toString();
    }
}
