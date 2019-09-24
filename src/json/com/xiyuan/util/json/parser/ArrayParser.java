package com.xiyuan.util.json.parser;

import java.io.Serializable;
import java.lang.reflect.Array;

import com.xiyuan.util.json.JsonLexer;
import com.xiyuan.util.json.JsonParser;

/***
 * 数组解析器,8种基本类型数组、字符串数组和对象数组
 * 
 * @DOTO 此类有重复代码 考虑到性能问题
 * @DOTO 子类后续添加 java.util.concurrent.atomic 包的类型
 * @version v1.0.0 @author lgz 2019-8-28 新建与整理
 */
public final class ArrayParser extends JsonParser implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final int defult_capacity = 10;// 初始化大小
    private static final double capacity_multiple = 2.5;// 1.5增长倍数
    
    public ArrayParser(JsonLexer lexer)
    {
        super(lexer);
    }
    
    public String toString(Object obj)
    {
        if (obj == null)
            return null;
        String first = null;
        Object[] arr = toArray(obj);
        if (arr.length > 0)
        {
            Object o = arr[0];
            first = lexer.getParser(o.getClass()).toString(o);
        }
        
        if (first == null)
            return JsonLexer.EMPTY_ARR;
        
        StringBuilder sb = new StringBuilder(first.length() * arr.length).append(JsonLexer.BRACKET_L);
        sb.append(first);
        for (int i = 1; i < arr.length; i++)
        {
            sb.append(JsonLexer.COMMA);
            Object o = arr[i];
            sb.append(o == null ? JsonLexer.NULL : lexer.getParser(o.getClass()).toString(o));
        }
        sb.append(JsonLexer.BRACKET_R);
        return sb.toString();
    }
    
    @Override
    public Object toObject(Class<?> cls)
    {
        // if (!cls.isArray())
        // return null;
        switch (cls.getName().hashCode())
        {
            case JsonLexer.BOOL_ARR_CLS_HASH:
                return fromArr(lexer, boolean.class, lexer.BaseParser());
            case JsonLexer.BYTE_ARR_CLS_HASH:
                return fromArr(lexer, byte.class, lexer.BaseParser());
            case JsonLexer.CHAR_ARR_CLS_HASH:
                return fromArr(lexer, char.class, lexer.BaseParser());
            case JsonLexer.SHORT_ARR_CLS_HASH:
                return fromArr(lexer, short.class, lexer.BaseParser());
            case JsonLexer.INT_ARR_CLS_HASH:
                return fromArr(lexer, int.class, lexer.BaseParser());
            case JsonLexer.LONG_ARR_CLS_HASH:
                return fromArr(lexer, long.class, lexer.BaseParser());
            case JsonLexer.FLOAT_ARR_CLS_HASH:
                return fromArr(lexer, float.class, lexer.BaseParser());
            case JsonLexer.DOUBLE_ARR_CLS_HASH:
                return fromArr(lexer, double.class, lexer.BaseParser());
            case JsonLexer.BOOL_OBJ_ARR_CLS_HASH:
                return fromArr(lexer, Boolean.class, lexer.BaseParser());
            case JsonLexer.BYTE_OBJ_ARR_CLS_HASH:
                return fromArr(lexer, Byte.class, lexer.BaseParser());
            case JsonLexer.CHAR_OBJ_ARR_CLS_HASH:
                return fromArr(lexer, Character.class, lexer.BaseParser());
            case JsonLexer.SHORT_OBJ_ARR_CLS_HASH:
                return fromArr(lexer, Short.class, lexer.BaseParser());
            case JsonLexer.INT_OBJ_ARR_CLS_HASH:
                return fromArr(lexer, Integer.class, lexer.BaseParser());
            case JsonLexer.LONG_OBJ_ARR_CLS_HASH:
                return fromArr(lexer, Long.class, lexer.BaseParser());
            case JsonLexer.FLOAT_OBJ_ARR_CLS_HASH:
                return fromArr(lexer, Float.class, lexer.BaseParser());
            case JsonLexer.DOUBLE_OBJ_ARR_CLS_HASH:
                return fromArr(lexer, Double.class, lexer.BaseParser());
            case JsonLexer.STRING_ARR_CLS_HASH:
                return fromArr(lexer, String.class, lexer.BaseParser());
            default:
                return fromArr(lexer, cls.getComponentType(), lexer.getParser(cls.getComponentType()));
        }
    }
    
    /***
     * 
     * @param lexer 分析器
     * @param cls 组件类型
     * @param parser 组件类型 对饮的解析器
     * @return
     */
    private Object fromArr(JsonLexer lexer, Class<?> cls, JsonParser parser)
    {
        if (lexer.isEOF())
            return null;
        
        if (!lexer.isArr())// 非 数组开始符 [
            return Array.newInstance(cls, 0);
        
        long l1 = System.currentTimeMillis();
        int poolSize = defult_capacity;
        int length = 0;
        int scope = lexer.scope();
        Object obj = null;
        Object temp = Array.newInstance(cls, poolSize);
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.tokenType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
                
            if (length == poolSize)
            {
                poolSize = (int) (poolSize * capacity_multiple);
                obj = Array.newInstance(cls, poolSize);
                System.arraycopy(temp, 0, obj, 0, length);
                temp = obj;
                obj = null;
            }
            
            Object value = parser.toObject(cls);
            Array.set(temp, length++, value);
        }
        
        obj = Array.newInstance(cls, length);
        if (length > 0)
        {
            System.arraycopy(temp, 0, obj, 0, length);
            temp = null;
        }
        System.out.println("fromAr耗时："+(System.currentTimeMillis()-l1));
        return obj;
    }
    
}
