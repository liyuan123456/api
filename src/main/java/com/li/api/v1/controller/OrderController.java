package com.li.api.v1.controller;

import com.li.api.core.UserLocal;
import com.li.api.core.interceptors.ScopeLevel;
import com.li.api.logic.OrderChecker;
import com.li.api.pojo.dto.OrderDto;
import com.li.api.pojo.vo.OrderIdVo;
import com.li.api.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 黎源
 * @date 2021/1/11 15:54
 */
@RestController
@RequestMapping("/v1/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("/generateOrder")
    @ScopeLevel
    public OrderIdVo generateOrder(@Validated @RequestBody OrderDto orderDto) {
        Long userId = UserLocal.getUser().getId();
        OrderChecker orderChecker = orderService.isOK(userId, orderDto);
        Long oid = orderService.placeOrder(userId, orderDto, orderChecker);
        return new OrderIdVo(oid);
    }
}
