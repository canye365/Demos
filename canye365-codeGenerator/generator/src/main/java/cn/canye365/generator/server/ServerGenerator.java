package cn.canye365.generator.server;

import cn.canye365.generator.utils.DbUtil;
import cn.canye365.generator.utils.Field;
import cn.canye365.generator.utils.FreemarkerUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

/**
 * 生成Service、ServiceImpl、Controller代码模板 的启动类
 * @author CanYe
 */
public class ServerGenerator {

    //读generatorConfig.xml
    static String generatorConfigPath = "demo/src/main/resources/generator/generatorConfig.xml";


    static String MODULE = "user";
    static String toServicePath = "demo/src/main/java/cn/canye365/demo/service/";
    static String toServiceImplPath = "demo/src/main/java/cn/canye365/demo/service/impl/";
    static String toControllerPath = "demo/src/main/java/cn/canye365/demo/controller/";
    static String toDtoPath = "demo/src/main/java/cn/canye365/demo/dto/";

    public static void main(String[] args) throws Exception {

        // 获取generatorConfig.xml配置文件中的第一个table节点
        File file = new File(generatorConfigPath);
        SAXReader reader = new SAXReader();
        Document doc = reader.read(file); //读取xml文件到Document中
        Element rootElement=doc.getRootElement(); //获取xml文件的根节点
        Element contextElement = rootElement.element("context"); //读取context节点
        Element tableElement; //定义一个Element用于遍历
        tableElement = contextElement.elementIterator("table").next(); //取第一个“table”的节点
        //读取属性
        String Domain = tableElement.attributeValue("domainObjectName");
        String tableName = tableElement.attributeValue("tableName");
        String tableNameCn = DbUtil.getTableComment(tableName);
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        System.out.println("表："+tableElement.attributeValue("tableName"));
        System.out.println("Domain："+tableElement.attributeValue("domainObjectName"));

        List<Field> fieldList = DbUtil.getColumnByTableName(tableName);
        Set<String> typeSet = getJavaTypes(fieldList);
        Map<String, Object> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);
        map.put("tableNameCn", tableNameCn);
        map.put("module", MODULE);
        map.put("fieldList", fieldList);
        map.put("typeSet", typeSet);

        // Dto
        FreemarkerUtil.initConfig("dto.ftl");
        FreemarkerUtil.generator(toDtoPath + Domain + "Dto.java", map);
//        // Service
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath + Domain + "Service.java", map);
//        // ServiceImpl
        FreemarkerUtil.initConfig("serviceimpl.ftl");
        FreemarkerUtil.generator(toServiceImplPath + Domain + "ServiceImpl.java", map);
//        // Controller
        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generator(toControllerPath + Domain + "Controller.java", map);

    }

    /**
     * 获取所有的Java类型，使用Set去重
     */
    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            set.add(field.getJavaType());
        }
        return set;
    }
}
