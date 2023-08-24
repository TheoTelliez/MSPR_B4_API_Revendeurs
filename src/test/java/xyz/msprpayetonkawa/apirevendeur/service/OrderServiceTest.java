package xyz.msprpayetonkawa.apirevendeur.service;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import xyz.msprpayetonkawa.apirevendeur.client.Customer;
import xyz.msprpayetonkawa.apirevendeur.order.Order;
import xyz.msprpayetonkawa.apirevendeur.product.Product;
import xyz.msprpayetonkawa.apirevendeur.product.ProductRepository;
import xyz.msprpayetonkawa.apirevendeur.product.ProductService;

import java.time.LocalDateTime;

public class OrderServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Before
    public void setUp(){
        Customer customer1 = new Customer(1L,"customer-uid-key","Last Name","First Name","first.last@email.com","Company",true);
        Product product1 = new Product(1L,"product-uid-key","Name","Description",11.11f,1);
        Order order = new Order();
        order.setUid("order");
        order.setId(1L);
        order.setCustomer(customer1);
        order.setDateCreated(LocalDateTime.now());
        //OrderProduct orderProduct = new OrderProduct(1L,);
        //List<OrderProduct> products = Arrays.asList(orderProduct);
    }

    /*@Test
    public void testFindAllProducts(){
        List<Product> result = productService.getProducts();
        assertEquals(2,result.size());
    }

    @Test
    public void testFindProductByUidThatExists(){
        String uid = "uid-key";
        Product result = productService.getProduct(uid);
        assertEquals(1L,result.getId());
    }

    @Test
    public void testFindProductByUidThatDoesNotExists(){
        String uid = "no-uid-key";
        Product result = productService.getProduct(uid);
        assertNull(result);
    }

    @Test
    public void testSaveProduct(){
        Product product3 = new Product("New-Name","New-Description",33.33f,3);

        Mockito.when(productRepository.save(any(Product.class))).thenReturn(product3);

        Product result = productService.saveProducts(product3);

        assertNotNull(result);
        assertEquals(product3,result);
    }*/
}
