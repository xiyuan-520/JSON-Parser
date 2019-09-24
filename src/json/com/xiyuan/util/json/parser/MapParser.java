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

import com.xiyuan.util.json.JsonLexer;
import com.xiyuan.util.json.JsonParser;

public final class MapParser extends JsonParser implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public MapParser(JsonLexer lexer)
    {
        super(lexer);
        // TODO Auto-generated constructor stub
    }
    
    public String toString(Object obj)
    {
        if (obj == null || !(obj instanceof Map) || ((Map<?, ?>) obj).isEmpty())
            return JsonLexer.EMPTY_OBJ;
        Map<?, ?> map = (Map<?, ?>) obj;
        
        StringBuilder strb = new StringBuilder().append(JsonLexer.BRACE_L);
        for (Entry<?, ?> entry : map.entrySet())
        {
            Object o = entry.getKey();
            String key = o == null ? JsonLexer.NULL : lexer.getParser(o.getClass()).toString(o);
            Object value = entry.getValue();
            strb.append(JsonLexer.DB_QUOTE).append(JsonLexer.removeStartEndQuotation(key)).append(JsonLexer.DB_QUOTE);
            strb.append(JsonLexer.COLON);
            strb.append(value == null ? JsonLexer.NULL : lexer.getParser(o.getClass()).toString(value));
            strb.append(JsonLexer.COMMA);
        }
        
        strb.setLength(strb.length() - 1);
        strb.append(JsonLexer.BRACE_R);
        return strb.toString();
    }
    
    @Override
    public Object toObject(Class<?> cls)
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
        
        if (map == null || !lexer.isObj())
            return map;// 不支持的类型或者不是 对象
            
        String key = null;
        String temp = null;
        int scope = lexer.scope();
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
                break;// 碰到结束符
                
            if (lexer.isColon() || lexer.isComma())
                continue;// 冒号或者逗号跳过
                
            if (lexer.isObj() || lexer.isArr())
                temp = (String) lexer.BaseParser().toObject(String.class);
            
            temp = temp != null ? temp : lexer.value();
            if (key != null)
            {
                if (JsonLexer.NULL.equals(temp))
                {
                    if (!(map instanceof ConcurrentHashMap))
                        map.put(key, JsonLexer.removeStartEndQuotation(temp));// 必须不是 ConcurrentHashMap 的子类 因为ConcurrentHashMap不允许put null值
                }
                else
                    map.put(key, JsonLexer.removeStartEndQuotation(temp));
                
                key = null;
            }
            else
                key = JsonLexer.removeStartEndQuotation(temp);
            
            temp = null;
            continue;
        }
        
        if (key != null && !(map instanceof ConcurrentHashMap))
            map.put(key, null);
        key = null;
        return map;
    }
}
