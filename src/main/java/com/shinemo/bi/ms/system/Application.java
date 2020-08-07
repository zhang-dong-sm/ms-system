package com.shinemo.bi.ms.system;

import com.shinemo.bi.ms.system.interceptor.AclInterceptor;
import com.shinemo.bi.ms.system.common.config.OptionConfig;
import com.shinemo.bi.ms.system.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: luchao
 * @email: luc@shinemo.com
 * @date: 2019/6/17
 */
@SpringBootApplication(scanBasePackages = "com.shinemo.bi.ms.system")
@EnableScheduling
@ImportResource(locations = "classpath:application-spring.xml")
@ServletComponentScan(basePackages = "com.shinemo.baas.ms.system")
public class Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        int serverPort = OptionConfig.SERVER_PORT;
        if (System.getProperty(OptionConfig.DEBUG) != null && Boolean.valueOf(System.getProperty(OptionConfig.DEBUG))) {
            serverPort = 8090;
        }
        System.setProperty("server.port", serverPort + "");
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private AuthInterceptor authInterceptor;
    @Autowired
    private AclInterceptor aclInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**")
                .excludePathPatterns(OptionConfig.IGNORE_URI);
        registry.addInterceptor(aclInterceptor).addPathPatterns("/**");
    }
}
