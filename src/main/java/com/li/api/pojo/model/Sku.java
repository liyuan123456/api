package com.li.api.pojo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.li.api.util.GenericAndJson;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 黎源
 * @date 2020/11/30 18:19
 */
@Entity
@Getter
@Setter
@Where(clause = "delete_time is null and online = 1")
public class Sku extends BaseEntity {
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

    public BigDecimal getRealPrice() {
        return discountPrice == null ? price : discountPrice;
    }
    public List<Specs> getSpecs() {
        return GenericAndJson.JsonToObject(this.specs, new TypeReference<List<Specs>>() {
        });
    }

    public void setSpecs(List<Specs> specs) {
        String jsonstr = GenericAndJson.ObjectToJson(specs);
        this.specs = jsonstr;
    }

    @JsonIgnore
    public List<String> getSpecValueList() {
        return getSpecs().stream().map(Specs::getValue).collect(Collectors.toList());
    }
}
