package cn.canye365.demo.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author CanYe
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("*") //允许的url
                .allowedHeaders(CorsConfiguration.ALL) // 服务器使用的字段
                .allowedMethods(CorsConfiguration.ALL) // 方法
                .allowCredentials(true) // 是否允许用户发送、处理 cookie
                .maxAge(3600); // 1小时内不需要再预检（发OPTIONS请求）
    }
}
