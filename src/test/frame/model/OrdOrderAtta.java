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
 * 订单附件表 对应表《ORD_ORDER_ATTA》
 */
public class OrdOrderAtta implements Serializable
{

    private static final long serialVersionUID = 1L;

    private long attaId;    // 1.订单附件编号
    private long oid;    // 2.订单编号
    private String attaModul;    // 3.附件标识
    private String fileName;    // 4.文件名
    private String savePath;    // 5.存储路径
    private String networkUrl;    // 6.网络(云)路径
    private int uploadStatus;    // 7.上传状态（0代表未上传，1代表已上传，2代表不需上传）
    private int uploadNeed;    // 8.是否需要上传 （ 0：否 1：是）
    private int ossType;    // 9.存储类型，=0表示原来附件信息，=1表示标准OSS=2表示归档OSS
    private String fileSize;    // 10.文件大小
    private String fileType;    // 11.文件扩展名，如：pdf等
    private String fileid;    // 12.文件Hash值
    private int uploadType;    // 13.文件来源，0：ERP系统上传，1：自助上传
    private String uploader;    // 14.上传人
    private Timestamp uploadTime;    // 15.上传时间
    private int fileStatus;    // 16.文件状态， =null或者=0 表示本地文件未上传到OSS=1表示已上传但本地文件存在， =2已上传但本地不存在
    private boolean isSendToDesigner;    // 17.是否已发送资料到设计平台
    private int attaSrc;    // 18.文件数据 来源，0=erp,1=设计平台

    public String toString()
    {
        return JsonUtil.toString(this);
    }

    public long getAttaId()
    {
        return attaId;
    }

    public void setAttaId(long attaId)
    {
        this.attaId = attaId;
    }

    public long getOid()
    {
        return oid;
    }

    public void setOid(long oid)
    {
        this.oid = oid;
    }

    public String getAttaModul()
    {
        return attaModul;
    }

    public void setAttaModul(String attaModul)
    {
        this.attaModul = attaModul;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getSavePath()
    {
        return savePath;
    }

    public void setSavePath(String savePath)
    {
        this.savePath = savePath;
    }

    public String getNetworkUrl()
    {
        return networkUrl;
    }

    public void setNetworkUrl(String networkUrl)
    {
        this.networkUrl = networkUrl;
    }

    public int getUploadStatus()
    {
        return uploadStatus;
    }

    public void setUploadStatus(int uploadStatus)
    {
        this.uploadStatus = uploadStatus;
    }

    public int getUploadNeed()
    {
        return uploadNeed;
    }

    public void setUploadNeed(int uploadNeed)
    {
        this.uploadNeed = uploadNeed;
    }

    public int getOssType()
    {
        return ossType;
    }

    public void setOssType(int ossType)
    {
        this.ossType = ossType;
    }

    public String getFileSize()
    {
        return fileSize;
    }

    public void setFileSize(String fileSize)
    {
        this.fileSize = fileSize;
    }

    public String getFileType()
    {
        return fileType;
    }

    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    public String getFileid()
    {
        return fileid;
    }

    public void setFileid(String fileid)
    {
        this.fileid = fileid;
    }

    public int getUploadType()
    {
        return uploadType;
    }

    public void setUploadType(int uploadType)
    {
        this.uploadType = uploadType;
    }

    public String getUploader()
    {
        return uploader;
    }

    public void setUploader(String uploader)
    {
        this.uploader = uploader;
    }

    public Timestamp getUploadTime()
    {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime)
    {
        this.uploadTime = uploadTime;
    }

    public int getFileStatus()
    {
        return fileStatus;
    }

    public void setFileStatus(int fileStatus)
    {
        this.fileStatus = fileStatus;
    }

    public boolean isSendToDesigner()
    {
        return isSendToDesigner;
    }

    public void setSendToDesigner(boolean isSendToDesigner)
    {
        this.isSendToDesigner = isSendToDesigner;
    }

    public int getAttaSrc()
    {
        return attaSrc;
    }

    public void setAttaSrc(int attaSrc)
    {
        this.attaSrc = attaSrc;
    }
}
