package com.li.api.service;

import com.li.api.exception.NotFoundException;
import com.li.api.pojo.model.Banner;
import com.li.api.repository.BannerRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 黎源
 * @date 2020/11/26 16:24
 */

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository repository;

    @Override
    public Banner getBanner(String name) {
        Banner banner = repository.findOneByName(name);
        if(banner == null){
            throw new NotFoundException(30001);
        }
        return banner;
    }
}
