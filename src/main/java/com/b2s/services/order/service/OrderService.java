package com.b2s.services.order.service;

import com.b2s.services.order.model.Order;
import com.b2s.services.order.model.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping(method = GET)
    public Iterable<Order> getAll() {
        return orderRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Order get(@PathVariable long id) {
        return orderRepository.findOne(id);
    }

    @RequestMapping(method = POST)
    public long create(@RequestBody Map<String,String> map) {
        return orderRepository.save(new Order(
                map.get("varOrderId"),
                map.get("partnerOrderId"),
                map.get("price")
        )).getId();
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public void remove(@PathVariable long id) {
        orderRepository.delete(id);
    }
}
