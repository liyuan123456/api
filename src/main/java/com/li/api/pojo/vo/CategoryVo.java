package com.li.api.pojo.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 黎源
 * @date 2020/12/7 16:16
 */
@Getter
@Setter
public class CategoryVo {
    private Long id;
    private String name;
    private Boolean isRoot;
    private Long parentId;
    private String img;
    private Byte index;
}
