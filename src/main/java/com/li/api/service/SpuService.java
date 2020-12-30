package com.li.api.service;

import com.li.api.pojo.bo.PageCounter;
import com.li.api.pojo.model.Spu;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author 黎源
 * @date 2020/11/29 20:35
 */
public interface SpuService {
    Spu getDetail(Long id);

    Page<Spu> getSpuLatestPagging(Integer pageNumber,Integer size);

    Page<Spu> getSpuByCategoryId(Long cid, Boolean isRoot, PageCounter pageCounter);
}
