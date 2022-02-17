package com.abhi.springjpademo.service;

import com.abhi.springjpademo.entity.Orders;
import com.abhi.springjpademo.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrderWithoutEx() {
        Orders o = new Orders();
        o.setTitle("1st order");
        o.setDescription("my first order");

        this.orderRepository.save(o);
    }

    public void createOrderWithRunTimeEx() {
        Orders o = new Orders();
        o.setTitle("1st order");
        o.setDescription("my first order");

        this.orderRepository.save(o);
        throw new RuntimeException("exception in creating order");
    }

    @Transactional
    public void createOrderWithEx() {
        Orders o = new Orders();
        o.setTitle("1st order");
        o.setDescription("my first order");

        this.orderRepository.save(o);
        throw new RuntimeException("exception in creating order");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createOrderWithNewPropagationEx() {
        Orders o = new Orders();
        o.setTitle("1st order");
        o.setDescription("my first order");

        this.orderRepository.save(o);
        throw new RuntimeException("exception in creating order with new propagation");
    }
}
