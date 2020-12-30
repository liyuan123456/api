package com.li.api.v1.controller;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.li.api.exception.NotFoundException;
import com.li.api.pojo.model.Theme;
import com.li.api.pojo.vo.ThemeSpusVo;
import com.li.api.pojo.vo.ThemeVo;
import com.li.api.service.ThemeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author 黎源
 * @date 2020/12/7 19:38
 */
@RestController
@RequestMapping("/v1/theme")
public class ThemeController {

    @Autowired
    private ThemeServiceImpl service;

    @GetMapping("/by/names")
    public List<ThemeVo> getSimpleThemeByNames(@RequestParam(name = "names") String names) {
        List<String> list = Arrays.asList(names.split(","));
        List<ThemeVo> themeVos = service.getThemeByNames(list);
        return themeVos;
    }

    @GetMapping("/name/{name}/with_spu")
    public ThemeSpusVo getThemeByName(@NotBlank @PathVariable(name = "name") String name) {
        Optional<Theme> optionalTheme = service.getThemeByName(name);
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        return optionalTheme.map(value -> mapper.map(value, ThemeSpusVo.class))
                            .orElseThrow(() -> new NotFoundException(30004));
    }

}
