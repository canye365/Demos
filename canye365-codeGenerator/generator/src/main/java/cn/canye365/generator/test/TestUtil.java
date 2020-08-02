package cn.canye365.generator.test;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestUtil {

    static String ftlPath = "generator/src/main/java/cn/canye365/onlinevideo/generator/test/";
    static String toPath = "generator/src/main/java/cn/canye365/onlinevideo/generator/test/";

    public static void main(String[] args) throws IOException, TemplateException {
        // 读取模板
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setDirectoryForTemplateLoading(new File(ftlPath));
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_28));
        Template temp = cfg.getTemplate("test.ftl");
        // 生成类
        FileWriter fw = new FileWriter(toPath + "Test.java");
        BufferedWriter bw = new BufferedWriter(fw);
        temp.process(null, bw);
        bw.flush();
        fw.close();
    }
}
