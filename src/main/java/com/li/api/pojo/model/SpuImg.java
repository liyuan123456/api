package com.li.api.pojo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author 黎源
 * @date 2020/11/30 18:19
 */
@Entity
@Getter
@Setter
public class SpuImg extends BaseEntity{
    @Id
    @JsonIgnore
    private Long id;
    private String img;
    @JsonIgnore
    private Long spuId;
}
