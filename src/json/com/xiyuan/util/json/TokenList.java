/*
 * 版权所有 (C) 2015 知启蒙(ZHIQIM) 保留所有权利。
 * 
 * 指定登记&发行网站： https://www.zhiqim.com/ 欢迎加盟知启蒙，[编程有你，知启蒙一路随行]。
 *
 * 本文采用《知启蒙许可证》，除非符合许可证，否则不可使用该文件！
 * 1、您可以免费使用、修改、合并、出版发行和分发，再授权软件、软件副本及衍生软件；
 * 2、您用于商业用途时，必须在原作者指定的登记网站，按原作者要求进行登记；
 * 3、您在使用、修改、合并、出版发行和分发时，必须包含版权声明、许可声明，及保留原作者的著作权、商标和专利等知识产权；
 * 4、您在互联网、移动互联网等大众网络下发行和分发再授权软件、软件副本及衍生软件时，必须在原作者指定的发行网站进行发行和分发；
 * 5、您可以在以下链接获取一个完整的许可证副本。
 * 
 * 许可证链接：http://zhiqim.org/licenses/zhiqim_register_publish_license.htm
 * 
 * 除非法律需要或书面同意，软件由原始码方式提供，无任何明示或暗示的保证和条件。详见完整许可证的权限和限制。
 */
package com.xiyuan.util.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenList
{
    private Token root;
    private int size;
    private int parent = -1;
    private Token[] list;
    private Map<Integer, List<Integer>> map;

    public TokenList(int capacity)
    {
        if (capacity < 10)
            capacity = 10;
        list = new Token[capacity];
//        map = new HashMap<Integer, List<Integer>>();
    }

    public void addToken(Token token, boolean filterComma, boolean hasList)
    {
        if (token == null || list == null)
            return;

        if (filterComma && token.type() == JsonUtil.T_COMMA)
            return;

        if (size == Integer.MAX_VALUE)
            return;// 达到最大指

        if ((size + 1) > this.list.length)
        {
            Token[] datas = new Token[size + 520];
            System.arraycopy(list, 0, datas, 0, size);
            this.list = datas;
        }

        if (parent >= 0 && map != null)// 父节点添加当前
        {
            map.get(parent).add(size);
        }

        if (token.type() == JsonUtil.T_BRACE_L || token.type() == JsonUtil.T_BRACKET_L)
        {
            // 当前节点为父节点
            if (hasList)
                this.parent = token.begin();
            
//            if ( map != null && map.isEmpty())
//                this.root = token;
            if ( size == 0)
                this.root = token;
            
            if (map != null)
            {
                List<Integer> ls = map.get(token.begin());
                if (ls == null)
                {
                    ls = new ArrayList<Integer>(0);
                    map.put(token.begin(), ls);
                }
            }
        }

        this.list[size++] = token;
    }

    /**
     * 设置当前节点为父节点
     */
    public void setCur(Token token)
    {
        this.parent = token.begin();
    }
    
    public Token getRoot()
    {
        return root;
    }
    
    public void finish()
    {
        
        if (map == null)
            return;
        
        
        for (int i = 0; i < size; i++)
        {
            Token token = list[i];
            if (token.type() != JsonUtil.T_BRACE_L && token.type() != JsonUtil.T_BRACKET_L)
                continue;//只允许 { 或者 [ 的token 有子token
            
            List<Integer> ls = map.get(token.begin());
            Token[] arr = new Token[ls.size()];
            int si = 0;
            for (Integer index : ls)
                arr[si++] = list[index];
            token.setList(arr);
        }
    }
}
