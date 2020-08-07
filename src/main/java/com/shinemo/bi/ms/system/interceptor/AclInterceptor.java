package com.shinemo.bi.ms.system.interceptor;

import com.shinemo.bi.ms.system.common.helper.UserLoginHelper;
import com.shinemo.common.utils.SmRequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

/**
 * 访问日志拦截器
 *
 * @author luchao
 */
@Component
public class AclInterceptor extends HandlerInterceptorAdapter {
    private Logger accessLogger = LoggerFactory.getLogger("accessLogger");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("beginTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        try {
            if (!(handler instanceof HandlerMethod)) {
                return;
            }
            long time = System.currentTimeMillis() - (Long) request.getAttribute("beginTime");
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            StringBuilder sb = new StringBuilder();
            sb.append('(').append(time).append("ms) ")
                    .append("ip:").append(SmRequestUtils.getIp()).append(' ')
                    .append("method:").append(handlerMethod.getMethod().getName()).append(' ')
                    .append("params:").append(parseParams(request)).append(' ')
                    .append("uri:").append(request.getRequestURI()).append(' ')
                    .append("type:").append(request.getMethod()).append(' ')
                    .append("uid:").append(UserLoginHelper.getUid()).append(' ')
                    .append("name:").append(UserLoginHelper.getName()).append(' ')
                    .append("mobile:").append(UserLoginHelper.getMobile()).append(' ');
            accessLogger.info("{}", sb.toString());
        } catch (Exception ex) {
            accessLogger.error("postHandle fail", ex);
        }
    }

    private String parseParams(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Map<String, String[]> map = request.getParameterMap();
        for (String name : map.keySet()) {
            if ("pwd".equalsIgnoreCase(name) || "password".equalsIgnoreCase(name) || "passwd".equalsIgnoreCase(name)) {
                continue;  //不打印密码
            }
            String[] values = map.get(name);
            String tmp = Arrays.toString(values);
            if (tmp != null && tmp.length() > 128) {
                tmp = tmp.substring(0, 127);
            }
            sb.append("&").append(name).append("=").append(tmp);
        }
        return sb.toString();
    }

}
