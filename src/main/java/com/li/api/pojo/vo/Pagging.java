package com.li.api.pojo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author 黎源
 * @date 2020/12/3 22:07
 */
@Getter
@Setter
@NoArgsConstructor
public class Pagging<T> {
    protected Long title;
    protected Integer count;
    protected Integer page;
    protected Integer totalPage;
    protected List<T> items;

    public Pagging(Page<T> page){
        initPagging(page);
        this.items = page.getContent();
    }
    protected void initPagging(Page<T> page){
        this.title = page.getTotalElements();
        this.count = page.getSize();
        this.page = page.getNumber();
        this.totalPage = page.getTotalPages();
    }
}
