package com.xiyuan.util.json.parser;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
    private static final int defult_capacity = 500;// 初始化大小数组大小
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
        switch (cls.getName().hashCode())
        {
            case JsonLexer.BOOL_ARR_CLS_HASH:
                return fromArr_boolean();
            case JsonLexer.BYTE_ARR_CLS_HASH:
                return fromArr_byte();
            case JsonLexer.CHAR_ARR_CLS_HASH:
                return fromArr_char();
            case JsonLexer.SHORT_ARR_CLS_HASH:
                return fromArr_short();
            case JsonLexer.INT_ARR_CLS_HASH:
                return fromArr_int();
            case JsonLexer.LONG_ARR_CLS_HASH:
                return fromArr_long();
            case JsonLexer.FLOAT_ARR_CLS_HASH:
                return fromArr_float();
            case JsonLexer.DOUBLE_ARR_CLS_HASH:
                return fromArr_double();
            case JsonLexer.BOOL_OBJ_ARR_CLS_HASH:
                return fromArr_Boolean();
            case JsonLexer.BYTE_OBJ_ARR_CLS_HASH:
                return fromArr_Byte();
            case JsonLexer.CHAR_OBJ_ARR_CLS_HASH:
                return fromArr_Char();
            case JsonLexer.SHORT_OBJ_ARR_CLS_HASH:
                return fromArr_Short();
            case JsonLexer.INT_OBJ_ARR_CLS_HASH:
                return fromArr_Int();
            case JsonLexer.LONG_OBJ_ARR_CLS_HASH:
                return fromArr_Long();
            case JsonLexer.FLOAT_OBJ_ARR_CLS_HASH:
                return fromArr_Float();
            case JsonLexer.DOUBLE_OBJ_ARR_CLS_HASH:
                return fromArr_Double();
            case JsonLexer.STRING_ARR_CLS_HASH:
                return fromArr(lexer, String.class, lexer.BaseParser());
            default:
                return fromArr(lexer, cls.getComponentType(), lexer.getParser(cls.getComponentType()));
        }
    }
    
    private class PageArr
    {
        Class<?> cls = null;
        volatile int size = 0;
        int pageSize = 500;
        List<Object[]> ls = new ArrayList<Object[]>();
        volatile Object[] cur = null;
        
        PageArr(Class<?> cls, int pageSize)
        {
            this.cls = cls;
            if (pageSize > this.pageSize)
                this.pageSize = pageSize;
            addPage();//初始化一页
        }
        
        public void addPage()
        {
            cur = (Object[]) Array.newInstance(cls, pageSize);
            size = 0;
            ls.add(cur);// 每页500
        }
        
        public void add(Object value)
        {
            if (size == cur.length)
                addPage();
            
            cur[size++] = value;
        }
        
        public Object[] toArr()
        {
            if (ls.size() == 0)
                return (Object[]) Array.newInstance(cls, 0);
            
            Object[] arr = (Object[]) Array.newInstance(cls, (ls.size() - 1)*pageSize + size);
            for (int i = 0; i < ls.size() - 1; i++)
                System.arraycopy(ls.get(i), 0, arr, 0, pageSize);
            
            if (size > 0)
                System.arraycopy(cur, 0, arr, 0, size);
            
            return arr;
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
        PageArr page = new PageArr(cls, (int) (capacity_multiple*defult_capacity));
        int scope = lexer.scope();
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.tokenType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
//            
            page.add(parser.toObject(cls));
//            temp[length++] = value;
        }
       
        System.out.println("fromAr耗时：" + (System.currentTimeMillis() - l1));
        return page.toArr();
    }
    
    /***
     * Integer[]数组解析
     * @param lexer 分析器
     * @param cls 组件类型
     * @param parser 组件类型 对饮的解析器
     * @return
     */
    private Object fromArr_Int()
    {
        if (lexer.isEOF())
            return null;
        
        if (!lexer.isArr())// 非 数组开始符 [
            return new int[0];
        
        int length = 0;
        int scope = lexer.scope();
        Integer[] arr = null;
        Integer[] temp = new Integer[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.tokenType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
                
            if (length == temp.length)
            {
                arr = new Integer[(int) (temp.length * capacity_multiple)];
                System.arraycopy(temp, 0, arr, 0, length);
                temp = arr;
                arr = null;
            }
            temp[length++] = BaseParser.intValue(lexer);
        }
        
        arr = new Integer[length];
        if (length > 0)
        {
            System.arraycopy(temp, 0, arr, 0, length);
            temp = null;
        }
        return arr;
    }
    
    /***
     * Boolean[]数组解析
     * @param lexer 分析器
     * @param cls 组件类型
     * @param parser 组件类型 对饮的解析器
     * @return
     */
    private Object fromArr_Boolean()
    {
        if (lexer.isEOF())
            return null;
        
        if (!lexer.isArr())// 非 数组开始符 [
            return new int[0];
        
        int length = 0;
        int scope = lexer.scope();
        Boolean[] arr = null;
        Boolean[] temp = new Boolean[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.tokenType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
                
            if (length == temp.length)
            {
                arr = new Boolean[(int) (temp.length * capacity_multiple)];
                System.arraycopy(temp, 0, arr, 0, length);
                temp = arr;
                arr = null;
            }
            temp[length++] = BaseParser.booleanValue(lexer);
        }
        
        arr = new Boolean[length];
        if (length > 0)
        {
            System.arraycopy(temp, 0, arr, 0, length);
            temp = null;
        }
        return arr;
    }
    
    /***
     * byte[]数组解析
     * @param lexer 分析器
     * @param cls 组件类型
     * @param parser 组件类型 对饮的解析器
     * @return
     */
    private Object fromArr_byte()
    {
        if (lexer.isEOF())
            return null;
        
        if (!lexer.isArr())// 非 数组开始符 [
            return new int[0];
        
        int length = 0;
        int scope = lexer.scope();
        byte[] arr = null;
        byte[] temp = new byte[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.tokenType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
                
            if (length == temp.length)
            {
                arr = new byte[(int) (temp.length * capacity_multiple)];
                System.arraycopy(temp, 0, arr, 0, length);
                temp = arr;
                arr = null;
            }
            temp[length++] = BaseParser.byteValue(lexer);
        }
        
        arr = new byte[length];
        if (length > 0)
        {
            System.arraycopy(temp, 0, arr, 0, length);
            temp = null;
        }
        return arr;
    }
    
    /***
     * char[]数组解析
     * @param lexer 分析器
     * @param cls 组件类型
     * @param parser 组件类型 对饮的解析器
     * @return
     */
    private Object fromArr_char()
    {
        if (lexer.isEOF())
            return null;
        
        if (!lexer.isArr())// 非 数组开始符 [
            return new int[0];
        
        int length = 0;
        int scope = lexer.scope();
        char[] arr = null;
        char[] temp = new char[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.tokenType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
                
            if (length == temp.length)
            {
                arr = new char[(int) (temp.length * capacity_multiple)];
                System.arraycopy(temp, 0, arr, 0, length);
                temp = arr;
                arr = null;
            }
            temp[length++] = BaseParser.charValue(lexer);
        }
        
        arr = new char[length];
        if (length > 0)
        {
            System.arraycopy(temp, 0, arr, 0, length);
            temp = null;
        }
        return arr;
    }
    
    /***
     * short[]数组解析
     * @param lexer 分析器
     * @param cls 组件类型
     * @param parser 组件类型 对饮的解析器
     * @return
     */
    private Object fromArr_short()
    {
        if (lexer.isEOF())
            return null;
        
        if (!lexer.isArr())// 非 数组开始符 [
            return new int[0];
        
        int length = 0;
        int scope = lexer.scope();
        short[] arr = null;
        short[] temp = new short[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.tokenType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
                
            if (length == temp.length)
            {
                arr = new short[(int) (temp.length * capacity_multiple)];
                System.arraycopy(temp, 0, arr, 0, length);
                temp = arr;
                arr = null;
            }
            temp[length++] = BaseParser.shortValue(lexer);
        }
        
        arr = new short[length];
        if (length > 0)
        {
            System.arraycopy(temp, 0, arr, 0, length);
            temp = null;
        }
        return arr;
    }
    
    /***
     * double[]数组解析
     * @param lexer 分析器
     * @param cls 组件类型
     * @param parser 组件类型 对饮的解析器
     * @return
     */
    private Object fromArr_double()
    {
        if (lexer.isEOF())
            return null;
        
        if (!lexer.isArr())// 非 数组开始符 [
            return new int[0];
        
        int length = 0;
        int scope = lexer.scope();
        double[] arr = null;
        double[] temp = new double[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.tokenType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
                
            if (length == temp.length)
            {
                arr = new double[(int) (temp.length * capacity_multiple)];
                System.arraycopy(temp, 0, arr, 0, length);
                temp = arr;
                arr = null;
            }
            temp[length++] = BaseParser.doubleValue(lexer);
        }
        
        arr = new double[length];
        if (length > 0)
        {
            System.arraycopy(temp, 0, arr, 0, length);
            temp = null;
        }
        return arr;
    }
    
    /***
     * long[]数组解析
     * @param lexer 分析器
     * @param cls 组件类型
     * @param parser 组件类型 对饮的解析器
     * @return
     */
    private Object fromArr_long()
    {
        if (lexer.isEOF())
            return null;
        
        if (!lexer.isArr())// 非 数组开始符 [
            return new int[0];
        
        int length = 0;
        int scope = lexer.scope();
        long[] arr = null;
        long[] temp = new long[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.tokenType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
                
            if (length == temp.length)
            {
                arr = new long[(int) (temp.length * capacity_multiple)];
                System.arraycopy(temp, 0, arr, 0, length);
                temp = arr;
                arr = null;
            }
            temp[length++] = BaseParser.longValue(lexer);
        }
        
        arr = new long[length];
        if (length > 0)
        {
            System.arraycopy(temp, 0, arr, 0, length);
            temp = null;
        }
        return arr;
    }
    
    /***
     * float[]数组解析
     * @param lexer 分析器
     * @param cls 组件类型
     * @param parser 组件类型 对饮的解析器
     * @return
     */
    private Object fromArr_float()
    {
        if (lexer.isEOF())
            return null;
        
        if (!lexer.isArr())// 非 数组开始符 [
            return new int[0];
        
        int length = 0;
        int scope = lexer.scope();
        float[] arr = null;
        float[] temp = new float[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.tokenType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
                
            if (length == temp.length)
            {
                arr = new float[(int) (temp.length * capacity_multiple)];
                System.arraycopy(temp, 0, arr, 0, length);
                temp = arr;
                arr = null;
            }
            temp[length++] = BaseParser.floatValue(lexer);
        }
        
        arr = new float[length];
        if (length > 0)
        {
            System.arraycopy(temp, 0, arr, 0, length);
            temp = null;
        }
        return arr;
    }
    
    /***
     * boolean[]数组解析
     * @param lexer 分析器
     * @param cls 组件类型
     * @param parser 组件类型 对饮的解析器
     * @return
     */
    private Object fromArr_boolean()
    {
        if (lexer.isEOF())
            return null;
        
        if (!lexer.isArr())// 非 数组开始符 [
            return new int[0];
        
        int length = 0;
        int scope = lexer.scope();
        boolean[] arr = null;
        boolean[] temp = new boolean[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.tokenType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
                
            if (length == temp.length)
            {
                arr = new boolean[(int) (temp.length * capacity_multiple)];
                System.arraycopy(temp, 0, arr, 0, length);
                temp = arr;
                arr = null;
            }
            temp[length++] = BaseParser.booleanValue(lexer);
        }
        
        arr = new boolean[length];
        if (length > 0)
        {
            System.arraycopy(temp, 0, arr, 0, length);
            temp = null;
        }
        return arr;
    }
    
    /***
     * Byte[]数组解析
     * @param lexer 分析器
     * @param cls 组件类型
     * @param parser 组件类型 对饮的解析器
     * @return
     */
    private Object fromArr_Byte()
    {
        if (lexer.isEOF())
            return null;
        
        if (!lexer.isArr())// 非 数组开始符 [
            return new int[0];
        
        int length = 0;
        int scope = lexer.scope();
        Byte[] arr = null;
        Byte[] temp = new Byte[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.tokenType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
                
            if (length == temp.length)
            {
                arr = new Byte[(int) (temp.length * capacity_multiple)];
                System.arraycopy(temp, 0, arr, 0, length);
                temp = arr;
                arr = null;
            }
            temp[length++] = BaseParser.byteValue(lexer);
        }
        
        arr = new Byte[length];
        if (length > 0)
        {
            System.arraycopy(temp, 0, arr, 0, length);
            temp = null;
        }
        return arr;
    }
    
    /***
     * Character[]数组解析
     * @param lexer 分析器
     * @param cls 组件类型
     * @param parser 组件类型 对饮的解析器
     * @return
     */
    private Object fromArr_Char()
    {
        if (lexer.isEOF())
            return null;
        
        if (!lexer.isArr())// 非 数组开始符 [
            return new int[0];
        
        int length = 0;
        int scope = lexer.scope();
        Character[] arr = null;
        Character[] temp = new Character[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.tokenType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
                
            if (length == temp.length)
            {
                arr = new Character[(int) (temp.length * capacity_multiple)];
                System.arraycopy(temp, 0, arr, 0, length);
                temp = arr;
                arr = null;
            }
            temp[length++] = BaseParser.charValue(lexer);
        }
        
        arr = new Character[length];
        if (length > 0)
        {
            System.arraycopy(temp, 0, arr, 0, length);
            temp = null;
        }
        return arr;
    }
    
    /***
     * Double[]数组解析
     * @param lexer 分析器
     * @param cls 组件类型
     * @param parser 组件类型 对饮的解析器
     * @return
     */
    private Object fromArr_Double()
    {
        if (lexer.isEOF())
            return null;
        
        if (!lexer.isArr())// 非 数组开始符 [
            return new int[0];
        
        int length = 0;
        int scope = lexer.scope();
        Double[] arr = null;
        Double[] temp = new Double[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.tokenType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
                
            if (length == temp.length)
            {
                arr = new Double[(int) (temp.length * capacity_multiple)];
                System.arraycopy(temp, 0, arr, 0, length);
                temp = arr;
                arr = null;
            }
            temp[length++] = BaseParser.doubleValue(lexer);
        }
        
        arr = new Double[length];
        if (length > 0)
        {
            System.arraycopy(temp, 0, arr, 0, length);
            temp = null;
        }
        return arr;
    }
    
    /***
     * Float[]数组解析
     * @param lexer 分析器
     * @param cls 组件类型
     * @param parser 组件类型 对饮的解析器
     * @return
     */
    private Object fromArr_Float()
    {
        if (lexer.isEOF())
            return null;
        
        if (!lexer.isArr())// 非 数组开始符 [
            return new int[0];
        
        int length = 0;
        int scope = lexer.scope();
        Float[] arr = null;
        Float[] temp = new Float[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.tokenType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
                
            if (length == temp.length)
            {
                arr = new Float[(int) (temp.length * capacity_multiple)];
                System.arraycopy(temp, 0, arr, 0, length);
                temp = arr;
                arr = null;
            }
            temp[length++] = BaseParser.floatValue(lexer);
        }
        
        arr = new Float[length];
        if (length > 0)
        {
            System.arraycopy(temp, 0, arr, 0, length);
            temp = null;
        }
        return arr;
    }
    
    /***
     * int[]数组解析
     * @param lexer 分析器
     * @param cls 组件类型
     * @param parser 组件类型 对饮的解析器
     * @return
     */
    private Object fromArr_int()
    {
        if (lexer.isEOF())
            return null;
        
        if (!lexer.isArr())// 非 数组开始符 [
            return new int[0];
        
        int length = 0;
        int scope = lexer.scope();
        int[] arr = null;
        int[] temp = new int[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.tokenType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
                
            if (length == temp.length)
            {
                arr = new int[(int) (temp.length * capacity_multiple)];
                System.arraycopy(temp, 0, arr, 0, length);
                temp = arr;
                arr = null;
            }
            temp[length++] = BaseParser.intValue(lexer);
        }
        
        arr = new int[length];
        if (length > 0)
        {
            System.arraycopy(temp, 0, arr, 0, length);
            temp = null;
        }
        return arr;
    }
    
    /***
     * Long[]数组解析
     * @param lexer 分析器
     * @param cls 组件类型
     * @param parser 组件类型 对饮的解析器
     * @return
     */
    private Object fromArr_Long()
    {
        if (lexer.isEOF())
            return null;
        
        if (!lexer.isArr())// 非 数组开始符 [
            return new int[0];
        
        int length = 0;
        int scope = lexer.scope();
        Long[] arr = null;
        Long[] temp = new Long[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.tokenType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
                
            if (length == temp.length)
            {
                arr = new Long[(int) (temp.length * capacity_multiple)];
                System.arraycopy(temp, 0, arr, 0, length);
                temp = arr;
                arr = null;
            }
            temp[length++] = BaseParser.longValue(lexer);
        }
        
        arr = new Long[length];
        if (length > 0)
        {
            System.arraycopy(temp, 0, arr, 0, length);
            temp = null;
        }
        return arr;
    }
    
    /***
     * Short[]数组解析
     * @param lexer 分析器
     * @param cls 组件类型
     * @param parser 组件类型 对饮的解析器
     * @return
     */
    private Object fromArr_Short()
    {
        if (lexer.isEOF())
            return null;
        
        if (!lexer.isArr())// 非 数组开始符 [
            return new int[0];
        
        int length = 0;
        int scope = lexer.scope();
        Short[] arr = null;
        Short[] temp = new Short[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.tokenType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
                
            if (length == temp.length)
            {
                arr = new Short[(int) (temp.length * capacity_multiple)];
                System.arraycopy(temp, 0, arr, 0, length);
                temp = arr;
                arr = null;
            }
            temp[length++] = BaseParser.shortValue(lexer);
        }
        
        arr = new Short[length];
        if (length > 0)
        {
            System.arraycopy(temp, 0, arr, 0, length);
            temp = null;
        }
        return arr;
    }
    
}
