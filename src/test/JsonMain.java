import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.zhiqim.kernel.constants.CodeConstants;
import org.zhiqim.kernel.constants.TypeConstants;
import org.zhiqim.kernel.util.Files;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiyuan.util.json.Jsons;

import frame.model.OrdOrder;

public class JsonMain implements TypeConstants, CodeConstants
{
    
    public static void main(String[] args) throws Exception
    {
        int listSize = 100000;
        String jsonPath = "./json/" + listSize + ".json";
        long l1 = System.currentTimeMillis();
        long l2 = l1;
        String jsonString = "{\"oid\":1802241101547130,\"tids\":\"119037997603325752\",\"status\":\"7\",\"shopNick\":\"th办公旗舰店\",\"buyerNick\":\"梦醒天神人\",\"prdTypeId\":1,\"productId\":1159,\"productText\":\"名片 | 铜版纸覆膜 | 90x54mm | 双面 | 2百张 | 1款1模\",\"policyIds\":\"\",\"amount\":1400,\"draftType\":0,\"invoiceType\":0,\"invoiceNotes\":\"\",\"invoiceItin\":\"\",\"industryId\":0,\"isOnlyDesign\":false,\"isUrgent\":false,\"printWidth\":0,\"printHeight\":0,\"printKs\":1,\"printMs\":1,\"printSpecial\":\"\",\"creater\":\"蛋蛋\",\"createTime\":\"2018-02-24 11:01:54\",\"modifyTime\":\"2018-02-24 14:07:50\",\"userText\":\"\",\"userMobile\":\"\",\"userQq\":\"\",\"receiverName\":\"黄浩\",\"receiverMobile\":\"13157172203\",\"receiverState\":\"浙江省\",\"receiverCity\":\"杭州市\",\"receiverDistrict\":\"滨江区\",\"receiverAddress\":\"长河街道春晓路529号 江南星座1栋一单元502\",\"supplierId\":6805,\"supplierOid\":\"10476998\",\"supplierOidStatus\":\"b\",\"supplierTime\":\"2018-02-24 14:04:03\",\"isSendSelfAddr\":false,\"csCount\":0,\"orderSrc\":0,\"orderSrcOid\":0,\"expressCode\":\"ZTO\",\"isSelfPickup\":false,\"isSfTopay\":false,\"unpackingNum\":0,\"productCostPriceJson\":\"{\\\"costPriceDate\\\":\\\"2018-02-01\\\",\\\"productId\\\":1159,\\\"prdPrice\\\":1400,\\\"prdCostPrice\\\":600,\\\"costPriceStatus\\\":0,\\\"costPriceModifyTime\\\":\\\"2018-02-01 14:14:36\\\"}\",\"ordShipHours\":24,\"ordShipTime\":\"2018-02-25 11:01:54\"}";
        l1 = System.currentTimeMillis();
        String json = new String(Files.read(new File(jsonPath), 1800 * MiB));
        l2 = System.currentTimeMillis();
        System.out.println("文件加载完成，共耗时：" + (l2 - l1) + " 毫秒，length:" + json.length());
        
//        testGson(json);//145033459
//        testFastJson(json);
//        testZhiqim(json);
        testMy(json, jsonString);
        // Token root = Token.newToken(Token.BRACE_L, 0);
        // Token next = root.next(Token.STRING, 1);
        //
        testOther();
        // List<Integer> ls = new LinkedList<Integer>();
        // // List<Token> ls = new LinkedList<Token>();
        
        // int count = 25033459;
        // l1 = System.currentTimeMillis();
        // TokenPool pool = new TokenPool(1000);
        // for (int i = 0; i < count; i++)
        // {
        // pool.add(Token.newToken(Token.BRACE_L, i)).context(null);
        // }
        // System.out.println(count + " - time:" + (System.currentTimeMillis() - l1));
        // System.out.println(pool.size());
        // System.out.println(pool.get(count - 1).begin());
        
        //
        // System.out.println(json.length());
        // System.out.println(ls.size()+2);
        // // System.out.println(Integer.MAX_VALUE);
        jsonString.equals(null);
    }
    
