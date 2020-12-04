package com.li.api.core.exceptionCodeConfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 黎源
 * @date 2020/11/14 20:35
 */


@PropertySource(value = "classpath:exceptionCode/exception-code.properties")
@ConfigurationProperties(prefix = "exception")
@Component
public class HttpExceptionCodeConfiguration {
    private Map<Integer, String> codes = new HashMap<>();

    public String getMessage(int code){
        return codes.get(code);
    }

    public Map<Integer, String> getCodes() {
        return codes;
    }

    public void setCodes(Map<Integer, String> codes) {
        this.codes = codes;
    }
}
