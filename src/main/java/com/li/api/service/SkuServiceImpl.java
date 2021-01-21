package com.li.api.service;

import com.li.api.exception.NotFoundException;
import com.li.api.pojo.model.Sku;
import com.li.api.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author 黎源
 * @date 2021/1/11 16:15
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuRepository repository;

    public List<Sku> getSkusByIdS(List<Long> ids) {
        Optional<List<Sku>> skus = repository.findAllByIdIn(ids);
        return skus.orElseThrow(() -> new NotFoundException(30008));
    }
}
