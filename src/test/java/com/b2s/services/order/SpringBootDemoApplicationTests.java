package com.b2s.services.order;

import com.b2s.services.order.model.Order;
import com.b2s.services.order.model.OrderRepository;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
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
@SpringApplicationConfiguration(classes = {SpringBootDemoApplication.class, TestConfig.class})
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class SpringBootDemoApplicationTests {

    @Autowired
    private OrderRepository orderRepository;

    @Value("${local.server.port}")
    private int port;

    private static final String BASE_URL = "orders";

    @Before
    public void setup() {
        RestAssured.port = port;
    }

	@Test
	public void lookupSeededRecords() {
        List<Order> orders = orderRepository.findByVarOrderId("foo");

        assertThat(orders.size(), is(1));
        assertThat(orders.get(0).getPartnerOrderId(), is("bar"));
	}

    @Test
    public void getAllRecords() {
        when().
                get(BASE_URL)
        .then().
                statusCode(HttpStatus.SC_OK).
                body("varOrderId", hasItems("foo", "faz", "this"));
    }

    @Test
    public void insertAndDelete() {
        int orderId =
        given().
                contentType(ContentType.JSON).
                body(new Order("voID", "poID", "USD 1.23"))
        .when().
                post(BASE_URL)
        .then().
                statusCode(HttpStatus.SC_CREATED)
        .extract().
                path("id");

        when().
                delete("{baseUrl}/{orderId}", BASE_URL, orderId)
        .then().
                statusCode(HttpStatus.SC_NO_CONTENT);

        when().
                get("{baseUrl}/{orderId}", BASE_URL, orderId)
        .then().
                statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void insertInvalid() {
        given().
                contentType(ContentType.JSON).
                body(new Order("v", "poID", "USD 1.23")) //varOrderId too short (<3 characters)
        .when().
                post(BASE_URL)
        .then().
                statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void updateOnlyExistingOrder() {
        int nonExistentOrderId = -1;

        given()
                .contentType(ContentType.JSON)
                .body(new Order(nonExistentOrderId, "nextVOID", "nextPOID", "USD 1"))
        .when()
                .put("{baseUrl}/{orderId}", BASE_URL, nonExistentOrderId)
        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);

        int orderId =
                given().
                        contentType(ContentType.JSON).
                        body(new Order("voID", "poID", "USD 1.23"))
                .when().
                        post(BASE_URL)
                .then().
                        statusCode(HttpStatus.SC_CREATED)
                .extract().
                        path("id");

        given()
                .contentType(ContentType.JSON)
                .body(new Order(orderId, "nextVOID", "nextPOID", "USD 1"))
        .when()
                .put("{baseUrl}/{orderId}", BASE_URL, orderId)
        .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        when().
                get("{baseUrl}/{orderId}", BASE_URL, orderId)
        .then().
                statusCode(HttpStatus.SC_OK)
                .body("partnerOrderId", is("nextPOID"));
    }
}
