package com.xiyuan.util.json.parser;

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
        
        StringBuilder strb = new StringBuilder(list.size()*500).append(JsonLexer.BRACKET_L);
        for (Object o : list)
            strb.append(o == null ? JsonLexer.NULL : lexer.getParser(o.getClass()).toString(o)).append(JsonLexer.COMMA);
        strb.setLength(strb.length() - 1);
        strb.append(JsonLexer.BRACKET_R);
        return strb.toString();
    }
    
    @Override
    public Object toObject(Class<?> cls)
    {
        if (!isSupportClass(cls))
            return null;// 不支持的类型
            
        String[] arr = null;
        if (lexer.isArr())
            arr = (String[]) lexer.ArrayParser().toObject(String[].class);// TODO 以后根据具体类型判断
            
        Collection<Object> list = null;
        if (cls == Collection.class || cls == List.class || cls == ArrayList.class)
            list = new ArrayList<Object>(arr == null ? 0 : arr.length);
        else if (cls == LinkedList.class)
            list = new LinkedList<Object>();
        else if (cls == TreeSet.class)
            list = new TreeSet<Object>();
        else if (cls == Set.class || cls == HashSet.class)
            list = new HashSet<Object>(arr == null ? 0 : arr.length);
        
        if (arr == null)
            return list;
        for (int i = 0; i < arr.length; i++)
            list.add(arr[i]);
        
        return list;
    }
    
    private boolean isSupportClass(Class<?> cls)
    {
        if (cls == Collection.class || cls == List.class || cls == ArrayList.class || cls == LinkedList.class || cls == TreeSet.class || cls == Set.class || cls == HashSet.class)
            return true;
        
        return false;
    }
}
