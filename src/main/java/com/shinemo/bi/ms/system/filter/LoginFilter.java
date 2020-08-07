package com.shinemo.bi.ms.system.filter;

import com.shinemo.bi.ms.system.common.helper.UserLoginHelper;
import com.shinemo.bi.ms.system.common.config.OptionConfig;
import com.shinemo.common.lang.SmRequestContext;
import com.shinemo.common.result.SmResult;
import com.shinemo.common.utils.SmRequestUtils;
import com.shinemo.common.utils.SmResponseUtil;
import com.shinemo.jace.api.userprofices.UserProficeClient;
import com.shinemo.jace.api.userprofices.UserProficeInfoDto;
import com.shinemo.jace.comm.JaceRetCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author luchao
 */
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {

    private Logger accessLogger = LoggerFactory.getLogger("accessLogger");
    @Autowired
    private UserProficeClient userProficeClient;


    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
        // checkstatus
        if (requestURI.endsWith(OptionConfig.CHECK_STATUS)) {
            chain.doFilter(request, response);
            return;
        }
        // 本地curl的接口
        String requestContext = requestURI.substring(0, requestURI.lastIndexOf("/"));
        String localAddr = request.getLocalAddr();
        String remoteAddr = request.getRemoteAddr();
        if (OptionConfig.IGNORE_URI.contains(requestContext) && localAddr.equals(remoteAddr)) {
            chain.doFilter(request, response);
            return;
        }
        // 外部接口
        String ip = SmRequestUtils.getIp();
        String uid = httpServletRequest.getHeader("uid");
        UserProficeInfoDto userProficeInfoDto = new UserProficeInfoDto();
        int rc = userProficeClient.getInfo(uid, userProficeInfoDto);
        if (rc != JaceRetCode.RET_SUCCESS) {
            accessLogger.error("userProfileClient getMobile fail - rc:{} uid:{}", rc, uid);
            accessLogger.error("login fail - ip:{} uid:{}", ip, uid);
            SmResponseUtil.out((HttpServletResponse) response, SmResult.fail("fail"));
            return;
        }
        String mobile = userProficeInfoDto.getMobile();
        String name = userProficeInfoDto.getName();
        UserLoginHelper.set(uid, mobile, name);
        accessLogger.info("login success - ip:{} uid:{} name:{} mobile:{}", ip, uid, name, mobile);
        chain.doFilter(request, response);
        // 清楚线程缓存
        SmRequestContext.remove();
    }

    @Override
    public void destroy() {
    }
}
