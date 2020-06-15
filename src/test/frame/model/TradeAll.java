/*
 * 版权所有 (C) 2013 长沙市红耀通信息技术有限公司。保留所有权利。
 * 
 * http://www.zhiqim.com/fadfox/ 欢迎加盟[知凡狐]兴趣小组。
 * 
 * 创建说明： 2013-6-13 由 zhichenggang创建
 */
package frame.model;

import java.util.ArrayList;
import java.util.List;


/**
 * 交易信息
 * http://api.taobao.com/apidoc/dataStruct.htm?path=apiId=46-invokePath:trades
 */
public class TradeAll
{
    private long tid;
    
    
    private long num_iid;
    private String created;
    
    
    private String end_time;
    
    private String title;
    
    
    private String pic_url;
    private int num;
    private String price;
    private String payment;
    private String status;
    
    
    private String buyer_rate;
    
    
    private String seller_rate;
    
    private String modified;
    
    
    private String buyer_nick;
    
    
    private String receiver_name;// String 是 东方不败 收货人的姓名
    
    
    private String receiver_state;// String 是 浙江省 收货人的所在省份
    
    
    private String receiver_city;//
    
    
    private String receiver_district;//
    
    
    private String receiver_address;// String 是 淘宝城911号 收货人的详细地址
    
    
    private String receiver_zip;// String 是 223700 收货人的邮编
    
    
    private String receiver_mobile;// String 是 13512501826 收货人的手机号码
    
    
    private String receiver_phone;// String 是 13819175372 收货人的电话号码
    
    
    private String post_fee; // 邮费
    
    
    private String alipay_no; // 支付宝交易号
    
    
    private String timeout_action_time;// Date 是 2000-01-01 00:00:00 超时到期时间
    
    
    private String has_buyer_message;// 是否有买家留言
    
    
    private String buyer_message;// 买家留言
    
    
    private int buyer_flag;// Number 否 1 买家备注旗帜
    
    
    private String seller_memo;// String 是 好的 卖家备注（与淘宝网上订单的卖家备注对应，只有卖家才能查看该字段）
    
    
    private int seller_flag;// Number 否 1 卖家备注旗帜
    
    
    private String credit_card_fee;// String 使用信用卡支付金额数
    
    
    private String trade_from;// String 是 WAP,JHS 交易内部来源。 WAP(手机);HITAO(嗨淘);TOP(TOP平台);TAOBAO(普通淘宝);JHS(聚划算) 一笔订单可能同时有以上多个标记，则以逗号分隔
    
    
    private String buyer_alipay_no; // 买家支付宝账号
    
    
    private String pay_time; // 付款时间
    
    
    private String consign_time; // 发货时间
    
    private Order[] orders = new Order[0];
//    private List<Order> orders = new ArrayList<>();

    public long getTid()
    {
        return tid;
    }

    public void setTid(long tid)
    {
        this.tid = tid;
    }

    public long getNum_iid()
    {
        return num_iid;
    }

    public void setNum_iid(long num_iid)
    {
        this.num_iid = num_iid;
    }

    public String getCreated()
    {
        return created;
    }

    public void setCreated(String created)
    {
        this.created = created;
    }

    public String getEnd_time()
    {
        return end_time;
    }

