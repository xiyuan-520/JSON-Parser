package com.xiyuan.util.json.parser;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.xiyuan.util.json.JsonParser;
import com.xiyuan.util.json.Jsons;
import com.xiyuan.util.json.Token;

public final class MapParser extends JsonParser implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public String toString(Object obj)
    {
        if (obj == null || !(obj instanceof Map) || ((Map<?, ?>) obj).isEmpty())
            return Jsons.EMPTY_OBJ;
        Map<?, ?> map = (Map<?, ?>) obj;
        
        StringBuilder strb = new StringBuilder().append(Jsons.BRACE_L);
        for (Entry<?, ?> entry : map.entrySet())
        {
            Object o = entry.getKey();
            String key = o == null ? Jsons.NULL : Jsons.getParser(o.getClass()).toString(o);
            Object value = entry.getValue();
            strb.append(Jsons.DB_QUOTE).append(Jsons.removeStartEndQuotation(key)).append(Jsons.DB_QUOTE).append(Jsons.COLON).append(value == null ? Jsons.NULL : Jsons.getParser(o.getClass()).toString(value)).append(Jsons.COMMA);
        }
        
        strb.setLength(strb.length() - 1);
        strb.append(Jsons.BRACE_R);
        return strb.toString();
    }
    
    @Override
    public Object toObject(String json, Token token, Class<?> cls)
    {// TODO 以后 获取cls 具体类型构造map 集构造类型，目前只放入 String
    
        Map<String, String> map = null;
        if (cls == Map.class || cls == HashMap.class)
            map = new HashMap<String, String>();
        else if (cls == ConcurrentMap.class || cls == ConcurrentHashMap.class)
            map = new ConcurrentHashMap<String, String>();
        else if (cls == Hashtable.class)
            map = new Hashtable<String, String>();
        else if (cls == LinkedHashMap.class)
            map = new LinkedHashMap<String, String>();
        else if (cls == TreeMap.class)
            map = new TreeMap<String, String>();
        else if (map == null || token.type() != Jsons.T_BRACE_L)
            return map;// 不支持的类型或者不是 对象
            
        Token[] ls = token.getElements();
        boolean isValue = false;
        Token key = null;
        for (Token t : ls)
        {
            if (t.type() == Jsons.T_COMMA)
                continue;
            
            if (t.type() == Jsons.T_COLON && key != null)
            {
                isValue = true;
                continue;
            }
            
            if (isValue && key != null)
            {
                String v = Jsons.removeStartEndQuotation(t.toString(json));
                map.put(Jsons.removeStartEndQuotation(key.toString(json)), Jsons.NULL.equals(v) ? null : v);
                isValue = false;
                key = null;
            }
            else
                key = t;
        }
        
        return map;
    }
}
