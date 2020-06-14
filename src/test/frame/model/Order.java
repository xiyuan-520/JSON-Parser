/*
 * 版权所有 (C) 2001-2013 深圳市凌亚科技有限公司。保留所有权利。
 * 版本：
 * 修改记录：
 *		1、2013-5-25，zhouwenbin创建。 
 */
package frame.model;

import org.zhiqim.kernel.annotation.AnAlias;

/**
 * 
 * 子订单信息
 * "orders.oid,orders.status,orders.cid,orders.modified,orders.payment,orders.end_time,orders.consign_time," + //子订单重要信息
 * "orders.num_iid,orders.title,orders.price,orders.num,orders.pic_path,orders.outer_iid," +                   //子订单商品信息
 * "orders.sku_id,orders.outer_sku_id,orders.sku_properties_name," +                                           //子订单SKU信息
 * "orders.buyer_rate,orders.seller_rate," +                                                                   //子订单评价状态
 * "orders.item_meal_id,orders.item_meal_name," +                                                              //子订单套餐信息
 * "orders.refund_id,orders.refund_status," +                                                                  //子订单退款信息
 * "orders.shipping_type,orders.logistics_company,orders.invoice_no,orders.is_daixiao,orders.store_code";      //子订单物流信息
 */
public class Order
{
    //子订单重要信息
    private long oid;//     Number  是   2231958349  子订单编号
    private String status;//  String  是   TRADE_NO_CREATE_PAY     订单状态（请关注此状态，如果为TRADE_CLOSED_BY_TAOBAO状态，则不要对此订单进行发货，切记啊！）。可选值:
    private long cid;//       Number  否   123456  交易商品对应的类目ID
    private String modified;//    Date    否   2000-01-01 00:00:00     订单修改时间，目前只有taobao.trade.ordersku.update会返回此字段。
    private String payment;//       String  是   200.07  子订单实付金额。精确到2位小数，单位:元。如:200.07，表示:200元7分。计算公式如下：payment = price * num + adjust_fee - discount_fee + post_fee(邮费，单笔子订单时子订单实付金额包含邮费，多笔子订单时不包含邮费)；对于退款成功的子订单，由于主订单的优惠分摊金额，会造成该字段可能不为0.00元。建议使用退款前的实付金额减去退款单中的实际退款金额计算。
    
    private String end_time;//    Date    是   2012-04-07 00:00:00     子订单的交易结束时间 说明：子订单有单独的结束时间，与主订单的结束时间可能有所不同，在有退款发起的时候或者是主订单分阶段付款的时候，子订单的结束时间会早于主订单的结束时间，所以开放这个字段便于订单结束状态的判断
    
    private String consign_time;//      String  是   2013-01-13 15:23:00     子订单发货时间，当卖家对订单进行了多次发货，子订单的发货时间和主订单的发货时间可能不一样了，那么就需要以子订单的时间为准。（没有进行多次发货的订单，主订单的发货时间和子订单的发货时间都一样）
    
    //子订单商品信息
    private long num_iid;//       Number  否   2342344     商品数字ID
    private String title;//   String  是   山寨版测试机器     商品标题
    private String price;//   String  是   200.07  商品价格。精确到2位小数;单位:元。如:200.07，表示:200元7分
    private long num;//      Number  是   1   购买数量。取值范围:大于零的整数
    private String pic_path;//      String  是   http://img08.taobao.net/bao/uploaded/i8/T1jVXXXePbXXaoPB6a_091917.jpg   商品图片的绝对路径
    private String outer_iid;//     String  是   152e442aefe88dd41cb0879232c0dcb0    商家外部编码(可与商家外部系统对接)。外部商家自己定义的商品Item的id，可以通过taobao.items.custom.get获取商品的Item的信息
    
    //子订单SKU信息
    private String sku_id;//    String  是   5937146     商品的最小库存单位Sku的id.可以通过taobao.item.sku.get获取详细的Sku信息
    private String outer_sku_id;//      String  是   81893848    外部网店自己定义的Sku编号
    private String sku_properties_name;//       String  是   颜色:桔色;尺码:M  SKU的值。如：机身颜色:黑色;手机套餐:官方标配
    
    //子订单评价状态
    private boolean buyer_rate;//  Boolean     是   true    买家是否已评价。可选值：true(已评价)，false(未评价)
    private boolean seller_rate;//     Boolean     是   true    卖家是否已评价。可选值：true(已评价)，false(未评价)
    
    //子订单套餐信息
    private long item_meal_id;//      Number  是   2564854632  套餐ID
    private String item_meal_name;//    String  是   M8原装电池:便携支架:M8专用座充:莫凡保护袋    套餐的值。如：M8原装电池:便携支架:M8专用座充:莫凡保护袋
    
    //子订单退款信息
    private long refund_id;//     Number  是   2231958349  最近退款ID
    private String refund_status;//     String  是   SUCCESS(退款成功)   退款状态。退款状态。可选值 WAIT_SELLER_AGREE(买家已经申请退款，等待卖家同意) WAIT_BUYER_RETURN_GOODS(卖家已经同意退款，等待买家退货) WAIT_SELLER_CONFIRM_GOODS(买家已经退货，等待卖家确认收货) SELLER_REFUSE_BUYER(卖家拒绝退款) CLOSED(退款关闭) SUCCESS(退款成功)
    
