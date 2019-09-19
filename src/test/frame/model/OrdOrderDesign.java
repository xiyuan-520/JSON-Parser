/*
 * 版权所有 (C) 2018 知启蒙(ZHIQIM) 保留所有权利。 欢迎到知启蒙网站（https://www.zhiqim.com）购买正版软件，知启蒙还提供许多开源框架和软件。 1、本软件产品所有源代码受《中华人民共和国著作权法》和其他有关法律、法规的保护，其所有知识产权归湖南知启蒙科技有限公司所有；
 * 2、禁止复制和修改。不得复制修改、翻译或改编本软件所有源代码，或者基于本软件产品创作衍生作品； 3、禁止进行逆向工程。不得对本软件的源代码进行逆向工程、反编译或试图以其他方式发现软件的源代码； 4、个别授权：如需进行商业性的销售、复制、分发，包括但不限于软件销售、预装、捆绑等，必须获得知启蒙的书面授权和许可；
 * 5、保留权利：本注释未明示授权的其他一切权利仍归知启蒙所有，任何人使用其他权利时必须获得知启蒙的书面同意。
 */

package frame.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.xiyuan.util.json.Jsons;

/**
 * 订单设计表 对应表《ORD_ORDER_DESIGN》
 */
public class OrdOrderDesign implements Serializable
{

    private static final long serialVersionUID = 1L;

    private long oid;    // 1.订单编号
    private int userDataStatus;    // 2.客户自助上传资料状态，0：已提交未确认，1：已确认,2：已接单设计中
    private String userContactDesc;    // 3.联系用户描述说明
    private String writer;    // 4.打字人
    private Timestamp writeTime;    // 5.领取打字单时间
    private Timestamp writeFinishTime;    // 6.打字提交时间
    private int writeNum;    // 7.打字字数
    private int receiveType;    // 8.订单领取类型,0手动领单1 自动派单,2 手工指定
    private String selfDraftText;    // 9.自来稿进度备注
    private String selfDraftDesigner;    // 10.自来稿设计人
    private Timestamp selfDraftReceiveTime;    // 11.自来稿领单时间
    private Timestamp selfDraftEndTime;    // 12.自来稿设计提交时间
    private String selfDraftBacker;    // 13.自来稿退回人
    private String selfDraftBackReason;    // 14.自来稿退回原因
    private Timestamp selfDraftBackTime;    // 15.自来稿退回时间
    private int dispatchType;    // 16.派单类型,0：内部单，1：外部单
    private String designer;    // 17.设计师
    private Timestamp designReceiveTime;    // 18.设计师领单时间
    private Timestamp designBeginTime;    // 19.设计师开始设计时间
    private Timestamp designDraftTime;    // 20.设计师初稿时间
    private Timestamp designEndTime;    // 21.设计师定稿时间
    private String designPauseReason;    // 22.设计师暂停原因（新增字段）
    private Timestamp designPauseTime;    // 23.设计师暂停时间
    private String designExclude;    // 24.设计师排除，多个逗号分隔
    private String designRequestReason;    // 25.设计师请求主管支援原由
    private String requestDesigner;    // 26.设计师请求主管支援 的设计师
    private Timestamp requestTime;    // 27.设计师请求主管支援 时间
    private String designRejectReason;    // 28.主管退回设计师原因
    private int designKeepDuration;    // 29.设计师保留时长，单位分钟
    private String checker;    // 30.审稿人
    private Timestamp checkTime;    // 31.审稿时间
    private String checkRejectReason;    // 32.审稿人退回原因
    private String checkRejectPictureUrl;    // 33.审核退回图片请求路径
    private String schedulBacker;    // 34.审核后退回人
    private Timestamp schedulBackTime;    // 35.审核后退回时间
    private String schedulBackReason;    // 36.审核后退回原因
    private String requestRefoundAcceptReason;    // 37.请求退款原因

    public String toString()
    {
        return Jsons.toString(this);
    }

    public long getOid()
    {
        return oid;
    }

    public void setOid(long oid)
    {
        this.oid = oid;
    }

    public int getUserDataStatus()
    {
        return userDataStatus;
    }

    public void setUserDataStatus(int userDataStatus)
    {
        this.userDataStatus = userDataStatus;
    }

    public String getUserContactDesc()
    {
        return userContactDesc;
    }

    public void setUserContactDesc(String userContactDesc)
    {
        this.userContactDesc = userContactDesc;
    }

    public String getWriter()
    {
        return writer;
    }

    public void setWriter(String writer)
    {
        this.writer = writer;
    }

    public Timestamp getWriteTime()
    {
        return writeTime;
    }

    public void setWriteTime(Timestamp writeTime)
    {
        this.writeTime = writeTime;
    }

    public Timestamp getWriteFinishTime()
    {
        return writeFinishTime;
    }

    public void setWriteFinishTime(Timestamp writeFinishTime)
    {
        this.writeFinishTime = writeFinishTime;
    }

    public int getWriteNum()
    {
        return writeNum;
    }

    public void setWriteNum(int writeNum)
    {
        this.writeNum = writeNum;
    }

    public int getReceiveType()
    {
        return receiveType;
    }

    public void setReceiveType(int receiveType)
    {
        this.receiveType = receiveType;
    }

    public String getSelfDraftText()
    {
        return selfDraftText;
    }

    public void setSelfDraftText(String selfDraftText)
    {
        this.selfDraftText = selfDraftText;
    }

    public String getSelfDraftDesigner()
    {
        return selfDraftDesigner;
    }

    public void setSelfDraftDesigner(String selfDraftDesigner)
    {
        this.selfDraftDesigner = selfDraftDesigner;
    }

