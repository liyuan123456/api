package com.li.api.v1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 黎源
 * @date 2020/11/3 18:48
 */
@Controller
public class BannerController {
    @GetMapping("/text")
    @ResponseBody
    public String getInfo() {
        return "黎某";
    }
}
