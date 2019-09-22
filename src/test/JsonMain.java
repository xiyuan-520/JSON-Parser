import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zhiqim.kernel.constants.CodeConstants;
import org.zhiqim.kernel.constants.TypeConstants;
import org.zhiqim.kernel.util.Files;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiyuan.util.json.Jsons;
import com.xiyuan.util.json.Token;

import frame.model.OrdOrder;
import frame.model.Person;

public class JsonMain implements TypeConstants, CodeConstants
{

    public static void main(String[] args) throws Exception
    {
        int listSize = 10000;
        String jsonPath = "./json/" + listSize + ".json";
        long l1 = System.currentTimeMillis();
        long l2 = l1;
        @SuppressWarnings("unused")
        String jsonString = "{\"oid\":1802241101547130,\"tids\":\"119037997603325752\",\"status\":\"7\",\"shopNick\":\"th办公旗舰店\",\"buyerNick\":\"梦醒天神人\",\"prdTypeId\":1,\"productId\":1159,\"productText\":\"名片 | 铜版纸覆膜 | 90x54mm | 双面 | 2百张 | 1款1模\",\"policyIds\":\"\",\"amount\":1400,\"draftType\":0,\"invoiceType\":0,\"invoiceNotes\":\"\",\"invoiceItin\":\"\",\"industryId\":0,\"isOnlyDesign\":false,\"isUrgent\":false,\"printWidth\":0,\"printHeight\":0,\"printKs\":1,\"printMs\":1,\"printSpecial\":\"\",\"creater\":\"蛋蛋\",\"createTime\":\"2018-02-24 11:01:54\",\"modifyTime\":\"2018-02-24 14:07:50\",\"userText\":\"\",\"userMobile\":\"\",\"userQq\":\"\",\"receiverName\":\"黄浩\",\"receiverMobile\":\"13157172203\",\"receiverState\":\"浙江省\",\"receiverCity\":\"杭州市\",\"receiverDistrict\":\"滨江区\",\"receiverAddress\":\"长河街道春晓路529号 江南星座1栋一单元502\",\"supplierId\":6805,\"supplierOid\":\"10476998\",\"supplierOidStatus\":\"b\",\"supplierTime\":\"2018-02-24 14:04:03\",\"isSendSelfAddr\":false,\"csCount\":0,\"orderSrc\":0,\"orderSrcOid\":0,\"expressCode\":\"ZTO\",\"isSelfPickup\":false,\"isSfTopay\":false,\"unpackingNum\":0,\"productCostPriceJson\":\"{\\\"costPriceDate\\\":\\\"2018-02-01\\\",\\\"productId\\\":1159,\\\"prdPrice\\\":1400,\\\"prdCostPrice\\\":600,\\\"costPriceStatus\\\":0,\\\"costPriceModifyTime\\\":\\\"2018-02-01 14:14:36\\\"}\",\"ordShipHours\":24,\"ordShipTime\":\"2018-02-25 11:01:54\"}";
        l1 = System.currentTimeMillis();
        String json = new String(Files.read(new File(jsonPath), 1800 * MiB));
        l2 = System.currentTimeMillis();
        System.out.println("文件加载完成，共耗时：" + (l2 - l1));

        // testFastJson(json);
        testMy(json, jsonString);
        // testZhiqim(json);
        // testGson(json);//145033459
        // Token root = Token.newToken(Token.BRACE_L, 0);
        // Token next = root.next(Token.STRING, 1);
        //
        // List<Integer> ls = new LinkedList<Integer>();
        // // List<Token> ls = new LinkedList<Token>();
        // int count = 1200000;
        // l1 = System.currentTimeMillis();
        // for (int i = 2; i <= count; i++)
        // {
        // next = next.next(Token.STRING, i);
        // // ls.add(i);
        // }
        // System.out.println(count+" - time:"+(System.currentTimeMillis()-l1));
        // //
        // System.out.println(root.size(null));
        // System.out.println(json.length());
        // System.out.println(ls.size()+2);
        // // System.out.println(Integer.MAX_VALUE);
    }

