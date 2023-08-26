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
import xyz.msprpayetonkawa.apirevendeur.retailer.Retailer;
import xyz.msprpayetonkawa.apirevendeur.retailer.RetailerRepository;

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

    @Mock
    private RetailerRepository retailerRepository;

    private final Product product1 = new Product(1L,"uid-key","Name","Description",11.11f,new Retailer(), 1, "image","bleu");
    private final Product product2 = new Product(2L,"other-uid-key","Other-Name","Other-Description",22.22f,new Retailer(), 2, "image","noir");
    private final List<Product> products = Arrays.asList(product1, product2);

    private final Retailer retailer1 = new Retailer(1L,"retailer-uid-key","Name","email@company.com","pass","Admin",products);

    @InjectMocks
    private ProductService productService;

    @Before
    public void setUp(){
        product1.setRetailer(retailer1);
    }

    @Test
    public void testFindAllProducts(){
        Mockito.when(productRepository.findAll()).thenReturn(products);
        List<Product> result = productService.getProducts();
        assertEquals(products,result);
    }

    @Test
    public void testFindProductByUidThatExists(){
        String uid = "uid-key";
        Mockito.when(productRepository.findByUid(uid)).thenReturn(product1);
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

        product3.setRetailer(retailer1);

        Mockito.when(productRepository.save(any(Product.class))).thenReturn(product3);

        Product result = productService.saveProducts(product3);

        assertNotNull(result);
        assertEquals(product3,result);
    }

    @Test
    public void testDeleteProduct(){

        String uid = "uid-key-to-delete";

        Product productToDelete = new Product(4L,uid,"Deleted","Deleted",99.99f, new Retailer(), 0, "image","vert");

        Mockito.when(productRepository.findByUid(uid)).thenReturn(productToDelete);

        Product resultExists = productService.getProduct(uid);

        assertNotNull(resultExists);

        productService.deleteProduct(uid);

        Mockito.when(productRepository.findByUid(uid)).thenReturn(null);

        Product resultNotExists = productService.getProduct(uid);

        assertNull(resultNotExists);

    }

    @Test
    public void testFindProductsByRetailerUid(){
        String uid = "retailer-uid-key";

        Mockito.when(productRepository.findProductsByRetailerUid(uid)).thenReturn(products);

        List<Product> result = productService.getProductsByRetailer(uid);

        assertEquals(products,result);

    }
}
