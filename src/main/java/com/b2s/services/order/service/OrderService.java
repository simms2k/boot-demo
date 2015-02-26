package com.b2s.services.order.service;

import com.b2s.services.order.model.Order;
import com.b2s.services.order.model.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import java.util.NoSuchElementException;

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
        Order order = orderRepository.findOne(id);
        if(null == order) {
            throw new NoSuchElementException();
        } else {
            return order;
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ NoSuchElementException.class })
    public void handleNotFound() {
    }

    @RequestMapping(method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@Valid @RequestBody Order order) {
        return orderRepository.save(new Order(
                order.getVarOrderId(),
                order.getPartnerOrderId(),
                order.getPrice()
        ));
    }

    @RequestMapping(value = "/{id}", method = PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long id, @Valid @RequestBody Order order) {
        get(id); //validate order exists
        orderRepository.save(new Order(
                id,
                order.getVarOrderId(),
                order.getPartnerOrderId(),
                order.getPrice()
        ));
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable long id) {
        orderRepository.delete(id);
    }
}
