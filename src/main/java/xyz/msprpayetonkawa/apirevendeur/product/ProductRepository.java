package xyz.msprpayetonkawa.apirevendeur.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByUid(String uid);
    void deleteByUid(String uid);

}
