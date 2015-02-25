package com.b2s.services.order;

import com.b2s.services.order.model.Order;
import com.b2s.services.order.model.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class TestConfig {
    @Autowired
    private OrderRepository orderRepository;

    @PostConstruct
    public void seedDb() {
        orderRepository.save(new Order("foo", "bar", "USD 10.99"));
        orderRepository.save(new Order("faz", "baz", "USD 15.99"));
        orderRepository.save(new Order("this", "that", "USD 17.99"));
    }
}
