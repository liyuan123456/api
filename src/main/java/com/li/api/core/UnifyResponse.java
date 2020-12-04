package com.li.api.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author 黎源
 * @date 2020/11/13 17:49
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UnifyResponse {
    private Integer code;
    private String message;
    private String request;
}
