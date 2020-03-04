package tech.jitao.httpapidemo.config.auth;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import tech.jitao.httpapidemo.config.RequestAttributes;
import tech.jitao.httpapidemo.config.RequestHeaders;
import tech.jitao.httpapidemo.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AuthInterceptor implements HandlerInterceptor {
    private static final List<String> PREFIXES = Lists.newArrayList("/app", "/web");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // let OPTIONS request go
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        // only check request with specified prefixes
        if (!PREFIXES.isEmpty()) {
            boolean include = false;
            for (String prefix : PREFIXES) {
                if (request.getRequestURI().startsWith(prefix)) {
                    include = true;
                }
            }

            if (!include) {
                return true;
            }
        }

        // os, version and network
        request.setAttribute(RequestAttributes.OS, request.getHeader(RequestHeaders.APP_OS));
        request.setAttribute(RequestAttributes.VERSION, request.getHeader(RequestHeaders.APP_VERSION));
        request.setAttribute(RequestAttributes.NETWORK, request.getHeader(RequestHeaders.NETWORK));

        // annotation
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getBean().getClass().isAnnotationPresent(NoAuth.class)) {
                return true;
            }
        }

        if (!check(request)) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"); // 401

            return false;
        }

        return true;
    }

    private boolean check(HttpServletRequest request) {
        String accessToken = request.getHeader(RequestHeaders.ACCESS_TOKEN);
        if (Strings.isNullOrEmpty(accessToken)) {
            return false;
        }

        long userId = accountService.getUserIdByToken(accessToken);

        if (userId <= 0) {
            logger.warn("Check access token failed: {}", accessToken);

            return false;
        }

        request.setAttribute(RequestAttributes.USER_ID, userId);

        logger.info("{} {} {}", request.getRequestURI(), userId, accessToken);

        return true;
    }
}
