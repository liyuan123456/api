package com.li.api.pojo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author 黎源
 * @date 2020/12/7 17:32
 */
@Entity
@Getter
@Setter
public class GridCategory extends BaseEntity{
    @Id
    private Long id;
    private String title;
    private String img;
    private String name;
    private Long categoryId;
    private Long rootCategoryId;

}
