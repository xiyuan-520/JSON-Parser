import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.zhiqim.kernel.constants.CodeConstants;
import org.zhiqim.kernel.constants.TypeConstants;
import org.zhiqim.kernel.json.Jsons;
import org.zhiqim.kernel.util.Files;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiyuan.util.json.JsonUtil;

import frame.model.OrdOrder;

public class JsonMain implements TypeConstants, CodeConstants
{
    
    public static void main(String[] args) throws Exception
    {
        long l1 = System.currentTimeMillis();
        long l2 = System.currentTimeMillis();
        String jsonString = "{\"oid\":1802241101547130,\"tids\":\"119037997603325752\",\"status\":\"7\",\"shopNick\":\"th办公旗舰店\",\"buyerNick\":\"梦醒天神人\",\"prdTypeId\":1,\"productId\":1159,\"productText\":\"名片 | 铜版纸覆膜 | 90x54mm | 双面 | 2百张 | 1款1模\",\"policyIds\":\"\",\"amount\":1400,\"draftType\":0,\"invoiceType\":0,\"invoiceNotes\":\"\",\"invoiceItin\":\"\",\"industryId\":0,\"isOnlyDesign\":false,\"isUrgent\":false,\"printWidth\":0,\"printHeight\":0,\"printKs\":1,\"printMs\":1,\"printSpecial\":\"\",\"creater\":\"蛋蛋\",\"createTime\":\"2018-02-24 11:01:54\",\"modifyTime\":\"2018-02-24 14:07:50\",\"userText\":\"\",\"userMobile\":\"\",\"userQq\":\"\",\"receiverName\":\"黄浩\",\"receiverMobile\":\"13157172203\",\"receiverState\":\"浙江省\",\"receiverCity\":\"杭州市\",\"receiverDistrict\":\"滨江区\",\"receiverAddress\":\"长河街道春晓路529号 江南星座1栋一单元502\",\"supplierId\":6805,\"supplierOid\":\"10476998\",\"supplierOidStatus\":\"b\",\"supplierTime\":\"2018-02-24 14:04:03\",\"isSendSelfAddr\":false,\"csCount\":0,\"orderSrc\":0,\"orderSrcOid\":0,\"expressCode\":\"ZTO\",\"isSelfPickup\":false,\"isSfTopay\":false,\"unpackingNum\":0,\"productCostPriceJson\":\"{\\\"costPriceDate\\\":\\\"2018-02-01\\\",\\\"productId\\\":1159,\\\"prdPrice\\\":1400,\\\"prdCostPrice\\\":600,\\\"costPriceStatus\\\":0,\\\"costPriceModifyTime\\\":\\\"2018-02-01 14:14:36\\\"}\",\"ordShipHours\":24,\"ordShipTime\":\"2018-02-25 11:01:54\"}";
        l1 = System.currentTimeMillis();
        String json = new String(Files.read(new File("./json/100000.txt"), 500 * MiB));
        l2 = System.currentTimeMillis();
        System.out.println("文件加载完成，共耗时：" + (l2 - l1));
        
        // testFastJson(json);
        testMy(json, jsonString);
    }
    
