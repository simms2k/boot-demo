package com.b2s.services.travel;

import com.b2s.services.order.SpringBootDemoApplication;
import com.b2s.services.order.model.Order;
import com.b2s.services.order.model.OrderRepository;
import com.jayway.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootDemoApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class SpringBootDemoApplicationTests {

    @Autowired
    private OrderRepository orderRepository;

    @Value("${local.server.port}")
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

	@Test
	public void lookupSeededRecords() {
        List<Order> orders = orderRepository.findByVarOrderId("foo");
        assertEquals(1, orders.size());
        assertEquals("bar", orders.get(0).getPartnerOrderId());
	}

    @Test
    public void getAllRecords() {
        when().
                get("/orders")
        .then().
                statusCode(HttpStatus.SC_OK).
                body("varOrderId", hasItems("foo", "faz", "this"));
    }

}
