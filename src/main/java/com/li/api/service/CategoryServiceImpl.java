package com.li.api.service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.li.api.exception.NotFoundException;
import com.li.api.pojo.model.Category;
import com.li.api.pojo.model.GridCategory;
import com.li.api.pojo.vo.CategoryVo;
import com.li.api.repository.CategoryRepository;
import com.li.api.repository.GridCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 黎源
 * @date 2020/12/7 16:00
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;
    @Autowired
    private GridCategoryRepository gridRepository;

    public Map getAllCategory(){
        List<Category> roots = repository.findAllByIsRootOrderByIndexAsc(true);
        List<Category> subs = repository.findAllByIsRootOrderByIndexAsc(false);
        if(roots == null || subs == null){
            throw new NotFoundException(30003);
        }
        Map map = new HashMap<String, Category>();
        map.put("roots", DozerCategoryVo(roots, CategoryVo.class));
        map.put("subs", DozerCategoryVo(subs,CategoryVo.class));
        return map;
    }

    public List<GridCategory> getAllGridCategory() {
        List<GridCategory> list = gridRepository.findAll();
        if(list == null){
            throw new NotFoundException(30003);
        }
        return list;
    }
    private <T,K> List<K> DozerCategoryVo(List<T> list,Class<K> kClass) {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        List listk = new ArrayList<K>();
        list.forEach(category -> {
            listk.add(mapper.map(category, kClass));
        });
        return listk;
    }
}
