//package json;
//
//import cache.LocalCacheUtil;
//
///**
// * @author sheyang
// * @description
// * @date 2019/9/14
// * @time 上午10:53
// */
//public class JsonUtil {
//
//    public static void main(String[] args){
//
//        TestModel model1 = new TestModel();
//
//        model1.setBoolAtr(true);
//
//        TestSubModel subModel = new TestSubModel();
//        subModel.setIntAtr(20);
//        subModel.setStrAtr1("subStr1");
//        subModel.setStrAtr2("subStr2");
//        model1.setClassAtr(subModel);
//
//        Map<TestEnum, TestEnum> enumMap = new HashMap<TestEnum, TestEnum>();
//        enumMap.put(TestEnum.ENUM1,TestEnum.ENUM1);
//        enumMap.put(TestEnum.ENUM2,TestEnum.ENUM2);
//        model1.setEnumMapAtr(enumMap);
//
//        model1.setIntAtr(40);
//        model1.setStrAtr1("str1");
//        //        model1.setStrAtr2("str2");
//
//        Map<String, String> strMap = new HashMap<String, String>();
//        strMap.put("key1","value1");
//        strMap.put("key2","value2");
//        strMap.put("key3","value3");
//        model1.setStrMapAtr(strMap);
//
//        //1 缺失某些模型属性后序列化；
//        String model1Str = JSONSerializableUtil.serialize(model1);
//        System.out.println(model1Str);
//        /**
//         * 缺失的属性将直接被忽略，不会有null的存在；enum map和string map从序列化结果来看没有差异；map和对象也没有明显差异，但对象保留了数字primetive类型
//         * {
//         * "boolAtr": true,
//         * "classAtr": {
//         * "intAtr": 20,
//         * "strAtr1": "subStr1",
//         * "strAtr2": "subStr2"
//         * },
//         * "enumMapAtr": {
//         * "ENUM1": "ENUM1",
//         * "ENUM2": "ENUM2"
//         * },
//         * "intAtr": 40,
//         * "strAtr1": "str1",
//         * "strMapAtr": {
//         * "key1": "value1",
//         * "key2": "value2",
//         * "key3": "value3"
//         * }
//         * }
//         */
//        TestModel model2 = JSONSerializableUtil.deserialize(model1Str, TestModel.class);
//        //2 存在特殊map属性后序列化；
//        Map objectMap = new HashMap();
//        objectMap.put("2",333);
//        objectMap.put(444,"55");
//        model1.setObjectMap(objectMap);
//        model1.setStrAtr2("str2");
//        String model2Str = JSONSerializableUtil.serialize(model1);
//        System.out.println(model2Str);
//        TestModel model3 = JSONSerializableUtil.deserialize(model2Str, TestModel.class);
//        System.out.println(model3);
//
//        //        JsonSerializer
//        /**
//         * 2 对于特殊map属性，其value值的数字类型被保留，与对象一致；但是由于key的数字类型也被保留，导致最终序列化出来的json本身格式出问题,但是即使如此，fastjson也能将其反序列化回去；
//         * {
//         * "boolAtr": true,
//         * "classAtr": {
//         * "intAtr": 20,
//         * "strAtr1": "subStr1",
//         * "strAtr2": "subStr2"
//         * },
//         * "enumMapAtr": {
//         * "ENUM1": "ENUM1",
//         * "ENUM2": "ENUM2"
//         * },
//         * "intAtr": 40,
//         * "objectMap": {
//         * "2": 333,
//         * 444: "55"
//         * },
//         * "strAtr1": "str1",
//         * "strAtr2": "str2",
//         * "strMapAtr": {
//         * "key1": "value1",
//         * "key2": "value2",
//         * "key3": "value3"
//         * }
//         * }
//         */
//
//        //3 增加不存在的类属性:
//
//        String model4Str = "{\"boolAtrx\":false,\"boolAtr\":true,\"classAtr\":{\"strAtrx\":\"subStrx\",\"intAtr\":20,\"strAtr1\":\"subStr1\",\"strAtr2\":\"subStr2\"},\"enumMapAtr\":{\"ENUM1\":\"ENUM1\",\"ENUM2\":\"ENUM2\"},\"intAtr\":40,\"strAtr1\":\"str1\",\"strAtr2\":\"str2\",\"strMapAtr\":{\"key1\":\"value1\",\"key2\":\"value2\",\"key3\":\"value3\"}}";
//        TestModel model4 = JSONSerializableUtil.deserialize(model4Str, TestModel.class);
//        String model4StrNew = JSONSerializableUtil.serialize(model4);
//        Assert.assertTrue(!StringUtil.equals(model4StrNew,model4Str));
//
//        //4 增加str MAP属性
//        String model5Str = "{\"boolAtr\":true,\"classAtr\":{\"intAtr\":20,\"strAtr1\":\"subStr1\",\"strAtr2\":\"subStr2\"},\"enumMapAtr\":{\"ENUM1\":\"ENUM1\",\"ENUM2\":\"ENUM2\"},\"intAtr\":40,\"strAtr1\":\"str1\",\"strAtr2\":\"str2\",\"strMapAtr\":{\"key1\":\"value1\",\"key2\":\"value2\",\"key3\":\"value3\",\"keyX\":\"valueX\"}}";
//        TestModel model5 = JSONSerializableUtil.deserialize(model5Str, TestModel.class);
//
//        String model5StrNew = JSONSerializableUtil.serialize(model5);
//        Assert.assertTrue(StringUtil.equals(model5StrNew,model5Str));
//
//        //5 增加str MAP属性
//        String model6Str = "{\"boolAtr\":true,\"classAtr\":{\"intAtr\":20,\"strAtr1\":\"subStr1\",\"strAtr2\":\"subStr2\"},\"enumMapAtr\":{\"ENUMX\":\"ENUMX\",\"ENUM1\":\"ENUM1\",\"ENUM2\":\"ENUM2\"},\"intAtr\":40,\"strAtr1\":\"str1\",\"strAtr2\":\"str2\",\"strMapAtr\":{\"key1\":\"value1\",\"key2\":\"value2\",\"key3\":\"value3\"}}";
//        try
//
//        {
//            TestModel model6 = JSONSerializableUtil.deserialize(model6Str, TestModel.class);
//        } catch(
//                Exception e)
//
//        {
//            e.printStackTrace();
//        }
//        //        TestModel model2 = JSONSerializableUtil.deserialize(model1Str, TestModel.class);
//    }
//
//
//
//
//
//    public static <T extends Enum<T>> T valueOf(Class<T> enumType,
//                                                String name) {
//        T result = enumType.enumConstantDirectory().get(name);
//        if (result != null)
//            return result;
//        if (name == null)
//            throw new NullPointerException("Name is null");
//        throw new IllegalArgumentException(
//                "No enum constant " + enumType.getCanonicalName() + "." + name);
//    }
//
//}
//
