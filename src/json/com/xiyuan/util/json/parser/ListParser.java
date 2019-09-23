
package com.xiyuan.util.json.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.xiyuan.util.json.JsonLexer;
import com.xiyuan.util.json.JsonParser;
import com.xiyuan.util.json.Jsons;
import com.xiyuan.util.json.Token;

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

        StringBuilder strb = new StringBuilder().append(JsonLexer.BRACKET_L);
        for (Object o : list)
            strb.append(o == null ? JsonLexer.NULL : lexer.getParser(o.getClass()).toString(o)).append(JsonLexer.COMMA);
        strb.setLength(strb.length()-1);
        strb.append(JsonLexer.BRACKET_R);
        return strb.toString();
    }

    @Override
    public Object toObject(Class<?> cls)
    {
        Collection<Object> list = null;
//        if (cls == Collection.class || cls == List.class || cls == ArrayList.class)
//            list = new ArrayList<Object>();
//        else if (cls == LinkedList.class)
//            list = new LinkedList<Object>();
//        else if (cls == Set.class || cls == HashSet.class)
//            list = new HashSet<Object>();
//        
//        if (list == null)// 其他不支持，直接返回null;
//            return list;
//        
//        if (token == null || token.type() != Token.BRACKET_L)
//            return list;
//        for (Token t : token.getElements(Token.COMMA))
//            list.add(t.toString(json));// TODO 以后 获取list 具体类型构造对象，目前只放入 String
        
        return list;
    }

}
