import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

import org.zhiqim.kernel.util.Streams;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiyuan.util.json.JsonParser;
import com.xiyuan.util.json.Jsons;
import com.xiyuan.util.json.parser.ObjectParser;

import frame.model.GenericList;
import frame.model.GenericMap;
import frame.model.OrdOrder;
import frame.model.Order;
import frame.model.TradeAll;

public class JsonMain
{
    public static byte[] read(File file, int max)
    {
        if (file == null || !file.isFile() || !file.canRead())
            return null;
        
        try (FileInputStream input = new FileInputStream(file))
        {
            
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = input.read(buffer)) != -1)
                output.write(buffer, 0, len);
            return output.toByteArray();
        }
        catch (IOException e)
        {
            return null;
        }
    }
    
    public static void main(String[] args) throws Exception
    {
        System.out.println("args = " + Arrays.toString(args));
        int listSize = 10000;
        if (args != null && args.length > 0 && Pattern.matches("^\\d+$", args[0]))
            listSize = Integer.parseInt(args[0]);
        String jsonPath = "./json/" + listSize + ".json";
        File jsonFile = new File(jsonPath);
        if (!jsonFile.exists())
            throw new Exception("json文件不存在：" + jsonPath);
        
        long l1 = System.currentTimeMillis();
        long l2 = l1;
        String jsonString = "{\"oid\":1802241101547130,\"tids\":\"119037997603325752\",\"status\":\"7\",\"shopNick\":\"th办公旗舰店\",\"buyerNick\":\"梦醒天神人\",\"prdTypeId\":1,\"productId\":1159,\"productText\":\"名片 | 铜版纸覆膜 | 90x54mm | 双面 | 2百张 | 1款1模\",\"policyIds\":\"\",\"amount\":1400,\"draftType\":0,\"invoiceType\":0,\"invoiceNotes\":\"\",\"invoiceItin\":\"\",\"industryId\":0,\"isOnlyDesign\":false,\"isUrgent\":false,\"printWidth\":0,\"printHeight\":0,\"printKs\":1,\"printMs\":1,\"printSpecial\":\"\",\"creater\":\"蛋蛋\",\"createTime\":\"2018-02-24 11:01:54\",\"modifyTime\":\"2018-02-24 14:07:50\",\"userText\":\"\",\"userMobile\":\"\",\"userQq\":\"\",\"receiverName\":\"黄浩\",\"receiverMobile\":\"13157172203\",\"receiverState\":\"浙江省\",\"receiverCity\":\"杭州市\",\"receiverDistrict\":\"滨江区\",\"receiverAddress\":\"长河街道春晓路529号 江南星座1栋一单元502\",\"supplierId\":6805,\"supplierOid\":\"10476998\",\"supplierOidStatus\":\"b\",\"supplierTime\":\"2018-02-24 14:04:03\",\"isSendSelfAddr\":false,\"csCount\":0,\"orderSrc\":0,\"orderSrcOid\":0,\"expressCode\":\"ZTO\",\"isSelfPickup\":false,\"isSfTopay\":false,\"unpackingNum\":0,\"productCostPriceJson\":\"{\\\"costPriceDate\\\":\\\"2018-02-01\\\",\\\"productId\\\":1159,\\\"prdPrice\\\":1400,\\\"prdCostPrice\\\":600,\\\"costPriceStatus\\\":0,\\\"costPriceModifyTime\\\":\\\"2018-02-01 14:14:36\\\"}\",\"ordShipHours\":24,\"ordShipTime\":\"2018-02-25 11:01:54\"}";
        l1 = System.currentTimeMillis();
        String json = new String(read(new File(jsonPath), 1800 * 1048576), "UTF-8");
        l2 = System.currentTimeMillis();
        System.out.println("文件加载完成，共耗时：" + (l2 - l1) + " 毫秒，length:" + json.length());
        
        int op = -1;
        if (args != null && args.length > 1 && Pattern.matches("^\\d+$", args[1]))
            op = Integer.parseInt(args[1]);
        System.out.println("测试option：" + op);
        
        switch (op)
        {
            case 1:
                testGson(json);// 145033459
                break;
            case 2:
                testFastJson(json);
                break;
            case 3:
                testZhiqim(json);
                break;
            
            case 6:
                testOther();
                break;
            case 7:
                testMyGenericJson(json, jsonString, args.length == 0);
                break;
            case 8:
                doBuildType();
                break;
            default:
                testMy(json, jsonString, args.length == 0);
                break;
        }
        // testGson(json);//145033459
        // testFastJson(json);
        // testZhiqim(json);
        // testMy(json, jsonString);
        // Token root = Token.newToken(Token.BRACE_L, 0);
        // Token next = root.next(Token.STRING, 1);
        //
        
        // List<Integer> ls = new LinkedList<Integer>();
        // // List<Token> ls = new LinkedList<Token>();
        
        // int count = 25033459;
        // l1 = System.currentTimeMillis();
        // TokenPool pool = new TokenPool(1000);
        // for (int i = 0; i < count; i++)
        // {
        // pool.add(Token.newToken(Token.BRACE_L, i)).context(null);
        // }
        // System.out.println(count + " - time:" + (System.currentTimeMillis() -
        // l1));
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
        long l1 = 0, l2 = 0;
        int count = 5;
        JsonParser parser = new ObjectParser(null);
        l1 = System.nanoTime();
        for (int i = 0; i < count; i++)
            parser.getFieldMapDeep(OrdOrder.class);
        l2 = System.nanoTime();
        System.out.println("hashMap:\t" + (l2 - l1) * 1.0 / 1000000);
        
        l1 = System.nanoTime();
        for (int i = 0; i < count; i++)
            parser.getFieldMapDeep(OrdOrder.class);
        l2 = System.nanoTime();
        System.out.println("currentMap:\t" + (l2 - l1) * 1.0 / 1000000);
        
        System.out.println("==============================");
        parser = new ObjectParser(null);
        l1 = System.nanoTime();
        for (int i = 0; i < count; i++)
            parser.getFieldMapDeep(OrdOrder.class);
        l2 = System.nanoTime();
        System.out.println("hashMap:\t" + (l2 - l1) * 1.0 / 1000000);
        
        l1 = System.nanoTime();
        for (int i = 0; i < count; i++)
            parser.getFieldMapDeep(OrdOrder.class);
        l2 = System.nanoTime();
        System.out.println("currentMap:\t" + (l2 - l1) * 1.0 / 1000000);
    }
    
    public static Object getArr()
    {
        Boolean[] temp = { true, false };
        boolean[] arr = new boolean[2];
        System.arraycopy(temp, 0, arr, 0, 2);
        return arr;
    }
    
    @SuppressWarnings({ "unused" })
    public static void testMy(String json, String jsonString, boolean console) throws Exception
    {
        // double l1 = 0, l2 = 0;
        // List<OrdOrder> orders = new ArrayList<OrdOrder>();
        // System.out.println("===========================================================================");
        // long a = 0;
        // l1 = System.currentTimeMillis();
        // orders = Jsons.toList(json, OrdOrder.class);
        // l2 = System.currentTimeMillis();
        // System.out.println("自己代码 共生成 " + (orders != null ? orders.size() : a) + "条数据，共耗时：" + (l2 - l1) / 1000 +
        // " 秒");
        // int empty = 0;
        // int start = 0;
        // for (int i = 0; i < orders.size(); i++)
        // {
        // OrdOrder o = orders.get(i);
        //
        // if (i > 1)
        // break;
        //
        // if (console)
        // System.out.println(Jsons.toString(orders.get(i)));
        // }
        json = Streams.getStringUTF8(new FileInputStream("./json/log.txt"));
        String tradeStr = Jsons.getString(json, "trade");
        System.out.println(tradeStr);
        System.out.println(Jsons.getString(json, "title"));
        System.out.println(Jsons.getString(json, "alipay_no"));
        System.out.println(Jsons.getString(json, "pic_path"));
//        TradeAll trade = Jsons.toObject(tradeStr, TradeAll.class);
//        Jsons.geta
//        Order[] orderArrStr = Jsons.getArray(tradeStr, "order", Order.class);
//        System.out.println(Jsons.toString(orderArrStr));
//        Order[] orderArr = Jsons.toObject(orderArrStr, Order[].class);
//        List<Order> orders = new ArrayList<Order>();
//        for (Order order : orderArr)
//            orders.add(order);
//        trade.setOrders(orders);
////        Map<String, String> map = Jsons.toMapSS(responseText, HashMap.class);
//        System.out.println(responseText);
//        
//        Order order = Jsons.toObject(responseText, Order.class);
//        System.out.println(Jsons.toString(order));
//      syso  String ordersstr = map.get("orders");
//        String orderArrStr = Jsons.getArray(ordersstr, "order");
//        map.remove("orders");
//        tradeStr = Jsons.toString(map);
//        
//        TradeAll trade = Jsons.toObject(tradeStr, TradeAll.class);
//        
//        Order[] orderArr = Jsons.toObject(orderArrStr, Order[].class);
//        List<Order> orders = new ArrayList<Order>();
//        for (Order order : orderArr)
//            orders.add(order);
//        trade.setOrders(orders);
    }
    
    public static void testMyGenericJson(String json, String jsonString, boolean console) throws Exception
    {
        double l1 = 0, l2 = 0;
        String genericJson = "{\"list\":" + json + "}";
        l1 = System.currentTimeMillis();
        GenericList genericList = Jsons.toObject(genericJson, GenericList.class);
        l2 = System.currentTimeMillis();
        System.out.println("GenericList==========================共生成 " + (genericList != null ? genericList.getList().size() : 0) + "条数据，共耗时：" + (l2 - l1) / 1000 + " 秒");
        // System.out.println(genericList);
        
        // for (int i = 0; i < genericList.getList().size(); i++)
        // {
        // OrdOrder o = genericList.getList().get(i);
        // System.out.println(o == null ? i : "");
        // }
        
        genericJson = "{\"map\":" + jsonString + "}";
        l1 = System.currentTimeMillis();
        GenericMap genericMap = Jsons.toObject(genericJson, GenericMap.class);
        l2 = System.currentTimeMillis();
        System.out.println("GenericMap==========================共生成 " + (genericMap != null ? genericMap.getMap().size() : 0) + "条数据，共耗时：" + (l2 - l1) / 1000 + " 秒");
        // System.out.println(genericMap);
        //
        jsonString = "{1:aa,2:ss,3:dd,4:ff,5:rr}";
        Map<String, String> mapss = Jsons.toMapSS(jsonString, Map.class);
        System.out.println("toMapSS===========================================================================");
        System.out.println(Jsons.toString(mapss));
        System.out.println(mapss.get("1"));
        //
        // jsonString = "{1:aa,2:ss,3:dd,4:ff,5:rr}";
        // Map<String, String> mapsv = Jsons.toMapSV(jsonString, Map.class,
        // String.class);
        // System.out.println("toMapSV===========================================================================");
        // System.out.println(Jsons.toString(mapsv));
        // System.out.println(mapsv.get("1"));
        //
        // jsonString = "{1:aa,2:ss,3:dd,4:ff,5:rr}";
        // Map<Integer, String> map1 = Jsons.toMap(jsonString, Map.class,
        // int.class, String.class);
        // System.out.println("toMap<int.class, String.class>===========================================================================");
        // System.out.println(Jsons.toString(map1));
        // System.out.println(map1.get(1));
        //
        // jsonString = "{1:12,2:50,3:600,4:7524,5:66}";
        // Map<Integer, Integer> map2 = Jsons.toMap(jsonString, Map.class,
        // int.class, Integer.class);
        // System.out.println("toMap<int.class, Integer.class>===========================================================================");
        // System.out.println(Jsons.toString(map2));
        // System.out.println(map2.get(1) + "---" + map2.get(8));
        //
        // jsonString = "{1:aa,2:ss,3:dd,4:ff,5:rr}";
        // Map<Integer, Character> map3 = Jsons.toMap(jsonString, Map.class,
        // int.class, Character.class);
        // System.out.println("toMap<int.class, Character.class>===========================================================================");
        // System.out.println(Jsons.toString(map3));
        // System.out.println(map3.get(1));
        //
        // jsonString = "{1:12,2:50,3:600,4:7524,8:1,,5:66}";
        // Map<Integer, Boolean> map4 = Jsons.toMap(jsonString, Map.class,
        // int.class, Boolean.class);
        // System.out.println("toMap<int.class, Boolean.class>===========================================================================");
        // System.out.println(Jsons.toString(map4));
        // System.out.println(map4.get(1));
        
        // jsonString =
        // "{\"order\",order:{\"sssss\":[11,22], \"oid\":1968980653310,\"tids\":\"616970113173646062,616956865634646062\",\"status\":\"9\",\"shopNick\":\"th办公旗舰店\",\"buyerNick\":\"chao817817\",\"prdTypeId\":1,\"productId\":1160,\"productText\":\"名片 | 铜版纸覆膜 | 90x54mm | 双面 | 5百张 | 2款1模包设计\",\"policyIds\":null,\"amount\":5000,\"draftType\":2,\"invoiceType\":0,\"invoiceNotes\":\"\",\"invoiceItin\":\"\",\"industryId\":6932,\"thumbnail\":null,\"isOnlyDesign\":false,\"isUrgent\":false,\"printWidth\":0,\"printHeight\":0,\"printKs\":2,\"printMs\":1,\"printOrderNum\":null,\"printSpecial\":\"\",\"creater\":\"朵喵\",\"createTime\":\"2019-09-10 11:15:40\",\"modifyTime\":\"2019-09-11 17:06:21\",\"userText\":\"\",\"userNotice\":null,\"userMobile\":\"17688070465\",\"userQq\":\"\",\"userWx\":\"\",\"receiverName\":\"蔡泽超\",\"receiverMobile\":\"17688070465\",\"receiverState\":\"广东省\",\"receiverCity\":\"汕尾市\",\"receiverDistrict\":\"海丰县\",\"receiverAddress\":\"公平镇集贤四巷十八号\",\"supplierId\":1908061252058471,\"supplierOid\":null,\"supplierOidStatus\":null,\"supplierTime\":\"2019-09-10 15:50:28\",\"canceler\":null,\"cancelTime\":null,\"cancelNote\":null,\"isSendSelfAddr\":true,\"csCount\":0,\"orderSrc\":0,\"orderSrcOid\":0,\"expressCode\":\"HTKY\",\"ordPost\":1,\"isSelfPickup\":false,\"isSfTopay\":false,\"isMergeOrder\":false,\"isModifyAddrSend\":false,\"unpackingNum\":0,\"sendWaitSureNote\":null,\"productCostPriceJson\":\"{\\\"costDate\\\":\\\"2019-08-06\\\",\\\"supplierId\\\":1908061252058471,\\\"productId\\\":1160,\\\"costPrice\\\":0}\",\"policyCostPriceJsons\":null,\"cancelLation\":null,\"ordShipHours\":24,\"ordShipTime\":\"2019-09-11 11:15:40\",\"userTextReplace\":null,\"consignmentOid\":1968980653310,\"sendRemindType\":0,\"orgId\":1806051109012492,\"orgReceiveTime\":\"2019-09-10 11:18:13\",\"orderFlag\":0,\"ordDesignPlatformFlag\":2,\"designId\":1968980653310,\"designRetrunTime\":null,\"designRetrunCount\":0,\"servicesMessage\":\"\"}}";
        // Object obj = Jsons.getObject(jsonString, "order", OrdOrder.class);
        // System.out.println(Jsons.toString(obj));
        // if (obj != null)
        // {
        // System.out.println(obj.getClass().getName());
        // }
        // int[] arr = Jsons.getObject(jsonString, "sssss", int[].class);
        // System.out.println(Arrays.toString(arr));
        //
        // OrdOrder[]arr1 = Jsons.toObject(json, OrdOrder[].class);
        // System.out.println(arr1[0]);
        
        // // json = "";
        //
        // Person p = new Person();
        // p.setAge(18);
        // p.setId(1);
        // p.setName("a");
        // p.setSex("0");
        // System.out.println(Jsons.toString(p));
        // System.out.println("============================================================");
        //
        // json = "";
        // json = "[";
        // json += "{ddd:{\"id\":155},\"name\":s,\"age\":18,\"sex\":\"0\"}";
        // json += ",";
        // json += "{\"id\":2,\"name\":\"b\",\"age\":19,\"sex\":\"1\"}";
        // // json += "{\"id\":2}";
        // json += "]";
        
        // json = "[1,6,3,1,85]";
        //
        // json = "{\"id\":2, ss:null,}";
        // System.out.println(json);
        // String result = Jsons.toStringAddOrUpdate("{}", "sex1", "12\"35");
        // System.out.println(result);
        // System.out.println("===============");
        // System.out.println(JsonLexer.trim("       "));
        // JsonLexer lexer = new JsonLexer(json);
        // while (lexer.hasNext())
        // {
        // String value = lexer.naxtToken().value();
        // System.out.println(lexer.scope() + "\t" + lexer.curType() + "\t" +
        // value);
        // }
        //
        // List<Person> peList = Jsons.toList(json, Person.class);
        // for (Person person : peList)
        // {
        // System.out.println(person);
        // }
        //
    }
    
    public static void testFastJson(String json)
    {
        double l1 = 0, l2 = 0;
        List<OrdOrder> orders = new ArrayList<OrdOrder>();
        System.out.println("===========================================================================");
        l1 = System.currentTimeMillis();
        orders = JSON.parseArray(json, OrdOrder.class);
        l2 = System.currentTimeMillis();
        System.out.println("马云代码 共生成 " + (orders == null ? 0 : orders.size()) + "条数据，共耗时：" + (l2 - l1) / 1000 + " 秒");
    }
    
    public static void testGson(String json)
    {
        double l1 = 0, l2 = 0;
        List<OrdOrder> orders = new ArrayList<OrdOrder>();
        System.out.println("===========================================================================");
        l1 = System.currentTimeMillis();
        Gson gson = new Gson();
        orders = gson.fromJson(json, new TypeToken<List<OrdOrder>>() {}.getType());
        l2 = System.currentTimeMillis();
        System.out.println("谷歌代码 共生成 " + (orders.size()) + "条数据，共耗时：" + (l2 - l1) / 1000 + " 秒");
    }
    
    public static void testZhiqim(String json)
    {
        double l1 = 0, l2 = 0;
        List<OrdOrder> orders = new ArrayList<OrdOrder>();
        System.out.println("===========================================================================");
        l1 = System.currentTimeMillis();
        orders = org.zhiqim.kernel.json.Jsons.toList(json, OrdOrder.class);
        l2 = System.currentTimeMillis();
        System.out.println("公司代码 共生成 " + (orders.size()) + "条数据，共耗时：" + (l2 - l1) / 1000 + " 秒");
    }
    
    public static void doBuildType()
    {
        // List<Object> ls = new ArrayList<Object>();
        // ls.add("// 基本类型");
        // ls.add(boolean.class);
        // ls.add(byte.class);
        // ls.add(char.class);
        // ls.add(short.class);
        // ls.add(int.class);
        // ls.add(long.class);
        // ls.add(float.class);
        // ls.add(double.class);
        //
        // ls.add("// 基本封装类型");
        // ls.add(Boolean.class);
        // ls.add(Byte.class);
        // ls.add(Character.class);
        // ls.add(Short.class);
        // ls.add(Integer.class);
        // ls.add(Long.class);
        // ls.add(Float.class);
        // ls.add(Double.class);
        //
        // ls.add("// 基本数组类型");
        // ls.add(boolean[].class);
        // ls.add(byte[].class);
        // ls.add(char[].class);
        // ls.add(short[].class);
        // ls.add(int[].class);
        // ls.add(long[].class);
        // ls.add(float[].class);
        // ls.add(double[].class);
        //
        // ls.add("// 基本封装类型数组");
        // ls.add(Boolean[].class);
        // ls.add(Byte[].class);
        // ls.add(Character[].class);
        // ls.add(Short[].class);
        // ls.add(Integer[].class);
        // ls.add(Long[].class);
        // ls.add(Float[].class);
        // ls.add(Double[].class);
        //
        // ls.add("// String型&数组");
        // ls.add(String.class);
        // ls.add(String[].class);
        //
        // ls.add("// 哈希表");
        // ls.add(Map.class);
        // ls.add(HashMap.class);
        // ls.add(ConcurrentMap.class);
        // ls.add(ConcurrentHashMap.class);
        // ls.add(Hashtable.class);
        // ls.add(LinkedHashMap.class);
        // ls.add(TreeMap.class);
        //
        // ls.add("// 链表");
        // ls.add(List.class);
        // ls.add(ArrayList.class);
        // ls.add(LinkedList.class);
        // ls.add(Set.class);
        // ls.add(HashSet.class);
        //
        // ls.add("// 通用Object型&数组");
        // ls.add(Object.class);
        // ls.add(Object[].class);
        //
        // ls.add("// 时间");
        // ls.add(java.util.Calendar.class);
        // ls.add(java.util.Date.class);
        // ls.add(java.sql.Time.class);
        // ls.add(java.sql.Date.class);
        // ls.add(java.sql.Timestamp.class);
        //
        //
        // ls.add("// java.util.concurrent.atomic");
        // ls.add(AtomicBoolean.class);
        // ls.add(AtomicInteger.class);
        // ls.add(AtomicLong.class);
        // ls.add(AtomicIntegerArray.class);
        // ls.add(AtomicLongArray.class);
        // List<String> lss = new ArrayList<String>();
        // for (Object obj : ls)
        // {
        // if (obj instanceof Class<?>)
        // {
        // Class<?> cls = (Class<?>) obj;
        // int hashCode = cls.getName().hashCode();
        // String describeName = cls.getName();
        // String varableName = describeName.replaceAll("java.", "").replaceAll("lang.", "").replaceAll("util.",
        // "").replaceAll("\\.", "_");
        // if (cls.isArray())
        // {
        // describeName = cls.getComponentType().getName();
        // varableName = describeName.replaceAll("java.", "").replaceAll("lang.", "").replaceAll("util.",
        // "").replaceAll("\\.", "_");
        // }
        //
        // StringBuffer sb = new StringBuffer();
        // for (int i = 0; i < varableName.length(); i++)
        // {
        // char ch = varableName.charAt(i);
        // if (i > 0 && (ch >= 'A' && ch <= 'Z') && varableName.charAt(i-1) != '_')
        // sb.append('_');
        //
        // sb.append(ch);
        // }
        //
        // if (cls.isArray())
        // {
        // sb.insert(0, "ARR_");
        // if (cls.getComponentType().getName().hashCode() == Boolean.class.getName().hashCode()
        // || cls.getComponentType().getName().hashCode() == Byte.class.getName().hashCode()
        // || cls.getComponentType().getName().hashCode() == Short.class.getName().hashCode()
        // || cls.getComponentType().getName().hashCode() == Long.class.getName().hashCode()
        // || cls.getComponentType().getName().hashCode() == Float.class.getName().hashCode()
        // || cls.getComponentType().getName().hashCode() == Double.class.getName().hashCode()
        // || cls.getComponentType().getName().hashCode() == Character.class.getName().hashCode()
        // || cls.getComponentType().getName().hashCode() == Integer.class.getName().hashCode())
        // {
        // if (cls.getComponentType().getName().hashCode() == Character.class.getName().hashCode()
        // || cls.getComponentType().getName().hashCode() == Integer.class.getName().hashCode())
        // {
        // sb = new StringBuffer(sb.toString().replaceAll("Character", "char"));
        // sb = new StringBuffer(sb.toString().replaceAll("Integer", "int"));
        // }
        // sb.append("_o");
        // }
        // }
        // else
        // {
        // if (hashCode == Boolean.class.getName().hashCode()
        // || hashCode == Byte.class.getName().hashCode()
        // || hashCode == Character.class.getName().hashCode()
        // || hashCode == Short.class.getName().hashCode()
        // || hashCode == Integer.class.getName().hashCode()
        // || hashCode == Long.class.getName().hashCode()
        // || hashCode == Float.class.getName().hashCode()
        // || hashCode == Double.class.getName().hashCode()
        // || hashCode== Character.class.getName().hashCode()
        // || hashCode == Integer.class.getName().hashCode())
        // {
        // if (hashCode == Character.class.getName().hashCode()
        // || hashCode == Integer.class.getName().hashCode())
        // {
        // sb = new StringBuffer(sb.toString().replaceAll("Character", "char"));
        // sb = new StringBuffer(sb.toString().replaceAll("Integer", "int"));
        // }
        // sb.append("_o");
        // }
        // }
        // lss.add("/** " + describeName + ".class.getName().hashCode()<br> = " + hashCode + "; */");
        // lss.add("public final static int " + sb.toString().toUpperCase() + " = " + hashCode + ";");
        // }
        // else
        // {
        // lss.add("");
        // lss.add(obj.toString());
        // }
        // }
        //
        // for (String string : lss)
        // {
        // System.out.println(string);
        // }
        
        String ddd = "5689";
        AtomicLong arr = Jsons.toObject(ddd, AtomicLong.class);
        System.out.println(arr);
    }
}
