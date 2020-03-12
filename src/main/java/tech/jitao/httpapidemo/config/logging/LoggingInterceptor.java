package tech.jitao.httpapidemo.config.logging;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import tech.jitao.httpapidemo.config.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            NoLogging noLogging = handlerMethod.getBean().getClass().getAnnotation(NoLogging.class);
            if (noLogging != null) {
                request.setAttribute(RequestAttributes.NO_LOGGING, noLogging);
            }
        }
    }
}
