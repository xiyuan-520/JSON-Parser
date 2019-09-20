/*
 * 版权所有 (C) 2018 知启蒙(ZHIQIM) 保留所有权利。 欢迎到知启蒙网站（https://www.zhiqim.com）购买正版软件，知启蒙还提供许多开源框架和软件。 1、本软件产品所有源代码受《中华人民共和国著作权法》和其他有关法律、法规的保护，其所有知识产权归湖南知启蒙科技有限公司所有；
 * 2、禁止复制和修改。不得复制修改、翻译或改编本软件所有源代码，或者基于本软件产品创作衍生作品； 3、禁止进行逆向工程。不得对本软件的源代码进行逆向工程、反编译或试图以其他方式发现软件的源代码； 4、个别授权：如需进行商业性的销售、复制、分发，包括但不限于软件销售、预装、捆绑等，必须获得知启蒙的书面授权和许可；
 * 5、保留权利：本注释未明示授权的其他一切权利仍归知启蒙所有，任何人使用其他权利时必须获得知启蒙的书面同意。
 */

package com.xiyuan.util.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1.Token类提供root() 方法，用于创建token的跟
 * 2.创建跟token后 有token.next(byte type, int begin) 派生子token
 * @version v1.0.0 @author lgz 2019-9-20 新建与整理
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
    /**提供床架根token方法*/
    public static Token root(byte type, int begin)
    {
        return new Token(type, begin);
    }
    
    private byte type;
    private int begin;
    private int end;
    
    private Token context;//当前token所在作用域
    private Token prev;
    private Token next;
    
    private Token(byte type, int begin)
    {
        this.type = type;
        this.begin = begin;
        this.end = this.begin;
    }
    
    /**
     * 生成下一个 返回 下一个token
     * 注：当下一个token类型为逗号时，不自动设置为当前的下一个token，需要手动社会
     * @param type          下一个token的类型
     * @param begin         下一个token的字符索引
     * @return
     */
    public Token next(byte type, int begin)
    {
        Token next = new Token(type,begin);
        if (type == COMMA)
            return next;
        
        return next(next);
    }
    
    /**获取下一个token*/
    public Token next()
    {
        return this.next;
    }
    /**设置下一个token
     * 注：这里不是插入，可能会丢失数据，举例：token序列 =[1,2,3,4,5,6,7] this = 1, 目标next=5,然后 1.next(5) 之后，2，3，4将会丢失，而设置后的token序列=[1,5,6,7]
     * */
    public Token next(Token next)
    {
        this.next = next;
        next.context = this.type==BRACE_L || this.type == BRACKET_L ? this : this.context;
        next.prev = this;
        return this.next;
    }
    
    /**获取子token的数量
     * 注意：是实时遍历，有损性能
     * 
     * @param filters   过滤类型
     * */
    public int size(byte... filters)
    {
        int size = 0;
        Token next = this.next;
        Token context = next == null  ? null : next.context;
        while (next != null)
        {
            if (next.context != context)
            {//子集子集里面跳过
                next = next.next;
                continue;
            }
            
            if (next.context == this.context)
                break;//和当前对象平级作用域，说明已经退出
            
            next = next.next;
            size++;
        }
        
        return size;
    }
    
    /**
     * 获取上一个token
     */
    public Token prev()
    {
        return this.prev;
    }
    
    /**设置token结束索引*/
    public void end(int end)
    {
        this.end = end;
    }
    /**获取token开始索引*/
    public int begin()
    {
        return this.begin;
    }
    /**获取token类型*/
    public byte type()
    {
        return this.type;
    }
    /**获取token结束索引*/
    public int end()
    {
        return this.end;
    }
    
    @Override
    public String toString()
    {
        return "Token [type=" + type + ", begin=" + begin + ", end=" + end + "]";
    }
    
    public String toString(String json)
    {
        return  toString();
    }
}
