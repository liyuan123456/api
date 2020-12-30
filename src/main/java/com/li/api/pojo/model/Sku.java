package com.li.api.pojo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.li.api.util.GenericAndJson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 黎源
 * @date 2020/11/30 18:19
 */
@Entity
@Getter
@Setter
public class Sku {
    @Id
    @JsonIgnore
    private Long id;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private byte online;
    private String img;
    private String title;
    private Long spuId;
    //    @Convert(converter = ListAndJson.class)
//    private List<Specs> specs;
    private String specs;
    private String code;
    private Long stock;
    private Long categoryId;
    private Long rootCategoryId;

    public List<Specs> getSpecs() {
        return GenericAndJson.JsonToObject(this.specs, new TypeReference<List<Specs>>() {
        });
    }

    public void setSpecs(List<Specs> specs) {
        String jsonstr = GenericAndJson.ObjectToJson(specs);
        this.specs = jsonstr;
    }
}
