package com.xiyuan.util.json;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * JSON解析器接口
 * 
 * @version v1.0.0 @author lgz 2016-3-21 新建与整理
 */
public abstract class JsonParser implements Serializable
{
    private static final long serialVersionUID = 1L;
    public static final long JS_MAX_LONG_VALUE = (long) Math.pow(2, 53);// 2^53//9007199254740992
    
    
    protected JsonLexer lexer;
    public JsonParser(JsonLexer lexer)
    {
        this.lexer = lexer;
    }
    
    /**
     * 解析对象成JSON字符串
     * 
     * @param obj 对象
     * @return JSON字符串
     */
    public abstract String toString(Object obj);

    /**
     * 解析JSON字符串成对象
     * @param <T>
     * 
     * @param json json字符串 // * @param token Json token流
     * @param cls 对象类结构
     * @return 返回对象
     */
    public abstract Object toObject(Class<?> cls);

    /***********************************************/
    // 以下提供子类调用方法
    /***********************************************/
    /**
     * 对一个未知的数组对象，转化为一个已知的数组对象，其中对基本类型进行转化
     * 
     * @param obj 未知的数组对象
     * @return 已知的数组对象
     */
    protected Object[] toArray(Object obj)
    {
        if (obj == null || !obj.getClass().isArray())
            throw new IllegalArgumentException("传入的参数不是数组类型");

        if (obj.getClass() == int[].class)
        {
            int[] os = (int[]) obj;
            Integer[] arr = new Integer[os.length];
            for (int i = 0; i < os.length; i++)
                arr[i] = os[i];
            return arr;
        }
        else if (obj.getClass() == long[].class)
        {
            long[] os = (long[]) obj;
            Long[] arr = new Long[os.length];
            for (int i = 0; i < os.length; i++)
                arr[i] = os[i];
            return arr;
        }
        else if (obj.getClass() == boolean[].class)
        {
            boolean[] os = (boolean[]) obj;
            Boolean[] arr = new Boolean[os.length];
            for (int i = 0; i < os.length; i++)
                arr[i] = os[i];
            return arr;
        }
        else if (obj.getClass() == byte[].class)
        {
            byte[] os = (byte[]) obj;
            Byte[] arr = new Byte[os.length];
            for (int i = 0; i < os.length; i++)
                arr[i] = os[i];
            return arr;
        }
        else if (obj.getClass() == char[].class)
        {
            char[] os = (char[]) obj;
            Character[] arr = new Character[os.length];
            for (int i = 0; i < os.length; i++)
                arr[i] = os[i];
            return arr;
        }
        else if (obj.getClass() == short[].class)
        {
            short[] os = (short[]) obj;
            Short[] arr = new Short[os.length];
            for (int i = 0; i < os.length; i++)
                arr[i] = os[i];
            return arr;
        }
        else if (obj.getClass() == float[].class)
        {
            float[] os = (float[]) obj;
            Float[] arr = new Float[os.length];
            for (int i = 0; i < os.length; i++)
                arr[i] = os[i];
            return arr;
        }
        else if (obj.getClass() == double[].class)
        {
            double[] os = (double[]) obj;
            Double[] arr = new Double[os.length];
            for (int i = 0; i < os.length; i++)
                arr[i] = os[i];
            return arr;
        }
        else
        {
            return (Object[]) obj;
        }
    }

    /**
     * 是否8种基本类型对象数组，支持byte/short/int/long/float/double/boolean/char
     * 
     * 这里只判断类int[],不去判断Integer[]，因为Integer[]可转化为Object[]，而int[]不行
     * 
     * @param clazz 类结构
     * @return =true/=false
     */
    protected boolean isPrimitiveArray(Class<?> clazz)
    {
        if (clazz == null)
            return false;

        if (clazz == int[].class)
            return true;
        else if (clazz == long[].class)
            return true;
        else if (clazz == boolean[].class)
            return true;
        else if (clazz == byte[].class)
            return true;
        else if (clazz == char[].class)
            return true;
        else if (clazz == short[].class)
            return true;
        else if (clazz == float[].class)
            return true;
        else if (clazz == double[].class)
            return true;
        else
            return false;
    }

    /**
     * 是否8种基本类型对象封装类数组，支持byte/short/int/long/float/double/boolean/char
     * 
     * 这里只判断类int[],不去判断Integer[]，因为Integer[]可转化为Object[]，而int[]不行
     * 
     * @param clazz 类结构
     * @return =true/=false
     */
    protected boolean isPrimitiveObjArray(Class<?> clazz)
    {
        if (clazz == null)
            return false;

        if (clazz == Integer[].class)
            return true;
        else if (clazz == Long[].class)
            return true;
        else if (clazz == Boolean[].class)
            return true;
        else if (clazz == Byte[].class)
            return true;
        else if (clazz == Character[].class)
            return true;
        else if (clazz == Short[].class)
            return true;
        else if (clazz == Float[].class)
            return true;
        else if (clazz == Double[].class)
            return true;
        else
            return false;
    }

