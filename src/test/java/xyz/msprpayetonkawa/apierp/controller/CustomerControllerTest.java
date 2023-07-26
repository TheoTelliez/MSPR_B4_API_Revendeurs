package xyz.msprpayetonkawa.apierp.controller;

import io.restassured.response.Response;
import mockit.Mocked;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.msprpayetonkawa.apierp.client.Customer;
import xyz.msprpayetonkawa.apierp.client.CustomerController;
import xyz.msprpayetonkawa.apierp.client.CustomerService;
import xyz.msprpayetonkawa.apierp.tools.SpringBeanMockUtil;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CustomerControllerTest {

    @Autowired
    CustomerController customerController;

    @Mocked
    CustomerService customerService;

    @Test
    public void testGetCustomers() {
        CustomerService customerServiceMock = SpringBeanMockUtil.mockFieldOnBean(customerController, CustomerService.class);
        doReturn(List.of(new Customer())).when(customerServiceMock).getCustomers();
        Response response = given().when().get("/api/customer");

        response.then().statusCode(200);
    }
}
