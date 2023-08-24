package xyz.msprpayetonkawa.apirevendeur.client;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    @Transactional(rollbackOn = Exception.class)
    public Customer saveCustomer(Customer customer) {
        customer.setUid(String.valueOf(UUID.randomUUID()));
        return customerRepository.save(customer);
    }

}

