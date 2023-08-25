package xyz.msprpayetonkawa.apirevendeur.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.msprpayetonkawa.apirevendeur.WebSecurityConfig;
import xyz.msprpayetonkawa.apirevendeur.product.Product;
import xyz.msprpayetonkawa.apirevendeur.retailer.Retailer;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Import(WebSecurityConfig.class)
public class RetailerTest {
    private final Product product1 = new Product(1L,"uid-key","Name","Description",11.11f,new Retailer(), 1, "image","bleu");
    private final Product product2 = new Product(2L,"other-uid-key","Other-Name","Other-Description",22.22f,new Retailer(), 2, "image","noir");

    private final Product product3 = new Product("New-Name","New-Description",33.33f,3);


    private final List<Product> ListProducts1 = Arrays.asList(product1,product3);
    private final List<Product> ListProducts2 = Arrays.asList(product1, product2);


    @Test
    public void testEqualsAndHash(){
        Retailer retailer = new Retailer(1L,"retailer-uid-key","Name","email@company.com","pass","Admin",ListProducts1);
        Retailer retailerCopy = new Retailer(1L,"retailer-uid-key","Name","email@company.com","pass","Admin",ListProducts1);
        assertEquals(retailer,retailerCopy);
        assertEquals(retailer.hashCode(), retailerCopy.hashCode());
        assertEquals(retailer.toString(), retailerCopy.toString());
    }

    @Test
    public void testNotEqualsAndNotHash(){
        Retailer retailer = new Retailer(1L,"retailer-uid-key","Name","email@company.com","pass","Admin",ListProducts1);
        Retailer otherRetailer2 = new Retailer(2L,"other-retailer-uid-key","Other Name","email@othercompany.com","otherpass","Admin",ListProducts2);
        assertNotEquals(retailer,otherRetailer2);
        assertNotEquals(retailer.hashCode(), otherRetailer2.hashCode());
        assertNotEquals(retailer.toString(), otherRetailer2.toString());
    }
}
