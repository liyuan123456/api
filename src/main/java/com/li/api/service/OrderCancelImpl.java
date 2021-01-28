package com.li.api.service;

import com.li.api.exception.ServerException;
import com.li.api.pojo.bo.OrderMessageBo;
import com.li.api.pojo.model.Order;
import com.li.api.repository.OrderRepository;
import com.li.api.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author 黎源
 * @date 2021/1/25 19:40
 */
@Service
public class OrderCancelImpl {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SkuRepository skuRepository;

    @Transactional(rollbackFor = ServerException.class)
    public void cancel(OrderMessageBo orderMessageBo) {
        if (orderMessageBo.getOrderId() <= 0) {
            throw new ServerException(9999);
        }
        cancel(orderMessageBo.getOrderId());
    }

    private void cancel(Long oid) {
        Optional<Order> orderOptional = orderRepository.findById(oid);
        Order order = orderOptional.orElseThrow(() -> new ServerException(9999));
        int result = orderRepository.cancelOrder(oid);
        if (result != 1) {
            return;
        }
        order.getSnapItems().stream()
                .forEach( sku -> skuRepository.backStock(sku.getId(),sku.getCount().longValue()));
    }
}