    /**
     * String到java.sql.Date的转换 标准格式:yyyy-MM-dd
     * 
     * @param date 日期字符串
     * @return java.sql.Date
     */
    protected java.sql.Date toSqlDate(String date)
    {
        return java.sql.Date.valueOf(date);
    }

    /**
     * 转换String 到 Time,格式:"HH:mm:ss"
     * 
     * @param time 时间字符串
     * @return Time
     */
    protected Time toTime(String time)
    {
        return Time.valueOf(time);
    }

    /**
     * 转换String 到 Timestamp,格式:"yyyy-MM-dd HH:mm:ss"
     * 
     * @param datetime 格式:"yyyy-MM-dd HH:mm:ss"
     * @return Timestamp
     */
    protected Timestamp toTimestamp(String datetime)
    {
        return Timestamp.valueOf(datetime);
    }

    /**
     * 字符串转为日历,字符串符合标准格式:yyyy-MM-dd HH:mm:ss
     * 
     * @param datetime 标准时间格式 "yyyy-MM-dd HH:mm:ss"
     * @return Calendar
     */
    protected Calendar toCalendar(String datetime)
    {
        if (datetime == null)
            return null;

        String reg = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s(([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9]))$";
        if (!Pattern.matches(reg, datetime))
            throw new IllegalArgumentException("传入参数格式不正确");

        int year = Integer.parseInt(datetime.substring(0, 4));
        int month = Integer.parseInt(datetime.substring(5, 7));
        int day = Integer.parseInt(datetime.substring(8, 10));
        int hour = Integer.parseInt(datetime.substring(11, 13));
        int minute = Integer.parseInt(datetime.substring(14, 16));
        int second = Integer.parseInt(datetime.substring(17));

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);// 月份的起始值为0而不是1
        c.set(Calendar.DATE, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND, 0);

        return c;
    }

    /**
     * 字符串转为时间,字符串符合标准格式yyyy-MM-dd HH:mm:ss
     * 
     * @param datetime 标准时间格式yyyy-MM-dd HH:mm:ss
     * @return 日期对象
     */
    protected java.util.Date toDate(String datetime)
    {
        return toCalendar(datetime).getTime();
    }

    /**
     * 生成标准日期，格式为yyyy-MM-dd
     * 
     * @param date 日期对象
     * @return 日期字符串，格式为yyyy-MM-dd
     */
    protected String toDateString(Date date)
    {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    /**
     * 根据输入的时间,生成时间格式 HH:mm:ss
     * 
     * @param date Date对象
     * @return 生成时间格式为HH:mm:ss
     */
    protected String toTimeString(Date date)
    {
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }

    /**
     * 生成标准格式的字符串 格式为yyyy-MM-dd HH:mm:ss
     * 
     * @param date 日期对象
     * @return 生成默认格式的字符串 格式为yyyy-MM-dd HH:mm:ss
     */
    protected String toDateTimeString(Date date)
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /**
     * 日历转标准时间字符串
     * 
     * @param calendar 日历,给定一个日历
     * @return 取得默认的日期时间字符串yyyy-MM-dd HH:mm:ss
     */
    protected String toDateTimeString(Calendar calendar)
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    /*****************************************************************/
    // 以下是类相关操作
    /*****************************************************************/

    /**
     * 获取类中指定的属性列表，支持深度查找父类的字段列表(父类的循递归查找),静态和临时两种属性不拷贝
     * 
     * @param clazz 类
     * @param fieldList 用于存储的字段列表
     */
    protected void getFieldListDeep(Class<?> clazz, List<Field> fieldList)
    {
        Field[] fieldArr = clazz.getDeclaredFields();
        for (Field field : fieldArr)
        {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isTransient(mod) || "this$0".equals(field.getName()))
                continue;// 静态和临时两种属性不拷贝，内部类指向外部类的引用不拷贝

            fieldList.add(field);
        }

        Class<?> superSrcClass = clazz.getSuperclass();
        if (superSrcClass != null && superSrcClass != Object.class)
        {
            getFieldListDeep(superSrcClass, fieldList);
        }
    }

    /**
     * 初始化实例，忽略异常，异常时返回null
     * 
     * @param cls 类结构
     * @return 实例
     */
    protected <T> T newInstance(Class<T> cls)
    {
        try
        {
            return cls.newInstance();
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
