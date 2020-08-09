package cn.canye365.generator.vue;

import cn.canye365.generator.utils.DbUtil;
import cn.canye365.generator.utils.Field;
import cn.canye365.generator.utils.FreemarkerUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

/**
 * 生成Vue代码模板 的启动类
 * @author CanYe
 */
public class VueGenerator {

    //读generatorConfig.xml
    static String generatorConfigPath = "demo/src/main/resources/generator/generatorConfig.xml";


    static String MODULE = "business";
    static String toVuePath = "vue-element-admin-master/src/views/table/";

    public static void main(String[] args) throws Exception {

        String module = MODULE;

        // 只生成配置文件中的第一个table节点
        File file = new File(generatorConfigPath);
        SAXReader reader = new SAXReader();
        //读取xml文件到Document中
        Document doc = reader.read(file);
        //获取xml文件的根节点
        Element rootElement=doc.getRootElement();
        //读取context节点
        Element contextElement = rootElement.element("context");
        //定义一个Element用于遍历
        Element tableElement;
        //取第一个“table”的节点
        tableElement = contextElement.elementIterator("table").next();
        //读取属性
        String Domain = tableElement.attributeValue("domainObjectName");
        String tableName = tableElement.attributeValue("tableName");
        String tableNameCn = DbUtil.getTableComment(tableName);
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        System.out.println("表："+tableElement.attributeValue("tableName"));
        System.out.println("Domain："+tableElement.attributeValue("domainObjectName"));
        /*String Domain = "Section";
        String domain = "section";
        String tableNameCn = "小节";
        String tableName = "section";
        String module = MODULE;*/

        List<Field> fieldList = DbUtil.getColumnByTableName(tableName);
        Set<String> typeSet = getJavaTypes(fieldList);
        Map<String, Object> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);
        map.put("tableNameCn", tableNameCn);
        map.put("module", module);
        map.put("fieldList", fieldList);
        map.put("typeSet", typeSet);

        // vue
        FreemarkerUtil.initConfig("vue2.ftl");
        FreemarkerUtil.generator(toVuePath + domain + ".vue", map);

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
