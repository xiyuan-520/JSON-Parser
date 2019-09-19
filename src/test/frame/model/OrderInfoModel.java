/*
 * 版权所有 (C) 2018 知启蒙(ZHIQIM) 保留所有权利。
 * 
 * 欢迎到知启蒙网站（https://www.zhiqim.com）购买正版软件，知启蒙还提供许多开源框架和软件。
 * 
 * 1、本软件产品所有源代码受《中华人民共和国著作权法》和其他有关法律、法规的保护，其所有知识产权归湖南知启蒙科技有限公司所有；
 * 2、禁止复制和修改。不得复制修改、翻译或改编本软件所有源代码，或者基于本软件产品创作衍生作品；
 * 3、禁止进行逆向工程。不得对本软件的源代码进行逆向工程、反编译或试图以其他方式发现软件的源代码；
 * 4、个别授权：如需进行商业性的销售、复制、分发，包括但不限于软件销售、预装、捆绑等，必须获得知启蒙的书面授权和许可；
 * 5、保留权利：本注释未明示授权的其他一切权利仍归知启蒙所有，任何人使用其他权利时必须获得知启蒙的书面同意。
*/
package frame.model;

import java.util.List;

import com.xiyuan.util.json.Jsons;

/**
 * 订单信息model类
 * @version v1.0.0 @author zhouwenbin 2017-8-29 新建与整理
 */
public class OrderInfoModel
{
    private OrdOrder order;
    private OrdOrderDesign orderDesign;
    private List<OrdOrderPrice> orderPriceList;
    private List<OrdOrderAtta> attaList;
    private List<OrdOrderUse> orderUseList;
    
    public OrderInfoModel()
    {
    }
    
    public OrderInfoModel(OrdOrder order, OrdOrderDesign orderDesign)
    {
        this.order = order;
        this.orderDesign = orderDesign;
    }
    
    public OrdOrder getOrder()
    {
        return order;
    }
    public void setOrder(OrdOrder order)
    {
        this.order = order;
    }
    public OrdOrderDesign getOrderDesign()
    {
        return orderDesign;
    }
    public void setOrderDesign(OrdOrderDesign orderDesign)
    {
        this.orderDesign = orderDesign;
    }
    public List<OrdOrderPrice> getOrderPriceList()
    {
        return orderPriceList;
    }
    public void setOrderPriceList(List<OrdOrderPrice> orderPriceList)
    {
        this.orderPriceList = orderPriceList;
    }
    public List<OrdOrderAtta> getAttaList()
    {
        return attaList;
    }
    public void setAttaList(List<OrdOrderAtta> attaList)
    {
        this.attaList = attaList;
    }
    public List<OrdOrderUse> getOrderUseList()
    {
        return orderUseList;
    }
    public void setOrderUseList(List<OrdOrderUse> orderUseList)
    {
        this.orderUseList = orderUseList;
    }
    
    @Override
    public String toString()
    {
        return Jsons.toString(this);
    }
}
