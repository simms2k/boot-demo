package com.b2s.services.order.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "DemoOrder")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Size(min=3)
    private String varOrderId;
    private String partnerOrderId;
    private String price;

    public Order() {
    }

    public Order(String varOrderId, String partnerOrderId, String price) {
        this.varOrderId = varOrderId;
        this.partnerOrderId = partnerOrderId;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVarOrderId() {
        return varOrderId;
    }

    public void setVarOrderId(String varOrderId) {
        this.varOrderId = varOrderId;
    }

    public String getPartnerOrderId() {
        return partnerOrderId;
    }

    public void setPartnerOrderId(String partnerOrderId) {
        this.partnerOrderId = partnerOrderId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
