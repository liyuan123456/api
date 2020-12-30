package com.li.api.pojo.vo;

import com.li.api.pojo.model.Spu;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * @author 黎源
 * @date 2020/12/13 18:25
 */
@Getter
@Setter
public class ThemeSpusVo extends ThemeVo {
    private List<ThemeSpuSimpleLifyVo> spuList;
}
