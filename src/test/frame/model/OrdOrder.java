/*
 * 版权所有 (C) 2018 知启蒙(ZHIQIM) 保留所有权利。 欢迎到知启蒙网站（https://www.zhiqim.com）购买正版软件，知启蒙还提供许多开源框架和软件。 1、本软件产品所有源代码受《中华人民共和国著作权法》和其他有关法律、法规的保护，其所有知识产权归湖南知启蒙科技有限公司所有；
 * 2、禁止复制和修改。不得复制修改、翻译或改编本软件所有源代码，或者基于本软件产品创作衍生作品； 3、禁止进行逆向工程。不得对本软件的源代码进行逆向工程、反编译或试图以其他方式发现软件的源代码； 4、个别授权：如需进行商业性的销售、复制、分发，包括但不限于软件销售、预装、捆绑等，必须获得知启蒙的书面授权和许可；
 * 5、保留权利：本注释未明示授权的其他一切权利仍归知启蒙所有，任何人使用其他权利时必须获得知启蒙的书面同意。
 */

package frame.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.xiyuan.util.json.JsonUtil;

/**
 * 订单表 对应表《ORD_ORDER》
 */
public class OrdOrder implements Serializable
{

    private static final long serialVersionUID = 1L;

    private long oid;    // 1.订单编号
    private String tids;    // 2.淘宝单号，多个逗号分隔
    private String status;    // 3.订单状态 -1 退回给售中，-2 退回给设计， -100 取消， 0 报价， 1 已提交， 1.1 等待打字 ，1.2 正在打字， 2 等待设计， 3 正在设计， 4 已初稿 ， 5 审稿（已定稿/待审） 6 已审核 ， 7 已下单 ，8 已生产， 9 已发货
    private String shopNick;    // 4.店铺昵称
    private String buyerNick;    // 5.客户昵称
    private long prdTypeId;    // 6.产品类型，冗余字段，便于查询
    private long productId;    // 7.产品id
    private String productText;    // 8.产品描述，自动组装
    private String policyIds;    // 9.后加工策略，多个逗号分隔
    private long amount;    // 10.订单总金额
    private int draftType;    // 11.稿件类型
    private int invoiceType;    // 12.发票类型
    private String invoiceNotes;    // 13.发票抬头
    private String invoiceItin;    // 14.买家纳税识别号
    private long industryId;    // 15.所属行业
    private String thumbnail;    // 16.缩略图，多个逗号分隔
    private boolean isOnlyDesign;    // 17.是否仅设计
    private boolean isUrgent;    // 18.是否加急
    private int printWidth;    // 19.印刷宽度，非常规尺寸使用
    private int printHeight;    // 20.印刷高度，非常规尺寸使用
    private int printKs;    // 21.印刷款数
    private int printMs;    // 22.印刷模数
    private String printOrderNum;    // 23.印刷第几单
    private String printSpecial;    // 24.印刷特殊工艺
    private String creater;    // 25.录单员
    private Timestamp createTime;    // 26.录单时间
    private Timestamp modifyTime;    // 27.修改时间
    private String userText;    // 28.客户文本
    private String userNotice;    // 29.客户注意事项
    private String userMobile;    // 30.用户手机
    private String userQq;    // 31.用户QQ
    private String userWx;    // 32.用户微信
    private String receiverName;    // 33.收货人的姓名
    private String receiverMobile;    // 34.收货人的手机号码
    private String receiverState;    // 35.收货人的所在省份
    private String receiverCity;    // 36.收货人的所在城市
    private String receiverDistrict;    // 37.收货人的所在区县
    private String receiverAddress;    // 38.收货人的详细地址
    private long supplierId;    // 39.供应商
    private String supplierOid;    // 40.供应商订单号
    private String supplierOidStatus;    // 41.供应商订单生产状态
    private Timestamp supplierTime;    // 42.工厂下单时间
    private String canceler;    // 43.取消人
    private Timestamp cancelTime;    // 44.取消时间
    private String cancelNote;    // 45.取消原因
    private boolean isSendSelfAddr;    // 46.是否已发送自主上传链接
    private int csCount;    // 47.售后记录数
    private int orderSrc;    // 48.订单来源，0:原订单，1:售后补单，2：售后赠单，3：微商单
    private long orderSrcOid;    // 49.来源订单号
    private String expressCode;    // 50.物流code
    private int ordPost;    // 51.订单是否包邮 =1表示不包邮， =1表示包邮
    private boolean isSelfPickup;    // 52.是否自提印刷品
    private boolean isSfTopay;    // 53.是否顺丰到付
    private boolean isMergeOrder;    // 54.是否合单
    private boolean isModifyAddrSend;    // 55.是否改地址过地址发货
    private int unpackingNum;    // 56.订单拆包数
    private String sendWaitSureNote;    // 57.发货待定备注
    private String productCostPriceJson;    // 58.产品成本价Json数据
    private String policyCostPriceJsons;    // 59.后加工成本价json数据
    private String cancelLation;    // 60.作废原因
    private int ordShipHours;    // 61.预计耗时(小时)，预计耗时 = 产品预计出货时间（小时）+后加工（加工耗时）
    private Timestamp ordShipTime;    // 62.预计出货时间
    private String userTextReplace;    // 63.格式化后的客户文本
    private long consignmentOid;    // 64.平台订单
    private int sendRemindType;    // 65.发货提醒类型
    private long orgId;    // 66.商户组织编号
    private Timestamp orgReceiveTime;    // 67.商户接单时间
    private int orderFlag;    // 68.订单标记，0:正常订单，1:老用户订单（不计时效），2:老用户订单（计时效）
    private int ordDesignPlatformFlag;    // 69.设计订单表示 0=表示默认订单， 1=表示需要发送到设计平台，2=已发送到设计平台
    private long designId;    // 70.设计平台订单号
    private Timestamp designRetrunTime;    // 71.设计平台退回时间
    private int designRetrunCount;    // 72.设计平台退回次数
    private String servicesMessage;    // 73.客服留言

