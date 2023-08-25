package xyz.msprpayetonkawa.apirevendeur.retailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RetailerService {

    @Autowired
    RetailerRepository retailerRepository;

    @Value("${customer.password}")
    private String defaultPassword;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Retailer getRetailerByUid(String uid) {
        return retailerRepository.findByUid(uid);
    }

    public Optional<Retailer> getRetailerByEmail(String email) {
        return retailerRepository.findByEmail(email);
    }

    public Retailer addRetailer(Retailer retailer) {
        retailer.setUid(UUID.randomUUID().toString());
        retailer.setRole("ROLE_RETAILER");
        retailer.setPassword(passwordEncoder.encode(defaultPassword));
        return retailerRepository.save(retailer);
    }
}
