package com.b2s.services.order.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByVarOrderId(String varOrderId);
    List<Order> findByPartnerOrderId(String partnerOrderId);
}
