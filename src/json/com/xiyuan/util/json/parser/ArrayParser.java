package com.xiyuan.util.json.parser;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLongArray;

import com.xiyuan.util.json.JsonLexer;
import com.xiyuan.util.json.JsonParser;

/***
 * 数组解析器,8种基本类型数组、字符串数组和对象数组
 * 
 * @DOTO 为了避免频繁调用lexer.getParser(clz)损耗性能,此类有重复代码
 * @version v1.0.0 @author lgz 2019-8-28 新建与整理
 */
public final class ArrayParser extends JsonParser implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final int defult_capacity = 500;// 初始化大小数组大小
    private static final double capacity_multiple = 2.5;// 1.5增长倍数
    
    /***
     * 数据分页对象 避免 内存频繁拷贝
     * @version v1.0.0 @author lgz 2019-9-25 新建与整理
     */
    public static final class ArrPage
    {
        private Class<?> cls = null;
        private List<Object[]> ls = new ArrayList<Object[]>();
        private int pageSize = 500;
        private int total = 0;
        private volatile int curSize = 0;// 当前页大小
        private volatile Object[] cur = null;// 当前对象数组
        
        public ArrPage(Class<?> cls, int pageSize)
        {
            this.cls = cls;
            if (pageSize > this.pageSize)
                this.pageSize = pageSize;
            addPage();// 初始化一页
        }
        
        private void addPage()
        {// 添加一页
            if (cur != null)
                this.ls.add(cur);// 每页500
                
            this.cur = (Object[]) Array.newInstance(cls, pageSize);
            this.curSize = 0;
        }
        
        public void add(Object value)
        {
            if (this.curSize == this.cur.length)
            {
                addPage();
            }
            
            this.cur[this.curSize++] = value;
            this.total++;
            
            if (value == null)
                System.out.println(this.total);
        }
        
        public Object[] toArr()
        {
            if (total == 0)
                return (Object[]) Array.newInstance(cls, 0);
            
            Object[] target = (Object[]) Array.newInstance(cls, total);
            for (int i = 0; i < ls.size(); i++)
            {
                Object[] src = ls.get(i);
                System.arraycopy(src, 0, target, src.length * i, src.length);
            }
            
            if (this.curSize > 0)
                System.arraycopy(cur, 0, target, ls.size() * pageSize, this.curSize);
            
            return target;
        }
    }
    
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
            case JsonLexer.CONCURRENT_ATOMIC_ATOMIC_INTEGER_ARRAY:
                return new AtomicIntegerArray((int[]) fromArr_int());
            case JsonLexer.CONCURRENT_ATOMIC_ATOMIC_LONG_ARRAY:
                return new AtomicLongArray((long[]) fromArr_long());
            // 基本类型
            case JsonLexer.ARR_BOOLEAN:
                return fromArr_boolean();
            case JsonLexer.ARR_BYTE:
                return fromArr_byte();
            case JsonLexer.ARR_CHAR:
                return fromArr_char();
            case JsonLexer.ARR_SHORT:
                return fromArr_short();
            case JsonLexer.ARR_INT:
                return fromArr_int();
            case JsonLexer.ARR_LONG:
                return fromArr_long();
            case JsonLexer.ARR_FLOAT:
                return fromArr_float();
            case JsonLexer.ARR_DOUBLE:
                return fromArr_double();
                // 基本封装类型
            case JsonLexer.ARR_BOOLEAN_O:
                return fromArr_Boolean();
            case JsonLexer.ARR_BYTE_O:
                return fromArr_Byte();
            case JsonLexer.ARR_CHAR_O:
                return fromArr_Char();
            case JsonLexer.ARR_SHORT_O:
                return fromArr_Short();
            case JsonLexer.ARR_INT_O:
                return fromArr_Int();
            case JsonLexer.ARR_LONG_O:
                return fromArr_Long();
            case JsonLexer.ARR_FLOAT_O:
                return fromArr_Float();
            case JsonLexer.ARR_DOUBLE_O:
                return fromArr_Double();
                // String 型
            case JsonLexer.ARR_STRING:
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
        if (lexer.isEOF() && !lexer.isArr())// 非 数组开始符 [或者文档结束
            return Array.newInstance(cls, 0);
        
        ArrPage page = new ArrPage(cls, (int) (capacity_multiple * defult_capacity));
        int scope = lexer.scope();
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
            if (lexer.curType() == JsonLexer.T_COMMA)
                continue;// 逗号跳过
                
            page.add(parser.toObject(cls));
        }
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
        if (lexer.isEOF() && !lexer.isArr())// 非 数组开始符 [或者文档结束
            return new Integer[0];
        
        int length = 0;
        int scope = lexer.scope();
        Integer[] arr = null;
        Integer[] temp = new Integer[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.curType() == JsonLexer.T_COMMA)
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
        if (lexer.isEOF() && !lexer.isArr())// 非 数组开始符 [或者文档结束
            return new Boolean[0];
        
        int length = 0;
        int scope = lexer.scope();
        Boolean[] arr = null;
        Boolean[] temp = new Boolean[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.curType() == JsonLexer.T_COMMA)
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
        if (lexer.isEOF() && !lexer.isArr())// 非 数组开始符 [或者文档结束
            return new byte[0];
        
        int length = 0;
        int scope = lexer.scope();
        byte[] arr = null;
        byte[] temp = new byte[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.curType() == JsonLexer.T_COMMA)
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
        if (lexer.isEOF() && !lexer.isArr())// 非 数组开始符 [或者文档结束
            return new char[0];
        
        int length = 0;
        int scope = lexer.scope();
        char[] arr = null;
        char[] temp = new char[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.curType() == JsonLexer.T_COMMA)
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
        if (lexer.isEOF() && !lexer.isArr())// 非 数组开始符 [或者文档结束
            return new short[0];
        
        int length = 0;
        int scope = lexer.scope();
        short[] arr = null;
        short[] temp = new short[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.curType() == JsonLexer.T_COMMA)
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
        if (lexer.isEOF() && !lexer.isArr())// 非 数组开始符 [或者文档结束
            return new double[0];
        
        int length = 0;
        int scope = lexer.scope();
        double[] arr = null;
        double[] temp = new double[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.curType() == JsonLexer.T_COMMA)
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
        if (lexer.isEOF() && !lexer.isArr())// 非 数组开始符 [或者文档结束
            return new long[0];
        
        int length = 0;
        int scope = lexer.scope();
        long[] arr = null;
        long[] temp = new long[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.curType() == JsonLexer.T_COMMA)
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
        if (lexer.isEOF() && !lexer.isArr())// 非 数组开始符 [或者文档结束
            return new float[0];
        
        int length = 0;
        int scope = lexer.scope();
        float[] arr = null;
        float[] temp = new float[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.curType() == JsonLexer.T_COMMA)
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
        if (lexer.isEOF() && !lexer.isArr())// 非 数组开始符 [或者文档结束
            return new boolean[0];
        
        int length = 0;
        int scope = lexer.scope();
        boolean[] arr = null;
        boolean[] temp = new boolean[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.curType() == JsonLexer.T_COMMA)
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
        if (lexer.isEOF() && !lexer.isArr())// 非 数组开始符 [或者文档结束
            return new Byte[0];
        
        int length = 0;
        int scope = lexer.scope();
        Byte[] arr = null;
        Byte[] temp = new Byte[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.curType() == JsonLexer.T_COMMA)
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
        if (lexer.isEOF() && !lexer.isArr())// 非 数组开始符 [或者文档结束
            return new Character[0];
        
        int length = 0;
        int scope = lexer.scope();
        Character[] arr = null;
        Character[] temp = new Character[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.curType() == JsonLexer.T_COMMA)
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
        if (lexer.isEOF() && !lexer.isArr())// 非 数组开始符 [或者文档结束
            return new Double[0];
        
        int length = 0;
        int scope = lexer.scope();
        Double[] arr = null;
        Double[] temp = new Double[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.curType() == JsonLexer.T_COMMA)
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
        if (lexer.isEOF() && !lexer.isArr())// 非 数组开始符 [或者文档结束
            return new Float[0];
        
        int length = 0;
        int scope = lexer.scope();
        Float[] arr = null;
        Float[] temp = new Float[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.curType() == JsonLexer.T_COMMA)
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
        if (lexer.isEOF() && !lexer.isArr())// 非 数组开始符 [或者文档结束
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
                
            if (lexer.curType() == JsonLexer.T_COMMA)
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
        if (lexer.isEOF() && !lexer.isArr())// 非 数组开始符 [或者文档结束
            return new Long[0];
        
        int length = 0;
        int scope = lexer.scope();
        Long[] arr = null;
        Long[] temp = new Long[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.curType() == JsonLexer.T_COMMA)
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
        if (lexer.isEOF() && !lexer.isArr())// 非 数组开始符 [或者文档结束
            return new Short[0];
        
        int length = 0;
        int scope = lexer.scope();
        Short[] arr = null;
        Short[] temp = new Short[defult_capacity];
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.curType() == JsonLexer.T_COMMA)
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
