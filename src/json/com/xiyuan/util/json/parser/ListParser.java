
package com.xiyuan.util.json.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.xiyuan.util.json.JsonParser;
import com.xiyuan.util.json.JsonUtil;
import com.xiyuan.util.json.Token;

public final class ListParser extends JsonParser
{
    private static final long serialVersionUID = 1L;

    @Override
    public String toString(Object obj)
    {
        Collection<?> list = null;
        if (obj == null || !(obj instanceof Collection) || (list = (Collection<?>) obj).isEmpty())
            return JsonUtil.EMPTY_ARR;

        StringBuilder strb = new StringBuilder().append(JsonUtil.BRACKET_L);
        for (Object o : list)
            strb.append(o == null ? JsonUtil.NULL : JsonUtil.getParser(o.getClass()).toString(o)).append(JsonUtil.COMMA);
        strb.setLength(strb.length()-1);
        strb.append(JsonUtil.BRACKET_R);
        return strb.toString();
    }

    @Override
    public Object toObject(String json, Token token, Class<?> cls)
    {
        Collection<Object> list = null;
        if (cls == Collection.class || cls == List.class || cls == ArrayList.class)
            list = new ArrayList<Object>();
        else if (cls == LinkedList.class)
            list = new LinkedList<Object>();
        else if (cls == Set.class || cls == HashSet.class)
            list = new HashSet<Object>();
        
        if (list == null)// 其他不支持，直接返回null;
            return list;
        
        if (token == null || token.type() != JsonUtil.T_BRACKET_L)
            return list;
        for (Token t : token.getElements(JsonUtil.T_COMMA))
            list.add(t.toString(json));// TODO 以后 获取list 具体类型构造对象，目前只放入 String
        
        return list;
    }

}
