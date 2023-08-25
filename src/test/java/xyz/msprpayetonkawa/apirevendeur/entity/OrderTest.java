package xyz.msprpayetonkawa.apirevendeur.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.msprpayetonkawa.apirevendeur.WebSecurityConfig;
import xyz.msprpayetonkawa.apirevendeur.client.Customer;
import xyz.msprpayetonkawa.apirevendeur.order.Order;
import xyz.msprpayetonkawa.apirevendeur.product.Product;
import xyz.msprpayetonkawa.apirevendeur.relations.OrderProduct;
import xyz.msprpayetonkawa.apirevendeur.relations.OrderProductKey;
import xyz.msprpayetonkawa.apirevendeur.retailer.Retailer;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Import(WebSecurityConfig.class)
public class OrderTest {

    private final Customer customer1 = new Customer(1L,"customer-uid-key","Last Name","First Name","first.last@email.com","Company",true);
    private final Customer customer2 = new Customer(2L,"other-customer-uid-key","Other Last Name","First Name","first.last@email.com","Company",true);
    private final Product product1 = new Product(1L,"product-uid-key","Name","Description",11.11f,new Retailer(), 1, "image","bleu");
    private final Product product2 = new Product(2L,"other-product-uid-key","Other Name","Description",11.11f,new Retailer(), 1, "image","noir");
    private final Order order1 = new Order();
    private final Order order2 = new Order();

    private final OrderProductKey orderProductKey1 = new OrderProductKey(order1.getId(),product1.getId());
    private final OrderProductKey orderProductKey2 = new OrderProductKey(order1.getId(),product2.getId());
    private final OrderProductKey orderProductKey3 = new OrderProductKey(order2.getId(),product1.getId());
    private final OrderProductKey orderProductKey4 = new OrderProductKey(order2.getId(),product2.getId());

    private final OrderProduct orderProduct1 = new OrderProduct(orderProductKey1,order1,product1,2);
    private final OrderProduct orderProduct2 = new OrderProduct(orderProductKey2,order1,product2,2);
    private final OrderProduct orderProduct3 = new OrderProduct(orderProductKey3,order2,product1,2);
    private final OrderProduct orderProduct4 = new OrderProduct(orderProductKey4,order2,product2,2);

    private final List<OrderProduct> orderProductList1 = Arrays.asList(orderProduct1,orderProduct2);
    private final List<OrderProduct> orderProductList2 = Arrays.asList(orderProduct3,orderProduct4);


    @Test
    public void testEqualsAndHash(){
        Order order = new Order(1L,"order-uid-key", LocalDateTime.now(),orderProductList1,customer1);
        Order orderCopy = new Order(1L,"order-uid-key",LocalDateTime.now(),orderProductList1,customer1);
        assertEquals(order,orderCopy);
        assertEquals(order.hashCode(), orderCopy.hashCode());
        assertEquals(order.toString(), orderCopy.toString());
    }

    @Test
    public void testNotEqualsAndNotHash(){
        Order order = new Order(1L,"order-uid-key",LocalDateTime.now(),orderProductList1,customer1);
        Order otherOrder = new Order(2L,"another-order-uid-key",LocalDateTime.now(),orderProductList2,customer2);
        assertNotEquals(order,otherOrder);
        assertNotEquals(order.hashCode(), otherOrder.hashCode());
        assertNotEquals(order.toString(), otherOrder.toString());
    }
}
