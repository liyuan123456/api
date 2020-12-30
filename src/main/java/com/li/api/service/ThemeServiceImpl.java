package com.li.api.service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.li.api.pojo.model.Theme;
import com.li.api.pojo.vo.ThemeVo;
import com.li.api.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 黎源
 * @date 2020/12/7 19:46
 */
@Service
public class ThemeServiceImpl implements ThemeService {

    @Autowired
    private ThemeRepository repository;

    public List<ThemeVo> getThemeByNames(List<String> names) {
        List<Theme> themeList = repository.getThemesByNames(names);
        List<ThemeVo> themeVoList = new ArrayList<>();
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        themeList.forEach(theme -> {
            themeVoList.add(mapper.map(theme, ThemeVo.class));
        });
        return themeVoList;
    }

    public Optional<Theme> getThemeByName(String name) {
        return repository.findByName(name);
    }
}
