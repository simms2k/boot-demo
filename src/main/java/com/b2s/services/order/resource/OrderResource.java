package com.b2s.services.order.resource;

import com.b2s.services.order.model.Order;
import com.b2s.services.order.model.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.*;
import java.util.Map;

@Service
@Path("/demo-orders")
@Consumes("application/json")
@Produces("application/json")
public class OrderResource {

    @Autowired
    private OrderRepository orderRepository;

    @GET
    public Iterable<Order> getAll() {
        return orderRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Order get(@PathParam("id") long id) {
        Order order = orderRepository.findOne(id);
        if(null == order) {
            throw new ClientErrorException(NOT_FOUND);
        } else {
            return order;
        }
    }

    @POST
    public Response create(Map<String,String> map) {
        final Order order = orderRepository.save(new Order(
                map.get("varOrderId"),
                map.get("partnerOrderId"),
                map.get("price")
        ));
        return Response.status(CREATED).entity(order).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") long id) {
        orderRepository.delete(id);
        return Response.status(NO_CONTENT).build();
    }
}
