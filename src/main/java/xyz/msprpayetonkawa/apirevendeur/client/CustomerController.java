package xyz.msprpayetonkawa.apirevendeur.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllProducts() {
        List<Customer> toReturn = customerService.getCustomers();
        return new ResponseEntity<>(toReturn, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> addClient(@RequestBody Customer customer) {
        Customer toReturn = customerService.saveCustomer(customer);
        return new ResponseEntity<>(toReturn, HttpStatus.CREATED);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Customer> getProductById(@PathVariable("uid") String uid) {
        Customer toReturn = customerService.getCustomerById(uid);
        return new ResponseEntity<>(toReturn, HttpStatus.OK);
    }

}
