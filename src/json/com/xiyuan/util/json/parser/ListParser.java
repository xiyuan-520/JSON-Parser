package com.xiyuan.util.json.parser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.xiyuan.util.json.JsonLexer;
import com.xiyuan.util.json.JsonParser;

public final class ListParser extends JsonParser
{
    private static final long serialVersionUID = 1L;
    
    public ListParser(JsonLexer lexer)
    {
        super(lexer);
    }
    
    @Override
    public String toString(Object obj)
    {
        Collection<?> list = null;
        if (obj == null || !(obj instanceof Collection) || (list = (Collection<?>) obj).isEmpty())
            return JsonLexer.EMPTY_ARR;
        
        StringBuilder strb = new StringBuilder(list.size() * 500).append(JsonLexer.BRACKET_L);
        for (Object o : list)
            strb.append(o == null ? JsonLexer.NULL : lexer.getParser(o.getClass()).toString(o)).append(JsonLexer.COMMA);
        strb.setLength(strb.length() - 1);
        strb.append(JsonLexer.BRACKET_R);
        return strb.toString();
    }
    
    @Override
    public Object toObject(Class<?> cls)
    {// TODO 以后 获取cls 具体类型构造list 集构造类型，目前只放入 String
        return toObject(cls, String.class);
    }
    
    public Object toObject(Class<?> genericClass, Class<?> resultClazz)
    {
        if (!isSupportClass(genericClass))
            return null;// 不支持的类型
            
        if (!lexer.isArr())
            return null;
        
        if (resultClazz.isPrimitive())// 如果是基本类型 则使用基本类型的包装类
            resultClazz = JsonLexer.getPrimitiveBase(resultClazz);
        
        Object[] arr = (Object[]) lexer.ArrayParser().toObject(Array.newInstance(resultClazz, 0).getClass());
        Collection<Object> list = newList(genericClass, arr == null ? 0 : arr.length);
        for (int i = 0; i < arr.length; i++)
            list.add(arr[i]);
        
        return list;
    }
    
    /**
     * 创建集合对象
     * @param collection
     * @param capcity
     * @return
     */
    private Collection<Object> newList(Class<?> collection, int capcity)
    {
        Collection<Object> list = null;
        if (collection == Collection.class || collection == List.class || collection == ArrayList.class)
            list = new ArrayList<Object>(capcity);
        else if (collection == LinkedList.class)
            list = new LinkedList<Object>();
        else if (collection == TreeSet.class)
            list = new TreeSet<Object>();
        else if (collection == Set.class || collection == HashSet.class)
            list = new HashSet<Object>(capcity);
        
        return list;
    }
    
    /**
     *  判断支持的集合类型
     */
    private boolean isSupportClass(Class<?> cls)
    {
        if (cls == Collection.class || cls == List.class || cls == ArrayList.class || cls == LinkedList.class || cls == TreeSet.class || cls == Set.class || cls == HashSet.class)
            return true;
        
        return false;
    }
}