    public static void testMy(String json, String jsonString)
    {
        long l1 = 0, l2 = 0;
        List<OrdOrder> orders = new ArrayList<OrdOrder>();
        System.out.println("===========================================================================");
        long a = 0;
        l1 = System.currentTimeMillis();
        orders = JsonUtil.toList(json, OrdOrder.class);
        // Token token = MyJsons.getTokens(json);
        l2 = System.currentTimeMillis();
        System.out.println("自己代码 共生成 " + (orders != null ? orders.size() : a) + "条数据，共耗时：" + (l2 - l1) + " 毫秒");
        // System.out.println(orders.get(0));
        // System.out.println(JsonUtil.getString(jsonString, "productCostPriceJson"));
        jsonString = "{\"oid\":1802241101547130,\"tids\":\"119037997603325752\",\"status\":\"7\",\"shopNick\":\"th办公旗舰店\",\"buyerNick\":\"梦醒天神人\",\"prdTypeId\":1,\"productId\":1159,\"productText\":\"名片 | 铜版纸覆膜 | 90x54mm | 双面 | 2百张 | 1款1模\",\"policyIds\":\"\",\"amount\":1400,\"draftType\":0,\"invoiceType\":0,\"invoiceNotes\":\"\",\"invoiceItin\":\"\",\"industryId\":0,\"isOnlyDesign\":false,\"isUrgent\":false,\"printWidth\":0,\"printHeight\":0,\"printKs\":1,\"printMs\":1,\"printSpecial\":\"\",\"creater\":\"蛋蛋\",\"createTime\":\"2018-02-24 11:01:54\",\"modifyTime\":\"2018-02-24 14:07:50\",\"userText\":\"\",\"userMobile\":\"\",\"userQq\":\"\",\"receiverName\":\"黄浩\",\"receiverMobile\":\"13157172203\",\"receiverState\":\"浙江省\",\"receiverCity\":\"杭州市\",\"receiverDistrict\":\"滨江区\",\"receiverAddress\":\"长河街道春晓路529号 江南星座1栋一单元502\",\"supplierId\":6805,\"supplierOid\":\"10476998\",\"supplierOidStatus\":\"b\",\"supplierTime\":\"2018-02-24 14:04:03\",\"isSendSelfAddr\":false,\"csCount\":0,\"orderSrc\":0,\"orderSrcOid\":0,\"expressCode\":\"ZTO\",\"isSelfPickup\":false,\"isSfTopay\":false,\"unpackingNum\":0,\"productCostPriceJson\":\"{\\\"costPriceDate\\\":\\\"2018-02-01\\\",\\\"productId\\\":1159,\\\"prdPrice\\\":1400,\\\"prdCostPrice\\\":600,\\\"costPriceStatus\\\":0,\\\"costPriceModifyTime\\\":\\\"2018-02-01 14:14:36\\\"}\",\"ordShipHours\":24,\"ordShipTime\":\"2018-02-25 11:01:54\"}";
        // jsonString = "{ssss:tids::,}";
        // System.out.println(jsonString);
        // System.out.println(JsonUtil.getString(jsonString, "tids"));
        System.out.println(JsonUtil.toStringAddOrUpdate(jsonString, "tids", 3454849));
        System.out.println(JsonUtil.remove(jsonString, "tids"));
        // System.out.println(JsonUtil.remove(jsonString, "22"));
        // jsonString = "{\"oid1\":{vvv:[1]}, ss:, dd:[]}";
        // System.out.println(JsonUtil.insertOrUpdate(jsonString, "fffdddd", new Date()));
        // System.out.println(JsonUtil.toString("ddddd", new Date()));
        // System.out.println(MyJsons.getTokens(jsonString).toString(jsonString));
        // System.out.println(jsonString);
        // System.out.println(MyJsons.getString(jsonString, "arr"));
    }
    
    public static void testFastJson(String json)
    {
        long l1 = 0, l2 = 0;
        List<OrdOrder> orders = new ArrayList<OrdOrder>();
        System.out.println("===========================================================================");
        l1 = System.currentTimeMillis();
        orders = JSON.parseArray(json, OrdOrder.class);
        l2 = System.currentTimeMillis();
        System.out.println("马云代码 共生成 " + (orders == null ? 0 : orders.size()) + "条数据，共耗时：" + (l2 - l1) + " 毫秒");
    }
    
    public static void testGson(String json)
    {
        long l1 = 0, l2 = 0;
        List<OrdOrder> orders = new ArrayList<OrdOrder>();
        System.out.println("===========================================================================");
        l1 = System.currentTimeMillis();
        Gson gson = new Gson();
        orders = gson.fromJson(json, new TypeToken<List<OrdOrder>>()
        {
        }.getType());
        l2 = System.currentTimeMillis();
        System.out.println("谷歌代码 共生成 " + (orders.size()) + "条数据，共耗时：" + (l2 - l1) + " 毫秒");
    }
    
    public static void testZhiqim(String json)
    {
        long l1 = 0, l2 = 0;
        List<OrdOrder> orders = new ArrayList<OrdOrder>();
        System.out.println("===========================================================================");
        l1 = System.currentTimeMillis();
        orders = Jsons.toList(json, OrdOrder.class);
        l2 = System.currentTimeMillis();
        System.out.println("公司代码 共生成 " + (orders.size()) + "条数据，共耗时：" + (l2 - l1) + " 毫秒");
    }
}
