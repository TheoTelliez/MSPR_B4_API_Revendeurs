package xyz.msprpayetonkawa.apirevendeur.product;

import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Data
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product getProduct(final String uid) {
        return productRepository.findByUid(uid);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void deleteProducts(final Long id) {
        productRepository.deleteById(id);
    }

    @Transactional(rollbackOn = Exception.class)
    public Product saveProducts(Product product) {
        product.setUid(String.valueOf(UUID.randomUUID()));
        return productRepository.save(product);
    }



}