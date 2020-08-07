package com.shinemo.bi.ms.system.job;

import com.shinemo.bi.ms.system.common.config.OptionConfig;
import com.shinemo.jace.biz.HttpCenterClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 注册应用到应用注册中心
 * Create Time:2019-10-31
 * User: luchao
 * Email: luc@shinemo.com
 */
@Component
@Order(1)
public class AppRegisterJob implements CommandLineRunner {

    @Value("${spring.application.name}")
    private String appName;
    @Value("${http.center.ips}")
    private String httpCenterIps;

    @Override
    public void run(String... args) throws Exception {
        if (System.getProperty(OptionConfig.DEBUG) != null && Boolean.valueOf(System.getProperty(OptionConfig.DEBUG))) {
            return;
        }
        new HttpCenterClient().Register(appName, OptionConfig.SERVER_PORT, httpCenterIps);
    }
}