    public Timestamp getSelfDraftReceiveTime()
    {
        return selfDraftReceiveTime;
    }

    public void setSelfDraftReceiveTime(Timestamp selfDraftReceiveTime)
    {
        this.selfDraftReceiveTime = selfDraftReceiveTime;
    }

    public Timestamp getSelfDraftEndTime()
    {
        return selfDraftEndTime;
    }

    public void setSelfDraftEndTime(Timestamp selfDraftEndTime)
    {
        this.selfDraftEndTime = selfDraftEndTime;
    }

    public String getSelfDraftBacker()
    {
        return selfDraftBacker;
    }

    public void setSelfDraftBacker(String selfDraftBacker)
    {
        this.selfDraftBacker = selfDraftBacker;
    }

    public String getSelfDraftBackReason()
    {
        return selfDraftBackReason;
    }

    public void setSelfDraftBackReason(String selfDraftBackReason)
    {
        this.selfDraftBackReason = selfDraftBackReason;
    }

    public Timestamp getSelfDraftBackTime()
    {
        return selfDraftBackTime;
    }

    public void setSelfDraftBackTime(Timestamp selfDraftBackTime)
    {
        this.selfDraftBackTime = selfDraftBackTime;
    }

    public int getDispatchType()
    {
        return dispatchType;
    }

    public void setDispatchType(int dispatchType)
    {
        this.dispatchType = dispatchType;
    }

    public String getDesigner()
    {
        return designer;
    }

    public void setDesigner(String designer)
    {
        this.designer = designer;
    }

    public Timestamp getDesignReceiveTime()
    {
        return designReceiveTime;
    }

    public void setDesignReceiveTime(Timestamp designReceiveTime)
    {
        this.designReceiveTime = designReceiveTime;
    }

    public Timestamp getDesignBeginTime()
    {
        return designBeginTime;
    }

    public void setDesignBeginTime(Timestamp designBeginTime)
    {
        this.designBeginTime = designBeginTime;
    }

    public Timestamp getDesignDraftTime()
    {
        return designDraftTime;
    }

    public void setDesignDraftTime(Timestamp designDraftTime)
    {
        this.designDraftTime = designDraftTime;
    }

    public Timestamp getDesignEndTime()
    {
        return designEndTime;
    }

    public void setDesignEndTime(Timestamp designEndTime)
    {
        this.designEndTime = designEndTime;
    }

    public String getDesignPauseReason()
    {
        return designPauseReason;
    }

    public void setDesignPauseReason(String designPauseReason)
    {
        this.designPauseReason = designPauseReason;
    }

    public Timestamp getDesignPauseTime()
    {
        return designPauseTime;
    }

    public void setDesignPauseTime(Timestamp designPauseTime)
    {
        this.designPauseTime = designPauseTime;
    }

    public String getDesignExclude()
    {
        return designExclude;
    }

    public void setDesignExclude(String designExclude)
    {
        this.designExclude = designExclude;
    }

    public String getDesignRequestReason()
    {
        return designRequestReason;
    }

    public void setDesignRequestReason(String designRequestReason)
    {
        this.designRequestReason = designRequestReason;
    }

    public String getRequestDesigner()
    {
        return requestDesigner;
    }

    public void setRequestDesigner(String requestDesigner)
    {
        this.requestDesigner = requestDesigner;
    }

    public Timestamp getRequestTime()
    {
        return requestTime;
    }

    public void setRequestTime(Timestamp requestTime)
    {
        this.requestTime = requestTime;
    }

    public String getDesignRejectReason()
    {
        return designRejectReason;
    }

    public void setDesignRejectReason(String designRejectReason)
    {
        this.designRejectReason = designRejectReason;
    }

    public int getDesignKeepDuration()
    {
        return designKeepDuration;
    }

    public void setDesignKeepDuration(int designKeepDuration)
    {
        this.designKeepDuration = designKeepDuration;
    }

    public String getChecker()
    {
        return checker;
    }

    public void setChecker(String checker)
    {
        this.checker = checker;
    }

    public Timestamp getCheckTime()
    {
        return checkTime;
    }

    public void setCheckTime(Timestamp checkTime)
    {
        this.checkTime = checkTime;
    }

    public String getCheckRejectReason()
    {
        return checkRejectReason;
    }

    public void setCheckRejectReason(String checkRejectReason)
    {
        this.checkRejectReason = checkRejectReason;
    }

    public String getCheckRejectPictureUrl()
    {
        return checkRejectPictureUrl;
    }

    public void setCheckRejectPictureUrl(String checkRejectPictureUrl)
    {
        this.checkRejectPictureUrl = checkRejectPictureUrl;
    }

    public String getSchedulBacker()
    {
        return schedulBacker;
    }

    public void setSchedulBacker(String schedulBacker)
    {
        this.schedulBacker = schedulBacker;
    }

    public Timestamp getSchedulBackTime()
    {
        return schedulBackTime;
    }

    public void setSchedulBackTime(Timestamp schedulBackTime)
    {
        this.schedulBackTime = schedulBackTime;
    }

    public String getSchedulBackReason()
    {
        return schedulBackReason;
    }

    public void setSchedulBackReason(String schedulBackReason)
    {
        this.schedulBackReason = schedulBackReason;
    }

    public String getRequestRefoundAcceptReason()
    {
        return requestRefoundAcceptReason;
    }

    public void setRequestRefoundAcceptReason(String requestRefoundAcceptReason)
    {
        this.requestRefoundAcceptReason = requestRefoundAcceptReason;
    }

}
