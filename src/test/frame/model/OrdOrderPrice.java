/*
 * 版权所有 (C) 2018 知启蒙(ZHIQIM) 保留所有权利。 欢迎到知启蒙网站（https://www.zhiqim.com）购买正版软件，知启蒙还提供许多开源框架和软件。 1、本软件产品所有源代码受《中华人民共和国著作权法》和其他有关法律、法规的保护，其所有知识产权归湖南知启蒙科技有限公司所有；
 * 2、禁止复制和修改。不得复制修改、翻译或改编本软件所有源代码，或者基于本软件产品创作衍生作品； 3、禁止进行逆向工程。不得对本软件的源代码进行逆向工程、反编译或试图以其他方式发现软件的源代码； 4、个别授权：如需进行商业性的销售、复制、分发，包括但不限于软件销售、预装、捆绑等，必须获得知启蒙的书面授权和许可；
 * 5、保留权利：本注释未明示授权的其他一切权利仍归知启蒙所有，任何人使用其他权利时必须获得知启蒙的书面同意。
 */

package frame.model;

import java.io.Serializable;

import com.xiyuan.util.json.JsonUtil;

/**
 * 订单报价表 对应表《ORD_ORDER_PRICE》
 */
public class OrdOrderPrice implements Serializable
{

    private static final long serialVersionUID = 1L;

    private long priceId;    // 1.报价ID
    private long oid;    // 2.订单编号
    private int priceType;    // 3.收费项类型，产品类 ,加工类，设计类，物流类 ，优惠类等
    private String priceName;    // 4.项目名称
    private long price;    // 5.单价
    private int total;    // 6.数量
    private long amount;    // 7.总额
    private int agree;    // 8.优惠列表专用字段:0为同意优惠,1同意优惠

    @Override
    public String toString()
    {
        return JsonUtil.toString(this);
    }

    public long getPriceId()
    {
        return priceId;
    }

    public void setPriceId(long priceId)
    {
        this.priceId = priceId;
    }

    public long getOid()
    {
        return oid;
    }

    public void setOid(long oid)
    {
        this.oid = oid;
    }

    public int getPriceType()
    {
        return priceType;
    }

    public void setPriceType(int priceType)
    {
        this.priceType = priceType;
    }

    public String getPriceName()
    {
        return priceName;
    }

    public void setPriceName(String priceName)
    {
        this.priceName = priceName;
    }

    public long getPrice()
    {
        return price;
    }

    public void setPrice(long price)
    {
        this.price = price;
    }

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public long getAmount()
    {
        return amount;
    }

    public void setAmount(long amount)
    {
        this.amount = amount;
    }

    public int getAgree()
    {
        return agree;
    }

    public void setAgree(int agree)
    {
        this.agree = agree;
    }

}
