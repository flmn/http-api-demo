package tech.jitao.httpapidemo.config;

import com.google.common.collect.Maps;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace);

        Map<String, Object> newMap = Maps.newHashMap();
        newMap.put("code", map.get("status"));
        newMap.put("message", map.get("error"));

        return newMap;
    }
}
