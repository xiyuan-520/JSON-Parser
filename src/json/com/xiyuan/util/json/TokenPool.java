package com.xiyuan.util.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 子token数
 * 
 * @version v1.0.0 @author lgz 2019-9-6 新建与整理
 */
public final class TokenPool implements Serializable
{
    public static String json = null;
    private static final long serialVersionUID = 1L;

    private volatile Token last;
    private Token root;
    private int capacity = 1000;
    private int index;
    private Token[] cur;// 当前页
    private List<Token[]> ls = new ArrayList<Token[]>();

    public TokenPool(int capacity)
    {
        if (capacity > this.capacity)
            this.capacity = capacity;

        addPage();
    }

    private void addPage()
    {
        this.cur = new Token[capacity];
        this.ls.add(cur);
        this.index = -1;
    }

    public Token add(Token token)
    {
        last = token;
        if (root == null)
            root = token;

        if ((index >= (capacity - 1)))
            addPage();// 创建新分页

        this.cur[++index] = token;
        token.index(index);
        return token;
    }

    public Token last()
    {
        return last;
    }

    public Token get(int index)
    {
        if (this.ls.size() == 0)
            return null;

        int page = index / capacity;
        index = index % capacity;

        return ls.get(page)[index];
    }

    public int size()
    {
        if (this.ls.isEmpty())
            return 0;

        return (this.ls.size() - 1) * capacity + index + 1;
    }
}
