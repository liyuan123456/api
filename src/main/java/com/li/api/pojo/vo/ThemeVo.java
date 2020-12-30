package com.li.api.pojo.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 黎源
 * @date 2020/12/7 20:22
 */
@Getter
@Setter
public class ThemeVo {
    private Long id;
    private String title;
    private String description;
    private String name;
    private String tplName;
    private String entranceImg;
    private String extend;
    private String internalTopImg;
    private String titleImg;
    private Boolean online;
}