    @Override
    public String toString()
    {
        return JsonUtil.toString(this);
    }

    public long getOid()
    {
        return oid;
    }

    public void setOid(long oid)
    {
        this.oid = oid;
    }

    public String getTids()
    {
        return tids;
    }

    public void setTids(String tids)
    {
        this.tids = tids;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getShopNick()
    {
        return shopNick;
    }

    public void setShopNick(String shopNick)
    {
        this.shopNick = shopNick;
    }

    public String getBuyerNick()
    {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick)
    {
        this.buyerNick = buyerNick;
    }

    public long getPrdTypeId()
    {
        return prdTypeId;
    }

    public void setPrdTypeId(long prdTypeId)
    {
        this.prdTypeId = prdTypeId;
    }

    public long getProductId()
    {
        return productId;
    }

    public void setProductId(long productId)
    {
        this.productId = productId;
    }

    public String getProductText()
    {
        return productText;
    }

    public void setProductText(String productText)
    {
        this.productText = productText;
    }

    public String getPolicyIds()
    {
        return policyIds;
    }

    public void setPolicyIds(String policyIds)
    {
        this.policyIds = policyIds;
    }

    public long getAmount()
    {
        return amount;
    }

    public void setAmount(long amount)
    {
        this.amount = amount;
    }

    public int getDraftType()
    {
        return draftType;
    }

    public void setDraftType(int draftType)
    {
        this.draftType = draftType;
    }

    public int getInvoiceType()
    {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType)
    {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceNotes()
    {
        return invoiceNotes;
    }

    public void setInvoiceNotes(String invoiceNotes)
    {
        this.invoiceNotes = invoiceNotes;
    }

    public String getInvoiceItin()
    {
        return invoiceItin;
    }

    public void setInvoiceItin(String invoiceItin)
    {
        this.invoiceItin = invoiceItin;
    }

    public long getIndustryId()
    {
        return industryId;
    }

    public void setIndustryId(long industryId)
    {
        this.industryId = industryId;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public boolean isOnlyDesign()
    {
        return isOnlyDesign;
    }

    public void setOnlyDesign(boolean isOnlyDesign)
    {
        this.isOnlyDesign = isOnlyDesign;
    }

    public boolean isUrgent()
    {
        return isUrgent;
    }

    public void setUrgent(boolean isUrgent)
    {
        this.isUrgent = isUrgent;
    }

    public int getPrintWidth()
    {
        return printWidth;
    }

    public void setPrintWidth(int printWidth)
    {
        this.printWidth = printWidth;
    }

    public int getPrintHeight()
    {
        return printHeight;
    }

    public void setPrintHeight(int printHeight)
    {
        this.printHeight = printHeight;
    }

    public int getPrintKs()
    {
        return printKs;
    }

    public void setPrintKs(int printKs)
    {
        this.printKs = printKs;
    }

    public int getPrintMs()
    {
        return printMs;
    }

    public void setPrintMs(int printMs)
    {
        this.printMs = printMs;
    }

    public String getPrintOrderNum()
    {
        return printOrderNum;
    }

    public void setPrintOrderNum(String printOrderNum)
    {
        this.printOrderNum = printOrderNum;
    }

    public String getPrintSpecial()
    {
        return printSpecial;
    }

    public void setPrintSpecial(String printSpecial)
    {
        this.printSpecial = printSpecial;
    }

    public String getCreater()
    {
        return creater;
    }

    public void setCreater(String creater)
    {
        this.creater = creater;
    }

    public Timestamp getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime)
    {
        this.createTime = createTime;
    }

    public Timestamp getModifyTime()
    {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime)
    {
        this.modifyTime = modifyTime;
    }

    public String getUserText()
    {
        return userText;
    }

    public void setUserText(String userText)
    {
        this.userText = userText;
    }

    public String getUserNotice()
    {
        return userNotice;
    }

    public void setUserNotice(String userNotice)
    {
        this.userNotice = userNotice;
    }

    public String getUserMobile()
    {
        return userMobile;
    }

    public void setUserMobile(String userMobile)
    {
        this.userMobile = userMobile;
    }

    public String getUserQq()
    {
        return userQq;
    }

    public void setUserQq(String userQq)
    {
        this.userQq = userQq;
    }

    public String getUserWx()
    {
        return userWx;
    }

    public void setUserWx(String userWx)
    {
        this.userWx = userWx;
    }

    public String getReceiverName()
    {
        return receiverName;
    }

    public void setReceiverName(String receiverName)
    {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile()
    {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile)
    {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverState()
    {
        return receiverState;
    }

    public void setReceiverState(String receiverState)
    {
        this.receiverState = receiverState;
    }

    public String getReceiverCity()
    {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity)
    {
        this.receiverCity = receiverCity;
    }

    public String getReceiverDistrict()
    {
        return receiverDistrict;
    }

    public void setReceiverDistrict(String receiverDistrict)
    {
        this.receiverDistrict = receiverDistrict;
    }

    public String getReceiverAddress()
    {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress)
    {
        this.receiverAddress = receiverAddress;
    }

    public long getSupplierId()
    {
        return supplierId;
    }

    public void setSupplierId(long supplierId)
    {
        this.supplierId = supplierId;
    }

    public String getSupplierOid()
    {
        return supplierOid;
    }

    public void setSupplierOid(String supplierOid)
    {
        this.supplierOid = supplierOid;
    }

    public String getSupplierOidStatus()
    {
        return supplierOidStatus;
    }

    public void setSupplierOidStatus(String supplierOidStatus)
    {
        this.supplierOidStatus = supplierOidStatus;
    }

    public Timestamp getSupplierTime()
    {
        return supplierTime;
    }

    public void setSupplierTime(Timestamp supplierTime)
    {
        this.supplierTime = supplierTime;
    }

    public String getCanceler()
    {
        return canceler;
    }

    public void setCanceler(String canceler)
    {
        this.canceler = canceler;
    }

    public Timestamp getCancelTime()
    {
        return cancelTime;
    }

    public void setCancelTime(Timestamp cancelTime)
    {
        this.cancelTime = cancelTime;
    }

    public String getCancelNote()
    {
        return cancelNote;
    }

    public void setCancelNote(String cancelNote)
    {
        this.cancelNote = cancelNote;
    }

    public boolean isSendSelfAddr()
    {
        return isSendSelfAddr;
    }

    public void setSendSelfAddr(boolean isSendSelfAddr)
    {
        this.isSendSelfAddr = isSendSelfAddr;
    }

    public int getCsCount()
    {
        return csCount;
    }

    public void setCsCount(int csCount)
    {
        this.csCount = csCount;
    }

    public int getOrderSrc()
    {
        return orderSrc;
    }

    public void setOrderSrc(int orderSrc)
    {
        this.orderSrc = orderSrc;
    }

    public long getOrderSrcOid()
    {
        return orderSrcOid;
    }

    public void setOrderSrcOid(long orderSrcOid)
    {
        this.orderSrcOid = orderSrcOid;
    }

    public String getExpressCode()
    {
        return expressCode;
    }

    public void setExpressCode(String expressCode)
    {
        this.expressCode = expressCode;
    }

    public int getOrdPost()
    {
        return ordPost;
    }

    public void setOrdPost(int ordPost)
    {
        this.ordPost = ordPost;
    }

    public boolean isSelfPickup()
    {
        return isSelfPickup;
    }

    public void setSelfPickup(boolean isSelfPickup)
    {
        this.isSelfPickup = isSelfPickup;
    }

    public boolean isSfTopay()
    {
        return isSfTopay;
    }

    public void setSfTopay(boolean isSfTopay)
    {
        this.isSfTopay = isSfTopay;
    }

    public boolean isMergeOrder()
    {
        return isMergeOrder;
    }

    public void setMergeOrder(boolean isMergeOrder)
    {
        this.isMergeOrder = isMergeOrder;
    }

    public boolean isModifyAddrSend()
    {
        return isModifyAddrSend;
    }

    public void setModifyAddrSend(boolean isModifyAddrSend)
    {
        this.isModifyAddrSend = isModifyAddrSend;
    }

    public int getUnpackingNum()
    {
        return unpackingNum;
    }

    public void setUnpackingNum(int unpackingNum)
    {
        this.unpackingNum = unpackingNum;
    }

    public String getSendWaitSureNote()
    {
        return sendWaitSureNote;
    }

    public void setSendWaitSureNote(String sendWaitSureNote)
    {
        this.sendWaitSureNote = sendWaitSureNote;
    }

    public String getProductCostPriceJson()
    {
        return productCostPriceJson;
    }

    public void setProductCostPriceJson(String productCostPriceJson)
    {
        this.productCostPriceJson = productCostPriceJson;
    }

    public String getPolicyCostPriceJsons()
    {
        return policyCostPriceJsons;
    }

    public void setPolicyCostPriceJsons(String policyCostPriceJsons)
    {
        this.policyCostPriceJsons = policyCostPriceJsons;
    }

    public String getCancelLation()
    {
        return cancelLation;
    }

    public void setCancelLation(String cancelLation)
    {
        this.cancelLation = cancelLation;
    }

    public int getOrdShipHours()
    {
        return ordShipHours;
    }

    public void setOrdShipHours(int ordShipHours)
    {
        this.ordShipHours = ordShipHours;
    }

    public Timestamp getOrdShipTime()
    {
        return ordShipTime;
    }

    public void setOrdShipTime(Timestamp ordShipTime)
    {
        this.ordShipTime = ordShipTime;
    }

    public String getUserTextReplace()
    {
        return userTextReplace;
    }

    public void setUserTextReplace(String userTextReplace)
    {
        this.userTextReplace = userTextReplace;
    }

    public long getConsignmentOid()
    {
        return consignmentOid;
    }

    public void setConsignmentOid(long consignmentOid)
    {
        this.consignmentOid = consignmentOid;
    }

    public int getSendRemindType()
    {
        return sendRemindType;
    }

    public void setSendRemindType(int sendRemindType)
    {
        this.sendRemindType = sendRemindType;
    }

    public long getOrgId()
    {
        return orgId;
    }

    public void setOrgId(long orgId)
    {
        this.orgId = orgId;
    }

    public Timestamp getOrgReceiveTime()
    {
        return orgReceiveTime;
    }

    public void setOrgReceiveTime(Timestamp orgReceiveTime)
    {
        this.orgReceiveTime = orgReceiveTime;
    }

    public int getOrderFlag()
    {
        return orderFlag;
    }

    public void setOrderFlag(int orderFlag)
    {
        this.orderFlag = orderFlag;
    }

    public int getOrdDesignPlatformFlag()
    {
        return ordDesignPlatformFlag;
    }

    public void setOrdDesignPlatformFlag(int ordDesignPlatformFlag)
    {
        this.ordDesignPlatformFlag = ordDesignPlatformFlag;
    }

    public long getDesignId()
    {
        return designId;
    }

    public void setDesignId(long designId)
    {
        this.designId = designId;
    }

    public Timestamp getDesignRetrunTime()
    {
        return designRetrunTime;
    }

    public void setDesignRetrunTime(Timestamp designRetrunTime)
    {
        this.designRetrunTime = designRetrunTime;
    }

    public int getDesignRetrunCount()
    {
        return designRetrunCount;
    }

    public void setDesignRetrunCount(int designRetrunCount)
    {
        this.designRetrunCount = designRetrunCount;
    }

    public String getServicesMessage()
    {
        return servicesMessage;
    }

    public void setServicesMessage(String servicesMessage)
    {
        this.servicesMessage = servicesMessage;
    }
}
