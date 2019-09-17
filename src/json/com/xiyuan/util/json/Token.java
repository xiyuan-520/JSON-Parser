package com.xiyuan.util.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * token标记符
 *
 * @version v1.0.0 @author lgz 2019-9-6 新建与整理
 */
public final class Token implements Serializable
{
    public static String json = null;
    private static final long serialVersionUID = 1L;
    
    private Token()
    {
    }
    
    /**
     * 提供静态构造
     * 
     * @param type      token类型
     * @param begin     token开始索引
     * @return
     */
    public static Token newToken(byte type, int begin)
    {
        
        Token token = new Token();
        token.type = type;
        token.begin = begin < 0 ? 0 : begin;
        token.end = token.begin;// 默认是当前索引
        if (type == JsonUtil.T_BRACE_L || type == JsonUtil.T_BRACKET_L)
            token.list = new Token[10];
        
        return token;
    }
    
    private int size = 0;
    private byte type = 0;
    private int begin = 0;// 字符串值 的开始索引
    private int end = 0;
    private Token[] list;
    
    public byte type()
    {
        return type;
    }
    
    /** 
     * 获取token开始字字符索引 
     * */
    public int begin()
    {
        return begin;
    }
    
    /** 获取token结束字字符索引 */
    public int end()
    {
        return end;
    }
    
    /**设置结束索引*/
    public void end(int end)
    {
        this.end = end;
    }
    
    /**
     * 添加子元素
     * 
     * @param token
     * @param filterComma       是否过滤逗号
     */
    public void addToken(Token token, boolean filterComma)
    {
        if (token == null || list == null)
            return;
        
        if (filterComma && token.type == JsonUtil.T_COMMA)
            return;
        
        if (size == Integer.MAX_VALUE)
            return;// 达到最大指
            
        if ((size + 1) > this.list.length)
        {
            Token[] datas = new Token[size + 520];
            System.arraycopy(list, 0, datas, 0, size);
            this.list = datas;
        }
        
        this.list[size++] = token;
    }
    
    /**
     * 获取子节点列表
     * 
     * @return 返回子元素列表
     */
    public Token[] getElements()
    {
        if (this.list == null)
            return new Token[0];
        
        if (size == this.list.length)
            return this.list;
        
        Token[] datas = new Token[size];
        System.arraycopy(list, 0, datas, 0, size);
        this.list = datas;
        return this.list;
    }
    
    /**
     * 获取子节点列表
     * 
     * @param 过滤指定token类型
     * @return 返回子元素列表
     */
    
    public List<Token> getElements(byte filterType)
    {
        List<Token> ls = new ArrayList<Token>(this.size - getElementSize(filterType));
        for (Token elem : getElements())
        {
            if (elem.type() == filterType)
                continue;
            ls.add(elem);
        }
        return ls;
    }
    
    /** 获取指定元素类型的数量 **/
    public int getElementSize(byte...types)
    {
        if (types == null)
            return 0;
        
        Map<Byte, Byte> map = new HashMap<Byte, Byte>();
        for (byte type : types)
            map.put(type, JsonUtil.ZERO);
        
        int count = 0;
        for (Token elem : getElements())
        {
            if (map.containsKey(elem.type()))
                count++;
        }
        
        return count;
    }
    
    /**
     * 获取键或者值的子元素列表
     * 
     * @return 返回子元素列表
     */
    public List<Token> getStringElements()
    {
        
        List<Token> ls = new ArrayList<Token>(getElementSize(JsonUtil.T_VALUE));
        for (Token elem : getElements())
            if (elem.type() == JsonUtil.T_VALUE)
                ls.add(elem);
        
        return ls;
    }
    
    /***
     * 获取对象类型的元素列表
     * 
     * @return
     */
    public List<Token> getObjectElements()
    {
        List<Token> ls = new ArrayList<Token>(getElementSize(JsonUtil.T_BRACE_L));
        for (Token elem : getElements())
        {
            if (elem.type() == JsonUtil.T_BRACE_L)
                ls.add(elem);
        }
        return ls;
    }
    
    /***
     * 获取数组类型的元素列表
     * 
     * @return
     */
    public List<Token> getArrayElements()
    {
        int lenth = 0;
        for (Token elem : getElements())
        {
            if (elem.type() == JsonUtil.T_BRACKET_L)
                lenth++;// 先进行length计算是为了初始化list的长度 避免list.add方法调用System.arrayCopy 从而降低时间损耗
        }
        
        List<Token> ls = new ArrayList<Token>(lenth);
        for (Token elem : getElements())
        {
            if (elem.type() == JsonUtil.T_BRACKET_L)
                ls.add(elem);
        }
        return ls;
    }
    
    /**
     * 获取元素列表大小
     * 
     * @return
     */
    public int size()
    {
        return size;
    }
    
    public String toString(String json)
    {
        if (this.end < 0)
            return null;
        
        if (json == null || json.length() == 0 || this.end > json.length())
            return null;
        
        if (json.length() <= this.end)
            return (json.substring(this.begin));
        else
            return (json.substring(this.begin, this.end + 1));
    }
    
}
