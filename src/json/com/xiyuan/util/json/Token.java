/*
 * 版权所有 (C) 2018 知启蒙(ZHIQIM) 保留所有权利。 欢迎到知启蒙网站（https://www.zhiqim.com）购买正版软件，知启蒙还提供许多开源框架和软件。 1、本软件产品所有源代码受《中华人民共和国著作权法》和其他有关法律、法规的保护，其所有知识产权归湖南知启蒙科技有限公司所有；
 * 2、禁止复制和修改。不得复制修改、翻译或改编本软件所有源代码，或者基于本软件产品创作衍生作品； 3、禁止进行逆向工程。不得对本软件的源代码进行逆向工程、反编译或试图以其他方式发现软件的源代码； 4、个别授权：如需进行商业性的销售、复制、分发，包括但不限于软件销售、预装、捆绑等，必须获得知启蒙的书面授权和许可；
 * 5、保留权利：本注释未明示授权的其他一切权利仍归知启蒙所有，任何人使用其他权利时必须获得知启蒙的书面同意。
 */

package com.xiyuan.util.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
    private static final long serialVersionUID = 1L;
    /** 未知类型 0 token  */
    public final static byte ZERO = 0;
    /** 左大括号类型 1 = { */
    public final static byte BRACE_L = 1;// "{"
    /** 右大括号类型 2 = } */
    public final static byte BRACE_R = 2;// "}"
    /** 左中括号类型 3 = [ */
    public final static byte BRACKET_L = 3;// "["
    /** 右中括号类型 4 = ] */
    public final static byte BRACKET_R = 4;// "]"
    /** 冒号类型 5 = : */
    public final static byte COLON = 5;// ":"
    /** 逗号类型 6 = , */
    public final static byte COMMA = 6;// ","
    /** 字符类型 7 */
    public final static byte STRING = 7;// String值
    private Token()
    {
    }

    /**
     * 提供静态构造
     * 
     * @param type token类型
     * @param begin token开始索引
     * @return
     */
    public static Token newToken(byte type, int begin)
    {
        Token token = new Token();
        token.type = type;
        token.begin = begin < 0 ? 0 : begin;
        token.end = token.begin;// 默认是当前索引
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

    /** 设置结束索引 */
    public void end(int end)
    {
        this.end = end;
    }

    /**
     * 添加子元素
     * 
     * @param token
     * @param filterComma 是否过滤逗号
     */
    public void addToken(Token token, boolean filterComma)
    {
        
        if (list == null && (type == BRACE_L || type == BRACKET_L))
            this.list = new Token[10];

        if (filterComma && token.type == COMMA)
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

        if (size != list.length)
        {
            Token[] datas = new Token[size];
            System.arraycopy(list, 0, datas, 0, size);
            this.list = datas;
        }
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
        List<Token> ls = new ArrayList<Token>(this.list.length - getElementSize(filterType));
        for (Token elem : getElements())
        {
            if (elem.type() == filterType)
                continue;
            ls.add(elem);
        }
        return ls;
    }

    /** 获取指定元素类型的数量 **/
    public int getElementSize(byte... types)
    {
        if (types == null)
            return 0;

        Map<Byte, Byte> map = new HashMap<Byte, Byte>();
        for (byte type : types)
            map.put(type, Jsons.ZERO);

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

        List<Token> ls = new ArrayList<Token>(getElementSize(STRING));
        for (Token elem : getElements())
            if (elem.type() == STRING)
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
        List<Token> ls = new ArrayList<Token>(getElementSize(BRACE_L));
        for (Token elem : getElements())
        {
            if (elem.type() == BRACE_L)
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
            if (elem.type() == BRACKET_L)
                lenth++;// 先进行length计算是为了初始化list的长度
                        // 避免list.add方法调用System.arrayCopy 从而降低时间损耗
        }

        List<Token> ls = new ArrayList<Token>(lenth);
        for (Token elem : getElements())
        {
            if (elem.type() == BRACKET_L)
                ls.add(elem);
        }
        return ls;
    }

    public int initList(Token[] list)
    {
        this.list = list;
        return (this.size = list == null ? 0 : list.length);
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

    @Override
    public String toString()
    {
        return "{\"size\":" + size + ", \"type\":" + type + ", \"begin\":" + begin + ", \"end\":" + end + "}";
    }
    
    
}
