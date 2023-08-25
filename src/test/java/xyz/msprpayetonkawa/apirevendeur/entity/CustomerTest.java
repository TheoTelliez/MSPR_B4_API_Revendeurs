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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Import(WebSecurityConfig.class)
public class CustomerTest {

    @Test
    public void testEqualsAndHash(){
        Customer customer = new Customer(1L,"uid-key","Last Name","First Name","first.last@email.com","Company",true);
        Customer customerCopy = new Customer(1L,"uid-key","Last Name","First Name","first.last@email.com","Company",true);
        assertEquals(customer,customerCopy);
        assertEquals(customer.hashCode(), customerCopy.hashCode());
        assertEquals(customer.toString(), customerCopy.toString());
    }

    @Test
    public void testNotEqualsAndNotHash(){
        Customer customer = new Customer(1L,"uid-key","Last Name","First Name","first.last@email.com","Company",true);
        Customer otherCustomer = new Customer(2L,"other-uid-key","Other Last Name","Other First Name","otherfirst.otherlast@email.com","Other Company",false);
        assertNotEquals(customer,otherCustomer);
        assertNotEquals(customer.hashCode(), otherCustomer.hashCode());
        assertNotEquals(customer.toString(), otherCustomer.toString());
    }

}