    public static void testOther()
    {
        
       
        String aaa = "f";
        String result = null;
        long t1 = System.nanoTime();
        if("a".equals(aaa)){
            result = "找到了";
        } else if ("b".equals(aaa)) {
            result = "找到了";
        } else if ("c".equals(aaa)) {
            result = "找到了";
        } else if ("d".equals(aaa)) {
            result = "找到了";
        } else if ("e".equals(aaa)) {
            result = "找到了";
        } else if ("f".equals(aaa)) {
            result = "找到了";
        } else if ("g".equals(aaa)) {
            result = "找到了";
        } else if ("h".equals(aaa)) {
            result = "找到了";
        } else if ("i".equals(aaa)) {
            result = "找到了";
        } else if ("j".equals(aaa)) {
            result = "找到了";
        } else if ("k".equals(aaa)) {
            result = "找到了";
        } else if ("l".equals(aaa)) {
            result = "找到了";
        } else if ("m".equals(aaa)) {
            result = "找到了";
        } else if ("n".equals(aaa)) {
            result = "找到了";
        } else {
            result = "未找到";
        }
        long t2 = System.nanoTime();
        System.out.println("if :\t" + (t2 - t1));
        
        System.out.println(result+":"+aaa);
        //switch语句测试代码：

        long tt1 = System.nanoTime();
        switch (aaa) {
            case "a":
                result = "找到了";
                break;
            case "b":
                result = "找到了";
                break;
            case "c":
                result = "找到了";
                break;
            case "d":
                result = "找到了";
                break;
            case "e":
                result = "找到了";
                break;
            case "f":
                result = "找到了";
                break;
            case "g":
                result = "找到了";
                break;
            case "h":
                result = "找到了";
                break;
            case "i":
                result = "找到了";
                break;
            case "j":
                result = "找到了";
                break;
            case "k":
                result = "找到了";
                break;
            case "l":
                result = "找到了";
                break;
            case "m":
                result = "找到了";
                break;
            case "n":
                result = "找到了";
                break;
            default:
                result = "未找到";
                break;
        }
        long tt2 = System.nanoTime();
        System.out.println("case :\t" + (tt2 - tt1));
    }
    public static Object getArr()
    {
        Boolean[] temp = { true, false };
        boolean[] arr = new boolean[2];
        System.arraycopy(temp, 0, arr, 0, 2);
        return arr;
    }
    
    public static void testMy(String json, String jsonString)
    {
        long l1 = 0, l2 = 0;
        List<OrdOrder> orders = new ArrayList<OrdOrder>();
        System.out.println("===========================================================================");
        long a = 0;
        l1 = System.currentTimeMillis();
        // json =
        // "[{\"oid\":1970649346523,\"tids\":\"625421090979097318\",\"status\":\"2\"},{\"oid\":1970649256481,\"tids\":\"625345184971437955\",\"status\":\"2\",\"shopNick\":\"th办公旗舰店\"},{\"oid\":1970649176433,\"tids\":\"625470499260064842\",\"status\":\"1\",\"shopNick\":\"yawiiwen\",\"buyerNick\":\"许诺612\"},{\"oid\":1970649086392,\"tids\":\"625340544680873331\",\"status\":\"2\",\"servicesMessage\":\"\",\"isOldUser\":0},{\"oid\":1970649026370,\"tids\":\"555698372277719588\",\"status\":\"1\",\"shopNick\":\"th办公旗舰店\"\"isOldUser\":0}]";
         orders = Jsons.toList(json, OrdOrder.class);
        // orders = Jsons.toList2(json, OrdOrder.class);
        // pool = Jsons.getTokens(json);
        // Token[]ss = token.getElements();
        // token = Jsons.getTokens(json, "oid");
        l2 = System.currentTimeMillis();
        System.out.println("自己代码 共生成 " + (orders != null ? orders.size() : a) + "条数据，共耗时：" + (l2 - l1) + " 毫秒");
        for (int i = 0; i < orders.size(); i++)
        {
            if (i > 0)
                break;
            
            System.out.println(orders.get(i));
        }
        System.out.println("===========================================================================");
//        // json = "";
//        
//        Person p = new Person();
//        p.setAge(18);
//        p.setId(1);
//        p.setName("a");
//        p.setSex("0");
//        System.out.println(Jsons.toString(p));
//        System.out.println("============================================================");
//        
//        json = "[";
//        json += "{\"id\":1,\"name\":\"a\",\"age\":18,\"sex\":\"0\"}";
//        json += ",";
//        json += "{\"id\":2,\"name\":\"b\",\"age\":19,\"sex\":\"1\"}";
//        // json += "{\"id\":2}";
//        json += "]";
//        
//        // json = "[1,6,3,1,85]";
//        //
//        // json = "{\"id\":2, ss:null,}";
//        System.out.println(json);
//        
//        System.out.println("===============");
//        JsonLexer lexer = new JsonLexer(json);
//        while (lexer.hasNext())
//        {
//            String value = lexer.naxtToken().value();
//            System.out.println(lexer.scope() + "\t" + lexer.tokenType() + "\t" + value);
//        }
//        
//        List<Person> peList = Jsons.toList(json, Person.class);
//        for (Person person : peList)
//        {
//            System.out.println(person);
//        }
//        
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
        orders = org.zhiqim.kernel.json.Jsons.toList(json, OrdOrder.class);
        l2 = System.currentTimeMillis();
        System.out.println("公司代码 共生成 " + (orders.size()) + "条数据，共耗时：" + (l2 - l1) + " 毫秒");
    }
}
