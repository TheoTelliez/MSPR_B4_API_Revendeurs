package xyz.msprpayetonkawa.apirevendeur.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import xyz.msprpayetonkawa.apirevendeur.client.Customer;
import xyz.msprpayetonkawa.apirevendeur.client.CustomerRepository;
import xyz.msprpayetonkawa.apirevendeur.order.Order;
import xyz.msprpayetonkawa.apirevendeur.order.OrderRepository;
import xyz.msprpayetonkawa.apirevendeur.order.OrderService;
import xyz.msprpayetonkawa.apirevendeur.product.Product;
import xyz.msprpayetonkawa.apirevendeur.product.ProductRepository;
import xyz.msprpayetonkawa.apirevendeur.relations.OrderProduct;
import xyz.msprpayetonkawa.apirevendeur.relations.OrderProductKey;
import xyz.msprpayetonkawa.apirevendeur.retailer.Retailer;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    Customer customer1 = new Customer(1L,"customer-uid-key","Last Name","First Name","first.last@email.com","Company",true);
    Customer customer2 = new Customer(2L,"other-customer-uid-key","Other Last Name","First Name","first.last@email.com","Company",true);
    Product product1 = new Product(1L,"product-uid-key","Name","Description",11.11f,new Retailer(), 1, "image");
    Product product2 = new Product(2L,"other-product-uid-key","Other Name","Description",11.11f,new Retailer(), 1, "image");
    Order order1 = new Order();
    Order order2 = new Order();

    OrderProductKey orderProductKey1 = new OrderProductKey(order1.getId(),product1.getId());
    OrderProductKey orderProductKey2 = new OrderProductKey(order1.getId(),product2.getId());
    OrderProductKey orderProductKey3 = new OrderProductKey(order2.getId(),product1.getId());
    OrderProductKey orderProductKey4 = new OrderProductKey(order2.getId(),product2.getId());

    OrderProduct orderProduct1 = new OrderProduct(orderProductKey1,order1,product1,2);
    OrderProduct orderProduct2 = new OrderProduct(orderProductKey2,order1,product2,2);
    OrderProduct orderProduct3 = new OrderProduct(orderProductKey3,order2,product1,2);
    OrderProduct orderProduct4 = new OrderProduct(orderProductKey4,order2,product2,2);

    List<OrderProduct> orderProductList1 = Arrays.asList(orderProduct1,orderProduct2);
    List<OrderProduct> orderProductList2 = Arrays.asList(orderProduct3,orderProduct4);

    List<Order> orders = Arrays.asList(order1,order2);


    @Before
    public void setUp(){

        order1.setUid("order1-uid-key");
        order1.setId(1L);
        order1.setCustomer(customer1);
        order1.setDateCreated(LocalDateTime.now());

        order2.setUid("order2-uid-key");
        order2.setId(2L);
        order2.setCustomer(customer2);
        order2.setDateCreated(LocalDateTime.now());

    }

    @Test
    public void testFindAllOrders(){
        Mockito.when(orderRepository.findAll()).thenReturn(orders);
        List<Order> result = orderService.getOrders();
        assertEquals(orders,result);
    }

    @Test
    public void testEqualsAndHash(){
        Order order = new Order(1L,"order-uid-key",LocalDateTime.now(),orderProductList1,customer1);
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

    @Test
    public void testFindOrderByUidThatExists(){
        String uid = "order1-uid-key";
        Mockito.when(orderRepository.findByUid(uid)).thenReturn(order1);
        Order result = orderService.getOrder(uid);
        assertEquals(order1,result);
    }


    @Test
    public void testFindOrderByUidThatDoesNotExists(){
        String uid = "no-uid-key";
        Mockito.when(orderRepository.findByUid(uid)).thenReturn(null);
        Order result = orderService.getOrder(uid);
        assertNull(result);
    }

    @Test
    public void testSaveOrder() {
        Order orderToAdd = new Order(3L,"added-order-uid-key",LocalDateTime.now(),orderProductList2,customer1);

        Mockito.when(orderRepository.save(any(Order.class))).thenReturn(orderToAdd);
        Mockito.when(customerRepository.findByUid(customer1.getUid())).thenReturn(customer1);

        orderToAdd.getProductList().forEach(orderProduct -> {
            Mockito.when(productRepository.findByUid(orderProduct.getProduct().getUid())).thenReturn(orderProduct.getProduct());
        });

        Order result = orderService.saveOrders(orderToAdd);

        assertNotNull(result);
        assertEquals(orderToAdd, result);
    }

}
