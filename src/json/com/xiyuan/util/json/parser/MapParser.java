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
        StringBuilder strb = new StringBuilder(map.size()*500).append(JsonLexer.BRACE_L);
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
        return  toObject(cls, String.class, String.class);
    }
    
    /***
     * 解析成map
     * @param <K>
     * @param <V>
     * @param mapClass          map类型
     * @param keyClass          key的类型
     * @param valueClass        value的类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public <K, V> Object toObject(Class<?> mapClass, Class<K> keyClass, Class<V> valueClass)
    {// TODO 以后 获取cls 具体类型构造map 集构造类型，目前只放入 String
    
        if (!isSupportClass(mapClass) || !lexer.isObj())
            return null;// 不支持的类型或者不是 对象
        
        Map<Object, Object> map =  (Map<Object, Object>) newMap(mapClass, keyClass, valueClass, 64);
        Object key = null;
        Object value = null;
        boolean isValue = false;
        int scope = lexer.scope();
        while (lexer.hasNext())
        {
            lexer.naxtToken();
            if (lexer.scope() < scope || lexer.isEOF())
            {
                isValue = false;
                break;// 碰到结束符
            }
                
            if (lexer.isColon() || lexer.isComma())
            {
                if (lexer.isColon() && key != null)
                    isValue = true;//已经找到key 当前是冒号，说明下一个token为值
                continue;// 冒号或者逗号跳过
            }
            
            if (key != null && isValue)
            {
                value = lexer.getParser(valueClass).toObject(valueClass);
                if (value == null || JsonLexer.NULL.equals(value))
                {
                    if (!(map instanceof ConcurrentHashMap))
                        map.put(key, value);// 必须不是 ConcurrentHashMap 的子类 因为ConcurrentHashMap不允许put null值
                }
                else
                    map.put(key, value);
                
                key = null;
                isValue = false;
            }
            else
                key = lexer.getParser(keyClass).toObject(keyClass);
            
            value = null;
            isValue = false;
        }
        
//        if (key != null  && !(map instanceof ConcurrentHashMap))
//            map.put(key, null);
        key = null;
        isValue = false;
        return map;
    }
    
    public <K, V> Map<K, V> newMap(Class<?> mapClass,Class<K> k, Class<V> v, int capcity)
    {
        Map<K, V> map = null;
        if (mapClass == Map.class || mapClass == HashMap.class)
            map = new HashMap<K, V>(capcity);
        else if (mapClass == ConcurrentMap.class || mapClass == ConcurrentHashMap.class)
            map = new ConcurrentHashMap<K, V>(capcity);
        else if (mapClass == Hashtable.class)
            map = new Hashtable<K, V>(capcity);
        else if (mapClass == LinkedHashMap.class)
            map = new LinkedHashMap<K, V>(capcity);
        else if (mapClass == TreeMap.class)
            map = new TreeMap<K, V>();
        return map;
    }
    
    
    /**
     *  判断支持的集合类型
     */
    private boolean isSupportClass(Class<?> cls)
    {
        if (cls == Map.class 
                || cls == HashMap.class 
                || cls == ConcurrentMap.class 
                || cls == ConcurrentHashMap.class 
                || cls == Hashtable.class 
                || cls == LinkedHashMap.class 
                || cls == TreeMap.class)
            return true;
        
        return false;
    }
}
