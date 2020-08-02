package cn.canye365.generator.utils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * 生成代码的工具类
 * @author CanYe
 */
public class FreemarkerUtil {

    static String ftlPath = "generator/src/main/java/cn/canye365/generator/ftl/";
//    static String toPath = "generator/src/main/java/cn/canye365/generator/ftl/";
    static Template temp;
    public static void initConfig(String ftlName) throws IOException {
        // 读取模板
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setDirectoryForTemplateLoading(new File(ftlPath));
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_28));
        temp = cfg.getTemplate(ftlName);
    }

    public static void generator(String fileName, Map<String, Object> map) throws IOException, TemplateException {
        // 生成类
        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        temp.process(map, bw);
        bw.flush();
        fw.close();
    }

}
