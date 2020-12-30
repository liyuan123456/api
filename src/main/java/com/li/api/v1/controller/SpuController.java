package com.li.api.v1.controller;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.li.api.exception.NotFoundException;
import com.li.api.pojo.bo.PageCounter;
import com.li.api.pojo.model.Spu;
import com.li.api.pojo.vo.PaggingDozer;
import com.li.api.pojo.vo.SpuSimpleLifyVo;
import com.li.api.service.SpuService;
import com.li.api.util.CommonUtil;
import com.li.api.validator.IdValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 黎源
 * @date 2020/11/29 20:25
 */
@RestController
@RequestMapping("/v1/spu")
@Validated
public class SpuController {

    @Autowired()
    @Qualifier(value = "spuServiceImpl")
    private SpuService service;

    @GetMapping("/id/{id}/detail")
    public Spu getDetail(@IdValidator @PathVariable(name = "id") Long id) {
        Spu spu = service.getDetail(id);
        if(spu == null){
            throw new NotFoundException(30002);
        }

        return spu;
    }

    @GetMapping("/latest")
    public PaggingDozer getSpuListAll(@RequestParam(name = "start", defaultValue = "0") Integer start,
                                  @RequestParam(name = "count", defaultValue = "10") Integer count) {
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        Page<Spu> page = service.getSpuLatestPagging(pageCounter.getPageNumber(), pageCounter.getSize());
        PaggingDozer<Spu, SpuSimpleLifyVo> paggingDozer = new PaggingDozer<>(page,SpuSimpleLifyVo.class);
        return paggingDozer;
    }

    @RequestMapping("/by/category/{id}")
    public PaggingDozer<Spu,SpuSimpleLifyVo> getSpuListAllByCategoryId(@Positive @PathVariable(name = "id") Long id,
                                          @RequestParam(name = "is_root",defaultValue = "false")Boolean isRoot,
                                          @RequestParam(name = "start",defaultValue = "0")Integer start,
                                          @RequestParam(name = "count",defaultValue = "2")Integer count) {
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        Page<Spu> page = service.getSpuByCategoryId(id,isRoot,pageCounter);
        PaggingDozer paggingDozer = new PaggingDozer(page, SpuSimpleLifyVo.class);
        return paggingDozer;
    }
}
