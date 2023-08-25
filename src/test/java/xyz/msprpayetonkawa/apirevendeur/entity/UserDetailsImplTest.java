package xyz.msprpayetonkawa.apirevendeur.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.msprpayetonkawa.apirevendeur.product.Product;
import xyz.msprpayetonkawa.apirevendeur.retailer.Retailer;
import xyz.msprpayetonkawa.apirevendeur.security.services.UserDetailsImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserDetailsImplTest {

    private UserDetailsImpl userDetailsImpl;

    private final Product product1 = new Product(1L,"uid-key","Name","Description",11.11f,new Retailer(), 1, "image","bleu");
    private final Product product2 = new Product(2L,"other-uid-key","Other-Name","Other-Description",22.22f,new Retailer(), 2, "image","noir");

    private final List<Product> ListProducts1 = Arrays.asList(product1,product2);

    private final Retailer retailer1 = new Retailer(1L,"retailer-uid-key","Name","email@company.com","pass","Admin",ListProducts1);

    private final List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(retailer1.getRole()));

    @Before
    public void setUp() {
        userDetailsImpl = new UserDetailsImpl(1L,"username","email","password",authorities);
    }


    @Test
    public void testBuild(){
        UserDetailsImpl buildedUser = UserDetailsImpl.build(retailer1);
        UserDetailsImpl userWithoutBuild = new UserDetailsImpl(1l,"Name","email@company.com","pass",Collections.singletonList(new SimpleGrantedAuthority("Admin")));
        assertEquals(buildedUser,userWithoutBuild);
    }

    @Test
    public void testGetAuthorities(){
        List<GrantedAuthority> authoritiesOfRetailer = Collections.singletonList(new SimpleGrantedAuthority("Admin"));
        assertEquals(authoritiesOfRetailer,userDetailsImpl.getAuthorities());
    }

    @Test
    public void testGetPassword(){
        String password = "password";
        assertEquals(password,userDetailsImpl.getPassword());
    }

    @Test
    public void testGetUsername(){
        String username = "username";
        assertEquals(username,userDetailsImpl.getUsername());
    }
    @Test
    public void testIsAccountNonExpired(){
        assertTrue(userDetailsImpl.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked(){
        assertTrue(userDetailsImpl.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired(){
        assertTrue(userDetailsImpl.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled(){
        assertTrue(userDetailsImpl.isEnabled());
    }
}
