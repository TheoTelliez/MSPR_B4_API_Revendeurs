package xyz.msprpayetonkawa.apirevendeur.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.msprpayetonkawa.apirevendeur.product.Product;
import xyz.msprpayetonkawa.apirevendeur.relations.OrderProductKey;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class OrderProductKeyTest {

    private OrderProductKey orderProductKey;

    @Before
    public void setUp() {
        orderProductKey = new OrderProductKey(1L, 2L);
    }

    @Test
    public void testOrderProductKey() {
        assertEquals(1L, orderProductKey.getOrderId());
        assertEquals(2L, orderProductKey.getProductId());
    }

    @Test
    public void testSetOrderId() {
        orderProductKey.setOrderId(3L);
        assertEquals(3L, orderProductKey.getOrderId());
    }

    @Test
    public void testSetProductId() {
        orderProductKey.setProductId(4L);
        assertEquals(4L, orderProductKey.getProductId());
    }

    @Test
    public void testEqualsAndHashCode() {
        OrderProductKey anotherKey = new OrderProductKey(1L, 2L);
        assertEquals(orderProductKey, anotherKey);
        assertEquals(orderProductKey.hashCode(), anotherKey.hashCode());

        OrderProductKey differentKey = new OrderProductKey(2L, 3L);
        assertNotEquals(orderProductKey, differentKey);
        assertNotEquals(orderProductKey.hashCode(), differentKey.hashCode());

        OrderProductKey anotherAgainKey = new OrderProductKey(1L, 4L);

        Product product = new Product();
        assertFalse(orderProductKey.equals(product));

        assertFalse(orderProductKey.equals(null));

        assertEquals(orderProductKey, orderProductKey);

        assertNotEquals(orderProductKey, anotherAgainKey);


    }

}