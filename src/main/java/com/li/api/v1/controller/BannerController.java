package com.li.api.v1.controller;

import com.li.api.core.interceptors.ScopeLevel;
import com.li.api.pojo.model.Banner;
import com.li.api.service.BannerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author 黎源
 * @date 2020/11/3 18:48
 */
@RestController
@RequestMapping("/v1/banner")
@Validated
public class BannerController {

    @Autowired
    private BannerServiceImpl bannerService;

    @GetMapping("/name/{name}")
    @ScopeLevel()
    public Banner getInfo(@PathVariable String name) {
        return bannerService.getBanner(name);
    }
}
