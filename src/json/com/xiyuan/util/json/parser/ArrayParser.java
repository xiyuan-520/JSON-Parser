package com.xiyuan.util.json.parser;

import java.io.Serializable;
import java.lang.reflect.Array;

import com.xiyuan.util.json.JsonLexer;
import com.xiyuan.util.json.JsonParser;
import com.xiyuan.util.json.Jsons;

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
    private static final double capacity_multiple = 1.5;// 1.5增长倍数

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

    /***
     * 
     * @param lexer 分析器
     * @param cls 组件类型
     * @param parser 组件类型 对饮的解析器
     * @return
     */
    private static <T> Object fromArr(JsonLexer lexer, Class<T> cls, JsonParser parser)
    {
        Class<?> type = cls.getComponentType();
        if (lexer.tokenType() != JsonLexer.T_BRACKET_L)
            return Array.newInstance(type, 0);

        int length = 0;
        int scope = lexer.scope();
        Object[] val = null;
        Object[] temp = (Object[]) Array.newInstance(type, defult_capacity);
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope)
                break;// 碰到结束符

            if (lexer.tokenType() != JsonLexer.T_COMMA)
                continue;// 逗号跳过

            if (length == temp.length)
            {
                val = (Object[]) Array.newInstance(type, (int) (temp.length * capacity_multiple));
                System.arraycopy(val, 0, val, 0, length);
                temp = val;
                val = null;
            }

            temp[length++] = parser.toObject(lexer, cls);
        }

        val = (Object[]) Array.newInstance(type, length);
        if (length > 0)
        {
            System.arraycopy(temp, 0, val, 0, length);
            temp = null;
        }

        return val;
    }

    @Override
    public Object toObject(JsonLexer lexer, Class<?> cls)
    {

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
        return fromArr(lexer, cls, lexer.getParser(cls));
    }
}
