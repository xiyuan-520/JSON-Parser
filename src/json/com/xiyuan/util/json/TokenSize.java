package com.xiyuan.util.json;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 子token数
 *
 * @version v1.0.0 @author lgz 2019-9-6 新建与整理
 */
public final class TokenSize implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public TokenSize()
    {
        map.put(begin, size);
    }
    
    private Map<Integer, AtomicInteger> map = new HashMap<Integer, AtomicInteger>();
    private int begin = 0;
    private AtomicInteger size = new AtomicInteger(1);
    
    public int size(int begin)
    {
        return map.containsKey(begin) ? map.get(begin).get() : 0;
    }
    
    public void begin(int begin, String json)
    {
        synchronized (this)
        {
//            this.token = json.charAt(begin);
            if (this.begin == begin)
                return;
            
            this.begin = begin;
            if (!map.containsKey(begin))
            {
                size = new AtomicInteger(1);
                map.put(begin, size);
            }
            else
                this.size = map.get(begin);
        }
    }
    
    
    public int beginCount()
    {
        return map.size();
    }
    
    
    public void begins(String json)
    {
        int s = 0;
        for (Entry<Integer, AtomicInteger> index : map.entrySet())
        {
            if (s < 5)
            {
                System.out.println(index.getKey() +":"+index.getValue()+"==" +json.charAt(index.getKey()));
            }
           
            this.size(index.getKey());
            
            s++;
        }
    }
    /**
     * 添加子元素
     * 
     * @param token
     * @param filterComma       是否过滤逗号
     */
    public void addIncrement()
    {
        size.getAndIncrement();
    }
}
