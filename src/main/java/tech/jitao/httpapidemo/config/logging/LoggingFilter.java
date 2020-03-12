package tech.jitao.httpapidemo.config.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import tech.jitao.httpapidemo.config.RequestAttributes;

import javax.annotation.Priority;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;

@Component
@Priority(Integer.MAX_VALUE - 2)
public class LoggingFilter extends OncePerRequestFilter {
    private static final String OMIT = "<< omit >>";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/actuator")
                || "/health".equals(request.getRequestURI())
                || request.getRequestURI().endsWith(".php")
        ) {
            filterChain.doFilter(request, response);

            return;
        }

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        Instant start = Instant.now();
        filterChain.doFilter(requestWrapper, responseWrapper);
        Instant end = Instant.now();

        NoLogging noLogging = (NoLogging) request.getAttribute(RequestAttributes.NO_LOGGING);
        boolean omitRequest = noLogging != null && noLogging.omitRequest();
        boolean omitResponse = noLogging != null && noLogging.omitResponse();

        String requestBody = omitRequest ? OMIT : readRequest(requestWrapper);

        Object os = request.getAttribute(RequestAttributes.OS);
        Object version = request.getAttribute(RequestAttributes.VERSION);
        Object userId = request.getAttribute(RequestAttributes.USER_ID);
        Object network = request.getAttribute(RequestAttributes.NETWORK);

        String responseBody = omitResponse ? OMIT : new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);

        responseWrapper.copyBodyToResponse();

        logger.info("{} {} {}\n<<<<<<<<<<\no= {}\nv= {}\nn= {}\nu= {}\nq= {}\nt= {} ms\nr= {}\n>>>>>>>>>>\n",
                request.getMethod(),
                request.getRequestURI(),
                responseWrapper.getStatus(),
                os,
                version,
                network,
                userId,
                requestBody,
                Duration.between(start, end).toMillis(),
                responseBody);
    }

    private String readRequest(ContentCachingRequestWrapper request) {
        if ("GET".equals(request.getMethod())) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
                sb.append(e.getKey()).append(":").append(Arrays.toString(e.getValue())).append(", ");
            }

            return sb.toString();
        } else if ("POST".equals(request.getMethod())) {
            return new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);
        } else {
            return request.getMethod();
        }
    }
}