    //子订单物流信息
    private String shipping_type;//     String  是   post    子订单的运送方式（卖家对订单进行多次发货之后，一个主订单下的子订单的运送方式可能不同，用order.shipping_type来区分子订单的运送方式）
    private String logistics_company;//     String  否   顺风快递    子订单发货的快递公司名称
    private String invoice_no;//    String  否   05432465    子订单所在包裹的运单号
    
    //其他信息
    private String seller_type;//       String  是   B（商城商家）     卖家类型，可选值为：B（商城商家），C（普通卖家）
    private String seller_nick;//       String  是   麦包包     卖家昵称
    private String buyer_nick;//    String  是   碎银子     买家昵称
    private String order_from;//    String  是   jhs     子订单来源,如jhs(聚划算)、taobao(淘宝)、wap(无线)
    
    private String total_fee;//     String  是   200.07  应付金额（商品价格 * 商品数量 + 手工调整金额 - 订单优惠金额）。精确到2位小数;单位:元。如:200.07，表示:200元7分
    private String discount_fee;//      String  是   200.07  订单优惠金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
    private String adjust_fee;//    String  是   1.01    手工调整金额.格式为:1.01;单位:元;精确到小数点后两位.
    
    private boolean is_oversold; //    Boolean     是   true    是否超卖
    private boolean is_service_order;//    Boolean     否   true    是否是服务订单，是返回true，否返回false。
    private long bind_oid;//      Number  否   23194074143138  捆绑的子订单号，表示该子订单要和捆绑的子订单一起发货，用于卖家子订单捆绑发货
    private String snapshot_url;//      String  是   T1mURbXopZXXXe3rLI.1257513712679_snap   订单快照URL
    private String snapshot;//      String  是   自定义值    订单快照详细信息
    private String timeout_action_time;//       Date    是   2000-01-01 00:00:00     订单超时到期时间。格式:yyyy-MM-dd HH:mm:ss

    
    private boolean buyerRateValid;
    private boolean sellerRateValid;
    private String buyerRateCreated;
    private String sellerRateCreated;
    
    private String buyerRateResult;
    private String sellerRateResult;
    private String buyerRateContent;
    private String sellerRateContent;
    
    private String abbrTitle;
    
