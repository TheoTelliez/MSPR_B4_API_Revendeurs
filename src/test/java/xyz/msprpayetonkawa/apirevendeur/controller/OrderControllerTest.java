package xyz.msprpayetonkawa.apirevendeur.controller;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.msprpayetonkawa.apirevendeur.WebSecurityConfig;
import xyz.msprpayetonkawa.apirevendeur.order.Order;
import xyz.msprpayetonkawa.apirevendeur.order.OrderController;
import xyz.msprpayetonkawa.apirevendeur.order.OrderService;
import xyz.msprpayetonkawa.apirevendeur.tools.SpringBeanMockUtil;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@DirtiesContext
@Import(WebSecurityConfig.class)
public class OrderControllerTest {
    @Autowired
    OrderController orderController;

    @Test
    public void testGetOrders() {
        OrderService orderServiceMock = SpringBeanMockUtil.mockFieldOnBean(orderController, OrderService.class);
        doReturn(List.of(new Order())).when(orderServiceMock).getOrders();
        Response response = given().when().get("/api/order");

        response.then().statusCode(200);
    }

    @Test
    public void testGetOrderById() {
        OrderService orderServiceMock = SpringBeanMockUtil.mockFieldOnBean(orderController, OrderService.class);
        doReturn(new Order()).when(orderServiceMock).getOrder("uid");
        Response response = given().when().pathParams("uid", "uid").get("/api/order/{uid}");
        response.then().statusCode(200);
    }

    @Test
    public void testAddOrder() {
        Order order = new Order();
        OrderService orderServiceMock = SpringBeanMockUtil.mockFieldOnBean(orderController, OrderService.class);
        doReturn(new Order()).when(orderServiceMock).saveOrders(Mockito.any(Order.class));
        Response response = given().contentType("application/json").when().body(order).post("/api/order");
        response.then().statusCode(201);
    }
}
