package com.li.api.service;

import com.li.api.pojo.model.Spu;
import com.li.api.repository.SpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 黎源
 * @date 2020/11/29 20:36
 */
@Service
public class SpuServiceImpl implements SpuService {
    @Autowired
    private SpuRepository repository;

    @Override
    public Spu getDetail(Long id) {
        return repository.findOneById(id);
    }

    @Override
    public Page<Spu> getSpuLatestPagging(Integer pageNumber, Integer size) {
        PageRequest pageRequest = PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.DESC,"createTime"));
        return repository.findAll(pageRequest);
    }
}
