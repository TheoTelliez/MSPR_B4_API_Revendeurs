package xyz.msprpayetonkawa.apirevendeur.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomerById(final String uid) {
        return customerRepository.findByUid(uid);
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

}

