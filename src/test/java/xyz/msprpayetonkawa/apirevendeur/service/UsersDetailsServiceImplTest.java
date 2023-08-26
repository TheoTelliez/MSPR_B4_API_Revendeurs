package xyz.msprpayetonkawa.apirevendeur.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import xyz.msprpayetonkawa.apirevendeur.product.Product;
import xyz.msprpayetonkawa.apirevendeur.retailer.Retailer;
import xyz.msprpayetonkawa.apirevendeur.retailer.RetailerRepository;
import xyz.msprpayetonkawa.apirevendeur.security.services.UserDetailsImpl;
import xyz.msprpayetonkawa.apirevendeur.security.services.UserDetailsServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UsersDetailsServiceImplTest {

    @Mock
    private RetailerRepository retailerRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private final Product product1 = new Product(1L,"uid-key","Name","Description",11.11f,new Retailer(), 1, "image","bleu");
    private final Product product2 = new Product(2L,"other-uid-key","Other-Name","Other-Description",22.22f,new Retailer(), 2, "image","noir");


    private final List<Product> ListProducts1 = Arrays.asList(product1,product2);

    private final Retailer retailer1 = new Retailer(1L,"retailer-uid-key","Name","email@company.com","pass","Admin",ListProducts1);

    private final UserDetails expectedUser = UserDetailsImpl.build(retailer1);


    @Test
    public void testLoadUserByUsername(){
        String email = "email@company.com";
        Mockito.when(retailerRepository.findByEmail(email)).thenReturn(Optional.of(retailer1));
        UserDetails user = userDetailsService.loadUserByUsername(email);
        assertEquals(expectedUser,user);
    }

}
