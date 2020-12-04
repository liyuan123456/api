package com.li.api.pojo.bo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 黎源
 * @date 2020/12/2 21:44
 */
@Setter
@Getter
@Builder
public class PageCounter {
    private Integer pageNumber;
    private Integer size;
}
