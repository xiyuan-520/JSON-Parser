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
    private static int a = 0;
    private static int b = 0;
    private static String json = null;
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
            
            if (!map.containsKey(token.begin()))
            {
                this.cur = new ArrayList<Integer>();
                map.put(token.begin(), this.cur);
                if (index==-1)//初始值
                    push(token);
            }
            else
                cur = map.get(token.begin());
            
            this.begin = token.begin();
            return token;
        }
        
    }
    
    /**返回当前token 的索引*/
    private int push(Token token)
    {
        if ((tokens.length - 1) == index)
        {
            long l1 = System.currentTimeMillis();
            Token[] arr = new Token[tokens.length + capacity];
            System.arraycopy(tokens, 0, arr, 0, tokens.length);
            this.tokens = null;
            this.tokens = arr;
            long l2 = System.currentTimeMillis();
            long diff = l2-l1;
            if (diff > 1)
               a += diff;
        }
        
        if (this.index >= token.begin())
            return -1;// 已经添加过了
            
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
        if (token == null || (filter && token.type() == Token.COMMA))
            return token;
        
        int ind = push(token);
        
        long l1 = System.currentTimeMillis();
       
      
        if (ind >= 0)
            this.cur.add(ind);//
        
        long l2 = System.currentTimeMillis();
        long diff = l2-l1;
        if (diff > 1)
           b += diff;
        return token;
    }
    
    /***
     * 返回 指定token 字符索引的token
     * @param begin
     * @return
     */
    public Token complated(int begin, String json)
    {
        long l1 = System.currentTimeMillis();
        Token root = null;
        for (int i = 0; i <= index; i++)
        {
            Token token = tokens[i];
            // System.out.println(i + "--" + token.begin() + "=" + (token.type() == Token.STRING ? token.toString(json) : token.toString(json).charAt(0)));
            if (root == null && token.begin() == begin)
                root = token;
            
            List<Integer> ls = map.get(token.begin());
            if (ls == null || ls.size() == 0)
                continue;
            
            Token[] arr = new Token[ls.size()];
            for (int j = 0; j < ls.size(); j++)
                arr[j] = tokens[ls.get(j)];
            
            token.initList(arr);
//            if (token != root)
//                System.out.println(i + "" + token.toString(json));
        }
        long l2 = System.currentTimeMillis();
        System.out.println("complated"+(l2-l1));
        map.clear();
        cur = null;
        this.begin = index = -1;
        tokens = null;
        
//        System.out.println(root.toString(json));
        System.out.println("数组组拷贝耗时"+a);
        System.out.println("list.add耗时"+b);
        return root;
    }
}
