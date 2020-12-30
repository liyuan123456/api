package com.li.api.v1.controller;

import com.li.api.pojo.model.GridCategory;
import com.li.api.pojo.vo.CategoryVo;
import com.li.api.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 黎源
 * @date 2020/12/7 16:05
 */
@RestController
@RequestMapping("/v1/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl service;

    @GetMapping("/all")
    public Map getAllCategory() {
        Map<String, CategoryVo> map = service.getAllCategory();
        return map;
    }

    @GetMapping("/grid/all")
    public List<GridCategory> getAllGridCategory(){
        return service.getAllGridCategory();
    }
}
