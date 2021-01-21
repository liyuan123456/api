package com.li.api.pojo.vo;

import com.li.api.pojo.model.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

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

    public CategoryVo(Category category){
        BeanUtils.copyProperties(category,this);
    }
}
