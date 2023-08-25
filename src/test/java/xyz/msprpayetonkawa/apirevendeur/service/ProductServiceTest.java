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
import xyz.msprpayetonkawa.apirevendeur.product.Product;
import xyz.msprpayetonkawa.apirevendeur.product.ProductRepository;
import xyz.msprpayetonkawa.apirevendeur.product.ProductService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    private final Product product1 = new Product(1L,"uid-key","Name","Description",11.11f,1);
    private final Product product2 = new Product(2L,"other-uid-key","Other-Name","Other-Description",22.22f,2);
    private final List<Product> products = Arrays.asList(product1, product2);

    @InjectMocks
    private ProductService productService;

    @Before
    public void setUp(){
        List<Product> products = Arrays.asList(product1, product2);


        Mockito.when(productRepository.findAll()).thenReturn(products);
        Mockito.when(productRepository.findByUid("uid-key")).thenReturn(product1);
        Mockito.when(productRepository.findByUid("no-uid-key")).thenReturn(null);
    }

    @Test
    public void testFindAllProducts(){
        Mockito.when(productRepository.findAll()).thenReturn(products);
        List<Product> result = productService.getProducts();
        assertEquals(products,result);
    }

    @Test
    public void testEqualsAndHash(){
        Product product = new Product(1L,"uid-key","Name","Description",11.11f, new Retailer(), 0, "image");
        Product productCopy = new Product(1L,"uid-key","Name","Description",11.11f, new Retailer(), 0, "image");
        assertEquals(product,productCopy);
        assertEquals(product.hashCode(), productCopy.hashCode());
        assertEquals(product.toString(), productCopy.toString());
    }

    @Test
    public void testNotEqualsAndNotHash(){
        Product product = new Product(1L,"uid-key","Name","Description",11.11f, new Retailer(), 0, "image");
        Product otherProduct = new Product(2L,"other-uid-key","Other-Name","Other-Description",22.22f, new Retailer(), 0, "image");
        assertNotEquals(product,otherProduct);
        assertNotEquals(product.hashCode(), otherProduct.hashCode());
        assertNotEquals(product.toString(), otherProduct.toString());
    }

    @Test
    public void testFindProductByUidThatExists(){
        String uid = "uid-key";
        Product result = productService.getProduct(uid);
        assertEquals(product1,result);
    }

    @Test
    public void testFindProductByUidThatDoesNotExists(){
        String uid = "no-uid-key";
        Mockito.when(productRepository.findByUid(uid)).thenReturn(null);
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
    }

    @Test
    public void testDeleteProduct(){

        String uid = "uid-key-to-delete";

        Product productToDelete = new Product(4L,uid,"Deleted","Deleted",99.99f, new Retailer(), 0, "image");

        Mockito.when(productRepository.findByUid(uid)).thenReturn(productToDelete);

        Product resultExists = productService.getProduct(uid);

        assertNotNull(resultExists);

        productService.deleteProduct(uid);

        Mockito.when(productRepository.findByUid(uid)).thenReturn(null);

        Product resultNotExists = productService.getProduct(uid);

        assertNull(resultNotExists);

    }
}
