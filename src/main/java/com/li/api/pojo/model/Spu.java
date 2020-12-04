package com.li.api.pojo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author 黎源
 * @date 2020/11/29 20:00
 */
@Entity
@Getter
@Setter
public class Spu extends BaseEntity {
    @Id
    private long id;
    private String title;
    private String subtitle;
    private long categoryId;
    private long rootCategoryId;
    private Boolean online;
    private String price;
    private long sketchSpecId;
    private long defaultSkuId;
    private String img;
    private String discountPrice;
    private String description;
    private String tags;
    private Boolean isTest;
    private String forThemeImg;

    @OneToMany
    @JoinColumn(name = "spuId")
    private List<SpuImg> spuImgs;

    @OneToMany
    @JoinColumn(name = "spuId")
    private List<SpuDetailImg> spuDetailImgs;

    @OneToMany
    @JoinColumn(name = "spuId")
    private List<Sku> skus;
}
