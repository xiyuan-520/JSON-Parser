package com.xiyuan.util.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 子token数
 *
 * @version v1.0.0 @author lgz 2019-9-6 新建与整理
 */
public final class TokenContext implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final int capacity = 500;
    
    private int index = -1;
    private Token[] tokens = new Token[capacity];
    private Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
    private int begin = -1;
    private List<Integer> cur;
    
    public int size(int begin)
    {
        return map.containsKey(begin) ? map.get(begin).size() : 0;
    }
    
    public Token context(Token token, String json)
    {
        if (token == null)
            return token;
        
        if (token.begin() < 0)
            return token;
        
        synchronized (this)
        {
            if (this.begin == token.begin() || (token.type() != Token.BRACE_L && token.type() != Token.BRACKET_L))
                return token;
            
            this.begin = token.begin();
            if (!map.containsKey(begin))
                push(token);
            else
                cur = map.get(begin);
            return token;
        }
        
    }
    
    /**返回当前token 的索引*/
    private int push(Token token)
    {
        if ((tokens.length - 1) == index)
        {
            Token[] arr = new Token[tokens.length + capacity];
            System.arraycopy(tokens, 0, arr, 0, tokens.length);
            this.tokens = null;
            this.tokens = arr;
        }
        
        this.tokens[++index] = token;
        return index;
    }
    
    public int beginCount()
    {
        return map.size();
    }
    
    /**
     * 添加子元素
     * 
     */
    public Token addIncrement(Token token, boolean filter)
    {
        if (token == null || filter)
            return token;
        
        if (cur == null)
        {
            this.cur = new ArrayList<Integer>();
            map.put(this.begin, this.cur);
        }
        this.cur.add(push(token));//
        
        return token;
    }
    
    /***
     * 返回 指定token 字符索引的token
     * @param begin
     * @return
     */
    public Token complated(int begin,String json)
    {
        Token root = null;
        for (int i = 0; i <= index; i++)
        {
            Token token = tokens[i];
            if (root == null && token.begin() == begin)
                root = token;
            
            List<Integer> ls = map.get(token.begin());
            if (ls == null || ls.size() == 0)
                continue;
            
            Token[] arr = new Token[ls.size()];
            for (int j = 0; j < ls.size(); j++)
                arr[j] = tokens[ls.get(j)];
            
            token.initList(arr);
            @SuppressWarnings("unused")
            int ss = 22;
        }
        
        map.clear();
        cur = null;
        this.begin = index = -1;
        tokens = null;
        
//        for (Token item : root.getElements())
//        {
//            this.begin++;
//            if ((this.begin) < 5)
//                System.out.println(item.toString(json));
//        }
        return root;
    }
}
