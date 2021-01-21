package com.li.api.pojo.vo;

import com.li.api.pojo.model.Activity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author 黎源
 * @date 2020/12/30 19:44
 */
@Setter
@Getter
public class ActivitySimpleVo {
    private Long id;
    private String title;
    private Date startTime;
    private Date endTime;
    private String remark;
    private Boolean online;
    private String entranceImg;

    public ActivitySimpleVo(Activity activity){
        BeanUtils.copyProperties(activity,this);
    }
}
