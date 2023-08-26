package xyz.msprpayetonkawa.apirevendeur.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.msprpayetonkawa.apirevendeur.relations.OrderProduct;
import xyz.msprpayetonkawa.apirevendeur.retailer.Retailer;
import xyz.msprpayetonkawa.apirevendeur.retailer.RetailerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RetailerRepository retailerRepository;

    public Product getProduct(final String uid) {
        return productRepository.findByUid(uid);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(final String uid) {
        Product deletedProduct = productRepository.findByUid(uid);
        productRepository.delete(deletedProduct);
    }

    @Transactional(rollbackOn = Exception.class)
    public Product saveProducts(Product product) {
        product.setUid(String.valueOf(UUID.randomUUID()));

        Optional<Retailer> retailerToAdd = retailerRepository.findByUid(product.getRetailer().getUid());

        retailerToAdd.ifPresent(product::setRetailer);

        return productRepository.save(product);
    }

    public List<Product> getProductsByRetailer(String uid){
        return productRepository.findProductsByRetailerUid(uid);
    }


}