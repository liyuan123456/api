package com.li.api.util;

import java.util.Date;

/**
 * @author 黎源
 * @date 2021/1/6 16:26
 */
public class DateUtil {
    public static Boolean isExpired(Date now,Date startDate,Date endDate) {
        Long nowDate = now.getTime();
        Long start = startDate.getTime();
        Long end = endDate.getTime();
        return nowDate > start && nowDate < end;
    }
}
