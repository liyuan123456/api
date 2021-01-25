package com.li.api.v1.controller;

import com.li.api.core.UserLocal;
import com.li.api.core.interceptors.ScopeLevel;
import com.li.api.logic.OrderChecker;
import com.li.api.pojo.bo.PageCounter;
import com.li.api.pojo.dto.OrderDto;
import com.li.api.pojo.model.Order;
import com.li.api.pojo.vo.OrderIdVo;
import com.li.api.pojo.vo.OrderPureVo;
import com.li.api.pojo.vo.OrderSimpleVo;
import com.li.api.pojo.vo.PaggingDozer;
import com.li.api.service.OrderServiceImpl;
import com.li.api.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 黎源
 * @date 2021/1/11 15:54
 */
@RestController
@RequestMapping("/v1/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Value("${conventional.pay-time-limit}")
    private Long payTimeLimit;

    @PostMapping("/generateOrder")
    @ScopeLevel
    public OrderIdVo generateOrder(@Validated @RequestBody OrderDto orderDto) {
        Long userId = UserLocal.getUser().getId();
        OrderChecker orderChecker = orderService.isOK(userId, orderDto);
        Long oid = orderService.placeOrder(userId, orderDto, orderChecker);
        return new OrderIdVo(oid);
    }


    @ScopeLevel
    @GetMapping("/status/unpaid")
    public PaggingDozer getUnpaidSimplifyList(@RequestParam(defaultValue = "0") Integer start,
                                              @RequestParam(defaultValue = "10") Integer count) {
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> orderPage = orderService.getUnpaid(page.getPageNumber(), page.getSize());
        PaggingDozer paggingDozer = new PaggingDozer(orderPage, OrderSimpleVo.class);
        paggingDozer.getItems().forEach(o -> ((OrderSimpleVo) o).setPeriod(payTimeLimit));
        return paggingDozer;
    }

    @ScopeLevel
    @GetMapping("/by/status/{status}")
    public PaggingDozer getUnpaidSimplifyList(@PathVariable("status") int status,
                                              @RequestParam(defaultValue = "0") Integer start,
                                              @RequestParam(defaultValue = "10") Integer count) {
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> orderPage = orderService.getByStatus(page.getPageNumber(),
                page.getSize(),
                status);
        PaggingDozer paggingDozer = new PaggingDozer(orderPage, OrderSimpleVo.class);
        paggingDozer.getItems().forEach(o -> ((OrderSimpleVo) o).setPeriod(payTimeLimit));
        return paggingDozer;
    }

    @ScopeLevel
    @GetMapping("/detail/{id}")
    public OrderPureVo getUnpaidSimplifyList(@PathVariable("id")long id) {
        Order order = orderService.getOrderDetail(id);
        return new OrderPureVo(order,payTimeLimit);
    }
}
