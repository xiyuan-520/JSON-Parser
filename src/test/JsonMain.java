

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zhiqim.kernel.constants.CodeConstants;
import org.zhiqim.kernel.constants.TypeConstants;
import org.zhiqim.kernel.util.Files;

import com.alibaba.fastjson.JSON;
import com.xiyuan.util.json.JsonUtil;

import frame.model.OrdOrder;


public class JsonMain implements TypeConstants, CodeConstants
{
    
    public static void main(String[] args) throws Exception
    {
        List<String> strLs = new ArrayList<String>();
        int count = 1977230;
        long l1 = System.currentTimeMillis();
        long l2 = System.currentTimeMillis();
        String jsonString = "{\"oid\":1802241101547130,\"tids\":\"119037997603325752\",\"status\":\"7\",\"shopNick\":\"th办公旗舰店\",\"buyerNick\":\"梦醒天神人\",\"prdTypeId\":1,\"productId\":1159,\"productText\":\"名片 | 铜版纸覆膜 | 90x54mm | 双面 | 2百张 | 1款1模\",\"policyIds\":\"\",\"amount\":1400,\"draftType\":0,\"invoiceType\":0,\"invoiceNotes\":\"\",\"invoiceItin\":\"\",\"industryId\":0,\"isOnlyDesign\":false,\"isUrgent\":false,\"printWidth\":0,\"printHeight\":0,\"printKs\":1,\"printMs\":1,\"printSpecial\":\"\",\"creater\":\"蛋蛋\",\"createTime\":\"2018-02-24 11:01:54\",\"modifyTime\":\"2018-02-24 14:07:50\",\"userText\":\"\",\"userMobile\":\"\",\"userQq\":\"\",\"receiverName\":\"黄浩\",\"receiverMobile\":\"13157172203\",\"receiverState\":\"浙江省\",\"receiverCity\":\"杭州市\",\"receiverDistrict\":\"滨江区\",\"receiverAddress\":\"长河街道春晓路529号 江南星座1栋一单元502\",\"supplierId\":6805,\"supplierOid\":\"10476998\",\"supplierOidStatus\":\"b\",\"supplierTime\":\"2018-02-24 14:04:03\",\"isSendSelfAddr\":false,\"csCount\":0,\"orderSrc\":0,\"orderSrcOid\":0,\"expressCode\":\"ZTO\",\"isSelfPickup\":false,\"isSfTopay\":false,\"unpackingNum\":0,\"productCostPriceJson\":\"{\\\"costPriceDate\\\":\\\"2018-02-01\\\",\\\"productId\\\":1159,\\\"prdPrice\\\":1400,\\\"prdCostPrice\\\":600,\\\"costPriceStatus\\\":0,\\\"costPriceModifyTime\\\":\\\"2018-02-01 14:14:36\\\"}\",\"ordShipHours\":24,\"ordShipTime\":\"2018-02-25 11:01:54\"}";
        // OrdOrder ord = MyJsons.toObject(jsonString, OrdOrder.class);ord.getAmount();
        // System.out.println(ord);
        // // System.out.println(JSON.parseObject(jsonString, OrdOrder.class));
        // boolean append = false;
        // for (int i = 0; i < 10000; i++)
        // {
        // // Files.writeUTF8(new File("./json/json.txt"), ", " + jsonString, append);
        // if (!append)
        // append = true;
        // }
        //
        l1 = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder("[");
        sb.append(new String(Files.read(new File("./json/json.txt"), 500 * MiB)));
//        sb.append(new String(Files.read(new File("./aaa.txt"), (long) 3 * GiB), "UTF-8"));
        // sb.append(jsonString);
        // sb.append(",").append(jsonString);
        // sb.append(",").append(jsonString);
        // sb.append(",").append(jsonString);
        sb.append("]");
        String json = sb.toString();
        l2 = System.currentTimeMillis();
        System.out.println("文件加载完成，共耗时：" + (l2 - l1));
        List<OrdOrder> orders = null;
        //
//        
//         System.out.println("===========================================================================");
//         l1 = System.currentTimeMillis();
//         orders = JSON.parseArray(json, OrdOrder.class);
//         l2 = System.currentTimeMillis();
//         System.out.println("马云代码 共生成 " +(orders == null ? 0: orders.size()) + "条数据，共耗时："+(l2-l1) + " 毫秒");
//         orders = null;
//        
        System.out.println("===========================================================================");
        long a = 0;
        l1 = System.currentTimeMillis();
        orders = JsonUtil.toList(json, OrdOrder.class);
        // Token token = MyJsons.getTokens(json);
        l2 = System.currentTimeMillis();
        System.out.println("自己代码 共生成 " + (orders != null ? orders.size() : a) + "条数据，共耗时：" + (l2 - l1) + " 毫秒");
//        System.out.println(orders.get(0));
        // orders = null;
//        System.out.println(JsonUtil.getString(jsonString, "productCostPriceJson"));
        jsonString = "{\"oid\":1802241101547130,\"tids\":\"119037997603325752\",\"status\":\"7\",\"shopNick\":\"th办公旗舰店\",\"buyerNick\":\"梦醒天神人\",\"prdTypeId\":1,\"productId\":1159,\"productText\":\"名片 | 铜版纸覆膜 | 90x54mm | 双面 | 2百张 | 1款1模\",\"policyIds\":\"\",\"amount\":1400,\"draftType\":0,\"invoiceType\":0,\"invoiceNotes\":\"\",\"invoiceItin\":\"\",\"industryId\":0,\"isOnlyDesign\":false,\"isUrgent\":false,\"printWidth\":0,\"printHeight\":0,\"printKs\":1,\"printMs\":1,\"printSpecial\":\"\",\"creater\":\"蛋蛋\",\"createTime\":\"2018-02-24 11:01:54\",\"modifyTime\":\"2018-02-24 14:07:50\",\"userText\":\"\",\"userMobile\":\"\",\"userQq\":\"\",\"receiverName\":\"黄浩\",\"receiverMobile\":\"13157172203\",\"receiverState\":\"浙江省\",\"receiverCity\":\"杭州市\",\"receiverDistrict\":\"滨江区\",\"receiverAddress\":\"长河街道春晓路529号 江南星座1栋一单元502\",\"supplierId\":6805,\"supplierOid\":\"10476998\",\"supplierOidStatus\":\"b\",\"supplierTime\":\"2018-02-24 14:04:03\",\"isSendSelfAddr\":false,\"csCount\":0,\"orderSrc\":0,\"orderSrcOid\":0,\"expressCode\":\"ZTO\",\"isSelfPickup\":false,\"isSfTopay\":false,\"unpackingNum\":0,\"productCostPriceJson\":\"{\\\"costPriceDate\\\":\\\"2018-02-01\\\",\\\"productId\\\":1159,\\\"prdPrice\\\":1400,\\\"prdCostPrice\\\":600,\\\"costPriceStatus\\\":0,\\\"costPriceModifyTime\\\":\\\"2018-02-01 14:14:36\\\"}\",\"ordShipHours\":24,\"ordShipTime\":\"2018-02-25 11:01:54\"}";
//        jsonString = "{ssss:tids::,}";
//        System.out.println(jsonString);
//        System.out.println(JsonUtil.getString(jsonString, "tids"));
        System.out.println(JsonUtil.toStringAddOrUpdate(jsonString, "tids", 3454849));
        System.out.println(JsonUtil.remove(jsonString, "tids"));
//        System.out.println(JsonUtil.remove(jsonString, "22"));
//        jsonString = "{\"oid1\":{vvv:[1]}, ss:, dd:[]}";
//        System.out.println(JsonUtil.insertOrUpdate(jsonString, "fffdddd", new Date()));
//        System.out.println(JsonUtil.toString("ddddd", new Date()));
        // System.out.println(MyJsons.getTokens(jsonString).toString(jsonString));
        // System.out.println(jsonString);
        // System.out.println(MyJsons.getString(jsonString, "arr"));
        
        
        
        // //
        // // Token token = MyJsons.getTokens("{a:a1,b:b1},{c:c1,d:d1}");
        // // Token[] ts = token.getElements();
        // // System.out.println(ts.length);
        // // for (Token token2 : ts)
        // // {
        // // System.out.println(token2.toString());
        // // }
        // // JSON.parseObject("{a:a1,b:b1},{c:c1,d:d1}");
        //
        //
        // System.out.println("===========================================================================");
        // l1 = System.currentTimeMillis();
        // orders = Jsons.toList(json, OrdOrder.class);
        // l2 = System.currentTimeMillis();
        // System.out.println("公司代码 共生成 " + (orders == null ? strLs.size(): orders.size()) + "条数据，共耗时："+(l2-l1) + " 毫秒");
        // orders = null;
        // //
        // //
        
        //
        // // System.out.println("===========================================================================");
        // // l1 = System.currentTimeMillis();
        // Gson gson = new Gson();
        // gson.fromJson(json, classOfT)
        // // orders = gson.fromJson(json, new TypeToken<List<OrdOrder>>() {}.getType());
        // // l2 = System.currentTimeMillis();
        // // System.out.println("谷歌代码 共生成 " + (orders == null ? strLs.size(): orders.size()) + "条数据，共耗时："+(l2-l1) + " 毫秒");
        // // orders = null;
        //
        // //
        // // int c = 10000*10000;
        // // l1 = System.currentTimeMillis();
        // // for (int i = 0; i <c; i++)
        // // {
        // // Token.newToken((byte) 0, 0, 0);
        // // }
        // // l2 = System.currentTimeMillis();
        // // System.out.println(l2 - l1);
        //
        // // int ss = 20;
        // // l1 = System.nanoTime();
        // // ss = ss + 1;
        // // ss = myAdd(ss, -1);
        // // l2 = System.nanoTime();
        // // System.out.println("ss:" + ss + " " + (l2 - l1));
        
        
        
        
        
    }
    
    public static int myAdd(int a, int b)
    {
        int sum;
        int carry;
        do
        {
            sum = a ^ b;
            carry = (a & b) << 1;// 进位需要左移一位
            a = sum;
            b = carry;
        }
        while (carry != 0);
        return sum;
    }
    
    private static void addList(List<Object> list, int count)
    {
        for (int i = 0; i < count; i++)
        {
            list.add(i);
        }
    }
    
    private static void addMap(Map<Object, Object> map, int count)
    {
        for (int i = 0; i < count; i++)
        {
            map.put(i, i);
        }
    }
}
