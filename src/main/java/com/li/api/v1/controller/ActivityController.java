package com.li.api.v1.controller;

import com.li.api.pojo.model.Activity;
import com.li.api.pojo.vo.ActivityCouponSimpleVo;
import com.li.api.pojo.vo.ActivitySimpleVo;
import com.li.api.service.ActivityService;
import com.li.api.service.ActivityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 黎源
 * @date 2020/12/30 18:51
 */
@RestController
@RequestMapping("/v1/activity")
public class ActivityController {

    @Autowired
    private ActivityServiceImpl service;

    @GetMapping("/name/{name}")
    public ActivitySimpleVo getSimpleActivity(@PathVariable(name = "name") String name) {
        Activity activity = service.getActivity(name);
        return new ActivitySimpleVo(activity);
    }

    @GetMapping("/name/{name}/with_spu")
    public ActivityCouponSimpleVo getActivityWithCoupon(@PathVariable(name = "name") String name) {
        Activity activity = service.getActivity(name);
        return new ActivityCouponSimpleVo(activity);
    }

}
