package xyz.msprpayetonkawa.apirevendeur.controller;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.msprpayetonkawa.apirevendeur.client.Customer;
import xyz.msprpayetonkawa.apirevendeur.client.CustomerController;
import xyz.msprpayetonkawa.apirevendeur.client.CustomerService;
import xyz.msprpayetonkawa.apirevendeur.tools.SpringBeanMockUtil;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CustomerControllerTest {

    @Autowired
    CustomerController customerController;

    @Test
    public void testGetCustomers() {
        CustomerService customerServiceMock = SpringBeanMockUtil.mockFieldOnBean(customerController, CustomerService.class);
        doReturn(List.of(new Customer())).when(customerServiceMock).getCustomers();
        Response response = given().when().get("/api/customer");

        response.then().statusCode(200);
    }

    @Test
    public void testGetCustomerById() {
        CustomerService customerServiceMock = SpringBeanMockUtil.mockFieldOnBean(customerController, CustomerService.class);
        doReturn(new Customer()).when(customerServiceMock).getCustomerById("uid");
        Response response = given().when().pathParams("uid", "uid").get("/api/customer/{uid}");
        response.then().statusCode(200);
    }
}
