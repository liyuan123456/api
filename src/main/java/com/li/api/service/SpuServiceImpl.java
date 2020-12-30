package com.li.api.service;

import com.li.api.exception.NotFoundException;
import com.li.api.pojo.bo.PageCounter;
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

    @Override
    public Page<Spu> getSpuByCategoryId(Long cid, Boolean isRoot, PageCounter pageCounter) {
        Pageable pageable = PageRequest.of(pageCounter.getPageNumber(), pageCounter.getSize(),
                Sort.by(Sort.Direction.DESC, "createTime"));
        Page<Spu> page;
        if (isRoot) {
            page = repository.findAllByRootCategoryId(cid, pageable);
        } else {
            page = repository.findAllByCategoryId(cid, pageable);
        }
        if(page.getTotalElements() == 0){
            throw new NotFoundException(30002);
        }
        return page;
    }

}
