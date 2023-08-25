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
import xyz.msprpayetonkawa.apirevendeur.client.CustomerService;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Before
    public void setUp(){
        Customer customer1 = new Customer(1L,"uid-key","Last Name","First Name","first.last@email.com","Company",true);
        Customer customer2 = new Customer(2L,"other-uid-key","Other Last Name","Other First Name","otherfirst.otherlast@email.com","Other Company",false);
        List<Customer> customers = Arrays.asList(customer1, customer2);


        Mockito.when(customerRepository.findAll()).thenReturn(customers);
        Mockito.when(customerRepository.findByUid("uid-key")).thenReturn(customer1);
        Mockito.when(customerRepository.findByUid("no-uid-key")).thenReturn(null);
    }


    @Test
    public void testFindAllCustomers(){
        List<Customer> result = customerService.getCustomers();
        assertEquals(2,result.size());
    }

    @Test
    public void testFindCustomerByUidThatExists(){
        String uid = "uid-key";
        Customer result = customerService.getCustomerById(uid);
        assertEquals(1L,result.getId());
    }

    @Test
    public void testFindCustomerByUidThatDoesNotExists(){
        String uid = "no-uid-key";
        Customer result = customerService.getCustomerById(uid);
        assertNull(result);
    }
}