    public void setEnd_time(String end_time)
    {
        this.end_time = end_time;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPic_url()
    {
        return pic_url;
    }

    public void setPic_url(String pic_url)
    {
        this.pic_url = pic_url;
    }

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getPayment()
    {
        return payment;
    }

    public void setPayment(String payment)
    {
        this.payment = payment;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getBuyer_rate()
    {
        return buyer_rate;
    }

    public void setBuyer_rate(String buyer_rate)
    {
        this.buyer_rate = buyer_rate;
    }

    public String getSeller_rate()
    {
        return seller_rate;
    }

    public void setSeller_rate(String seller_rate)
    {
        this.seller_rate = seller_rate;
    }

    public String getModified()
    {
        return modified;
    }

    public void setModified(String modified)
    {
        this.modified = modified;
    }

    public String getBuyer_nick()
    {
        return buyer_nick;
    }

    public void setBuyer_nick(String buyer_nick)
    {
        this.buyer_nick = buyer_nick;
    }

    public String getReceiver_name()
    {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name)
    {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_state()
    {
        return receiver_state;
    }

    public void setReceiver_state(String receiver_state)
    {
        this.receiver_state = receiver_state;
    }

    public String getReceiver_city()
    {
        return receiver_city;
    }

    public void setReceiver_city(String receiver_city)
    {
        this.receiver_city = receiver_city;
    }

    public String getReceiver_district()
    {
        return receiver_district;
    }

    public void setReceiver_district(String receiver_district)
    {
        this.receiver_district = receiver_district;
    }

    public String getReceiver_address()
    {
        return receiver_address;
    }

    public void setReceiver_address(String receiver_address)
    {
        this.receiver_address = receiver_address;
    }

    public String getReceiver_zip()
    {
        return receiver_zip;
    }

    public void setReceiver_zip(String receiver_zip)
    {
        this.receiver_zip = receiver_zip;
    }

    public String getReceiver_mobile()
    {
        return receiver_mobile;
    }

    public void setReceiver_mobile(String receiver_mobile)
    {
        this.receiver_mobile = receiver_mobile;
    }

    public String getReceiver_phone()
    {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone)
    {
        this.receiver_phone = receiver_phone;
    }

    public String getPost_fee()
    {
        return post_fee;
    }

    public void setPost_fee(String post_fee)
    {
        this.post_fee = post_fee;
    }

    public String getAlipay_no()
    {
        return alipay_no;
    }

    public void setAlipay_no(String alipay_no)
    {
        this.alipay_no = alipay_no;
    }

    public String getTimeout_action_time()
    {
        return timeout_action_time;
    }

    public void setTimeout_action_time(String timeout_action_time)
    {
        this.timeout_action_time = timeout_action_time;
    }

    public String getHas_buyer_message()
    {
        return has_buyer_message;
    }

    public void setHas_buyer_message(String has_buyer_message)
    {
        this.has_buyer_message = has_buyer_message;
    }

    public String getBuyer_message()
    {
        return buyer_message;
    }

    public void setBuyer_message(String buyer_message)
    {
        this.buyer_message = buyer_message;
    }

    public int getBuyer_flag()
    {
        return buyer_flag;
    }

    public void setBuyer_flag(int buyer_flag)
    {
        this.buyer_flag = buyer_flag;
    }

    public String getSeller_memo()
    {
        return seller_memo;
    }

    public void setSeller_memo(String seller_memo)
    {
        this.seller_memo = seller_memo;
    }

    public int getSeller_flag()
    {
        return seller_flag;
    }

    public void setSeller_flag(int seller_flag)
    {
        this.seller_flag = seller_flag;
    }

    public String getCredit_card_fee()
    {
        return credit_card_fee;
    }

    public void setCredit_card_fee(String credit_card_fee)
    {
        this.credit_card_fee = credit_card_fee;
    }

    public String getTrade_from()
    {
        return trade_from;
    }

    public void setTrade_from(String trade_from)
    {
        this.trade_from = trade_from;
    }

    public String getBuyer_alipay_no()
    {
        return buyer_alipay_no;
    }

    public void setBuyer_alipay_no(String buyer_alipay_no)
    {
        this.buyer_alipay_no = buyer_alipay_no;
    }

    public String getPay_time()
    {
        return pay_time;
    }

    public void setPay_time(String pay_time)
    {
        this.pay_time = pay_time;
    }

    public String getConsign_time()
    {
        return consign_time;
    }

    public void setConsign_time(String consign_time)
    {
        this.consign_time = consign_time;
    }

//    public List<Order> getOrders()
//    {
//        return orders;
//    }
//
//    public void setOrders(List<Order> orders)
//    {
//        this.orders = orders;
//    }
    
}