    public long getOid()
    {
        return oid;
    }
    public void setOid(long oid)
    {
        this.oid = oid;
    }
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getPrice()
    {
        return price;
    }
    public void setPrice(String price)
    {
        this.price = price;
    }
    public long getCid()
    {
        return cid;
    }
    public void setCid(long cid)
    {
        this.cid = cid;
    }
    public String getModified()
    {
        return modified;
    }
    public void setModified(String modified)
    {
        this.modified = modified;
    }
    public String getPayment()
    {
        return payment;
    }
    public void setPayment(String payment)
    {
        this.payment = payment;
    }
    public String getEnd_time()
    {
        return end_time;
    }
    public void setEnd_time(String end_time)
    {
        this.end_time = end_time;
    }
    public String getConsign_time()
    {
        return consign_time;
    }
    public void setConsign_time(String consign_time)
    {
        this.consign_time = consign_time;
    }
    public long getNum_iid()
    {
        return num_iid;
    }
    public void setNum_iid(long num_iid)
    {
        this.num_iid = num_iid;
    }
    public long getNum()
    {
        return num;
    }
    public void setNum(long num)
    {
        this.num = num;
    }
    public String getPic_path()
    {
        return pic_path;
    }
    public void setPic_path(String pic_path)
    {
        this.pic_path = pic_path;
    }
    public String getOuter_iid()
    {
        return outer_iid;
    }
    public void setOuter_iid(String outer_iid)
    {
        this.outer_iid = outer_iid;
    }
    public String getSku_id()
    {
        return sku_id;
    }
    public void setSku_id(String sku_id)
    {
        this.sku_id = sku_id;
    }
    public String getOuter_sku_id()
    {
        return outer_sku_id;
    }
    public void setOuter_sku_id(String outer_sku_id)
    {
        this.outer_sku_id = outer_sku_id;
    }
    public String getSku_properties_name()
    {
        return sku_properties_name;
    }
    public void setSku_properties_name(String sku_properties_name)
    {
        this.sku_properties_name = sku_properties_name;
    }
    public boolean isBuyer_rate()
    {
        return buyer_rate;
    }
    public void setBuyer_rate(boolean buyer_rate)
    {
        this.buyer_rate = buyer_rate;
    }
    public boolean isSeller_rate()
    {
        return seller_rate;
    }
    public void setSeller_rate(boolean seller_rate)
    {
        this.seller_rate = seller_rate;
    }
    public long getItem_meal_id()
    {
        return item_meal_id;
    }
    public void setItem_meal_id(long item_meal_id)
    {
        this.item_meal_id = item_meal_id;
    }
    public String getItem_meal_name()
    {
        return item_meal_name;
    }
    public void setItem_meal_name(String item_meal_name)
    {
        this.item_meal_name = item_meal_name;
    }
    public long getRefund_id()
    {
        return refund_id;
    }
    public void setRefund_id(long refund_id)
    {
        this.refund_id = refund_id;
    }
    public String getRefund_status()
    {
        return refund_status;
    }
    public void setRefund_status(String refund_status)
    {
        this.refund_status = refund_status;
    }
    public String getShipping_type()
    {
        return shipping_type;
    }
    public void setShipping_type(String shipping_type)
    {
        this.shipping_type = shipping_type;
    }
    public String getLogistics_company()
    {
        return logistics_company;
    }
    public void setLogistics_company(String logistics_company)
    {
        this.logistics_company = logistics_company;
    }
    public String getInvoice_no()
    {
        return invoice_no;
    }
    public void setInvoice_no(String invoice_no)
    {
        this.invoice_no = invoice_no;
    }
    public String getSeller_type()
    {
        return seller_type;
    }
    public void setSeller_type(String seller_type)
    {
        this.seller_type = seller_type;
    }
    public String getSeller_nick()
    {
        return seller_nick;
    }
    public void setSeller_nick(String seller_nick)
    {
        this.seller_nick = seller_nick;
    }
    public String getBuyer_nick()
    {
        return buyer_nick;
    }
    public void setBuyer_nick(String buyer_nick)
    {
        this.buyer_nick = buyer_nick;
    }
    public String getOrder_from()
    {
        return order_from;
    }
    public void setOrder_from(String order_from)
    {
        this.order_from = order_from;
    }
    public String getTotal_fee()
    {
        return total_fee;
    }
    public void setTotal_fee(String total_fee)
    {
        this.total_fee = total_fee;
    }
    public String getDiscount_fee()
    {
        return discount_fee;
    }
    public void setDiscount_fee(String discount_fee)
    {
        this.discount_fee = discount_fee;
    }
    public String getAdjust_fee()
    {
        return adjust_fee;
    }
    public void setAdjust_fee(String adjust_fee)
    {
        this.adjust_fee = adjust_fee;
    }
    public boolean isIs_oversold()
    {
        return is_oversold;
    }
    public void setIs_oversold(boolean is_oversold)
    {
        this.is_oversold = is_oversold;
    }
    public boolean isIs_service_order()
    {
        return is_service_order;
    }
    public void setIs_service_order(boolean is_service_order)
    {
        this.is_service_order = is_service_order;
    }
    public long getBind_oid()
    {
        return bind_oid;
    }
    public void setBind_oid(long bind_oid)
    {
        this.bind_oid = bind_oid;
    }
    public String getSnapshot_url()
    {
        return snapshot_url;
    }
    public void setSnapshot_url(String snapshot_url)
    {
        this.snapshot_url = snapshot_url;
    }
    public String getSnapshot()
    {
        return snapshot;
    }
    public void setSnapshot(String snapshot)
    {
        this.snapshot = snapshot;
    }
    public String getTimeout_action_time()
    {
        return timeout_action_time;
    }
    public void setTimeout_action_time(String timeout_action_time)
    {
        this.timeout_action_time = timeout_action_time;
    }
    public boolean isBuyerRateValid()
    {
        return buyerRateValid;
    }
    public void setBuyerRateValid(boolean buyerRateValid)
    {
        this.buyerRateValid = buyerRateValid;
    }
    public boolean isSellerRateValid()
    {
        return sellerRateValid;
    }
    public void setSellerRateValid(boolean sellerRateValid)
    {
        this.sellerRateValid = sellerRateValid;
    }
    public String getBuyerRateCreated()
    {
        return buyerRateCreated;
    }
    public void setBuyerRateCreated(String buyerRateCreated)
    {
        this.buyerRateCreated = buyerRateCreated;
    }
    public String getSellerRateCreated()
    {
        return sellerRateCreated;
    }
    public void setSellerRateCreated(String sellerRateCreated)
    {
        this.sellerRateCreated = sellerRateCreated;
    }
    public String getBuyerRateResult()
    {
        return buyerRateResult;
    }
    public void setBuyerRateResult(String buyerRateResult)
    {
        this.buyerRateResult = buyerRateResult;
    }
    public String getSellerRateResult()
    {
        return sellerRateResult;
    }
    public void setSellerRateResult(String sellerRateResult)
    {
        this.sellerRateResult = sellerRateResult;
    }
    public String getBuyerRateContent()
    {
        return buyerRateContent;
    }
    public void setBuyerRateContent(String buyerRateContent)
    {
        this.buyerRateContent = buyerRateContent;
    }
    public String getSellerRateContent()
    {
        return sellerRateContent;
    }
    public void setSellerRateContent(String sellerRateContent)
    {
        this.sellerRateContent = sellerRateContent;
    }
    public String getAbbrTitle()
    {
        return abbrTitle;
    }
    public void setAbbrTitle(String abbrTitle)
    {
        this.abbrTitle = abbrTitle;
    }
    
}
