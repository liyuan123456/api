package com.li.api.util;

import com.li.api.pojo.bo.PageCounter;

import java.util.Calendar;

/**
 * @author 黎源
 * @date 2020/12/2 21:44
 */
public class CommonUtil {
    public static PageCounter convertToPageParameter(Integer start,Integer count){
        return PageCounter.builder().pageNumber(start/count).size(count).build();
    }

    public static Calendar addSomeSeconds(Calendar calendar, int secounds) {
        calendar.add(Calendar.SECOND, secounds);
        return calendar;
    }
}
