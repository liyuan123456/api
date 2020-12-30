package com.li.api.pojo.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 黎源
 * @date 2020/12/13 19:16
 */

@Getter
@Setter
public class ThemeSpuSimpleLifyVo {
    private long id;
    private String title;
    private String subtitle;
    private long categoryId;
    private long rootCategoryId;
    private String price;
    private String img;
    private String forThemeImg;
    private String description;
    private String discountPrice;
    private String tags;
    private Boolean isTest;
    private Boolean online;
}
