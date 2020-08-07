package com.shinemo.bi.ms.system.interceptor;

import com.shinemo.common.annotation.SmIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 方法权限拦截
 *
 * @author luchao
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private Logger accessLogger = LoggerFactory.getLogger("accessLogger");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }
        final Method method = ((HandlerMethod) handler).getMethod();
        final SmIgnore smIgnore = method.getAnnotation(SmIgnore.class);
        if (null != smIgnore) {
            return true;
        }

        return true;
    }

}
