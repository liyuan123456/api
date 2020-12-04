package com.li.api.pojo.vo;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 黎源
 * @date 2020/12/3 22:44
 */
@Getter
@Setter
public class PaggingDozer<T , K> extends Pagging {
    public PaggingDozer(Page<T> page , Class<K> kClass){
        initPagging(page);
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        List<T> listT = page.getContent();
        List<K> listK = new ArrayList<>();
        listT.forEach( t -> {
            K k = mapper.map(t, kClass);
            listK.add(k);
        });
        this.items = listK;
    }
}