    public static void testMy(String json, String jsonString)
    {
        Token token = Token.newToken((byte) 0, 0);
        long l1 = 0, l2 = 0;
        List<OrdOrder> orders = new ArrayList<OrdOrder>();
        System.out.println("===========================================================================");
        long a = 0;
        l1 = System.currentTimeMillis();
        // json =
        // "[{\"oid\":1970649346523,\"tids\":\"625421090979097318\",\"status\":\"2\"},{\"oid\":1970649256481,\"tids\":\"625345184971437955\",\"status\":\"2\",\"shopNick\":\"th办公旗舰店\"},{\"oid\":1970649176433,\"tids\":\"625470499260064842\",\"status\":\"1\",\"shopNick\":\"yawiiwen\",\"buyerNick\":\"许诺612\"},{\"oid\":1970649086392,\"tids\":\"625340544680873331\",\"status\":\"2\",\"servicesMessage\":\"\",\"isOldUser\":0},{\"oid\":1970649026370,\"tids\":\"555698372277719588\",\"status\":\"1\",\"shopNick\":\"th办公旗舰店\"\"isOldUser\":0}]";
//        orders = Jsons.toList(json, OrdOrder.class);
        // orders = Jsons.toList2(json, OrdOrder.class);
         token = Jsons.getTokens(json);
        // Token[]ss = token.getElements();
        // token = Jsons.getTokens(json, "oid");
        l2 = System.currentTimeMillis();
        System.out.println("自己代码 共生成 " + (orders != null ? orders.size() : a) + "条数据，共耗时：" + (l2 - l1) + " 毫秒");
        System.out.println(token.size());
        for (int i = 0; i < orders.size(); i++)
        {
            if (i > 0)
                break;

            System.out.println(orders.get(i));
        }

        // json = "";
       
        Person p = new Person();
        p.setAge(18);
        p.setId(1);
        p.setName("a");
        p.setSex("0");
        System.out.println(Jsons.toString(p));
        System.out.println("============================================================");

        json = "[";
        json += "{\"id\":1,\"name\":\"a\",\"age\":18,\"sex\":\"0\"}";
        json += ",";
        json += "{\"id\":2,\"name\":\"b\",\"age\":19,\"sex\":\"1\"}";
        json += "]";

        // List<Person> ls = Jsons.toList(json, Person.class);
        // for (Person person : ls)
        // System.out.println(person);
        //
        // System.out.println("============================================================");
        // System.out.println(Jsons.getTokens(json).size());

        // Map<Object, Object> map = new HashMap<Object, Object>();
        // map.put(p, p);
        // System.out.println(Jsons.toString(map));
        // System.out.println(token.toString(json));
        // System.out.println(Token.count);
        // TokenSize size = JsonUtil.calculateSubTokenSize(json, null);
        // System.out.println(size.size(1153));
        // System.out.println(size.beginCount());
        // System.out.println(jsonString);
        // l1 = System.currentTimeMillis();
        // size.begins(json);
        // System.out.println("自己代码 size.begins 共生成 " + (orders != null ?
        // orders.size() : a) + "条数据，共耗时：" + (l2 - l1) + " 毫秒");
        // System.out.println(orders.get(0));
        // System.out.println(JsonUtil.getString(jsonString,
        // "productCostPriceJson"));
        // jsonString = "{ssss:tids::,}";
        // System.out.println(jsonString);
        // System.out.println(JsonUtil.getString(jsonString, "tids"));
        // System.out.println(JsonUtil.toStringAddOrUpdate(jsonString, "tids",
        // 3454849));
        // System.out.println(JsonUtil.remove(jsonString, "tids"));
        // System.out.println(JsonUtil.remove(jsonString, "22"));
        // jsonString = "{\"oid1\":{vvv:[1]}, ss:, dd:[]}";
        // System.out.println(JsonUtil.insertOrUpdate(jsonString, "fffdddd", new
        // Date()));
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
        orders = gson.fromJson(json, new TypeToken<List<OrdOrder>>() {}.getType());
        l2 = System.currentTimeMillis();
        System.out.println("谷歌代码 共生成 " + (orders.size()) + "条数据，共耗时：" + (l2 - l1) + " 毫秒");
    }

    public static void testZhiqim(String json)
    {
        long l1 = 0, l2 = 0;
        List<OrdOrder> orders = new ArrayList<OrdOrder>();
        System.out.println("===========================================================================");
        l1 = System.currentTimeMillis();
        orders = org.zhiqim.kernel.json.Jsons.toList(json, OrdOrder.class);
        l2 = System.currentTimeMillis();
        System.out.println("公司代码 共生成 " + (orders.size()) + "条数据，共耗时：" + (l2 - l1) + " 毫秒");
    }
}
