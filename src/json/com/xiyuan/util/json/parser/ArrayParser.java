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

        Object[] arr = toArray(obj);
        StringBuilder sb = new StringBuilder().append(JsonLexer.BRACKET_L);
        for (int i = 0; i < arr.length; i++)
        {
            if (i > 0)
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
        if (!cls.isArray())
            return null;

        if (cls == boolean[].class)
            return fromArr(lexer, boolean.class, lexer.BaseParser());
        else if (cls == Boolean[].class)
            return fromArr(lexer, Boolean.class, lexer.BaseParser());
        else if (cls == byte[].class)
            return fromArr(lexer, byte.class, lexer.BaseParser());
        else if (cls == Byte[].class)
            return fromArr(lexer, Byte.class, lexer.BaseParser());
        else if (cls == char[].class)
            return fromArr(lexer, char.class, lexer.BaseParser());
        else if (cls == Character[].class)
            return fromArr(lexer, Character.class, lexer.BaseParser());
        else if (cls == int[].class)
            return fromArr(lexer, int.class, lexer.BaseParser());
        else if (cls == Integer[].class)
            return fromArr(lexer, Integer.class, lexer.BaseParser());
        else if (cls == long[].class)
            return fromArr(lexer, long.class, lexer.BaseParser());
        else if (cls == Long[].class)
            return fromArr(lexer, Long.class, lexer.BaseParser());
        else if (cls == short[].class)
            return fromArr(lexer, short.class, lexer.BaseParser());
        else if (cls == Short[].class)
            return fromArr(lexer, Short.class, lexer.BaseParser());
        else if (cls == float[].class)
            return fromArr(lexer, float.class, lexer.BaseParser());
        else if (cls == Float[].class)
            return fromArr(lexer, Float.class, lexer.BaseParser());
        else if (cls == double[].class)
            return fromArr(lexer, double.class, lexer.BaseParser());
        else if (cls == Double[].class)
            return fromArr(lexer, Double.class, lexer.BaseParser());
        else if (cls == String[].class)
            return fromArr(lexer, String.class, lexer.BaseParser());
        else
            return fromArr(lexer, cls.getComponentType(), lexer.getParser(cls.getComponentType()));
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

        return obj;
    }

}
