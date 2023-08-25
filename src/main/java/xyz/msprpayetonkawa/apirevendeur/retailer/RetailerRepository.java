package xyz.msprpayetonkawa.apirevendeur.retailer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RetailerRepository extends JpaRepository<Retailer,Long> {
    Optional<Retailer> findByUid(String uid);
    Optional<Retailer> findByEmail(String email);
}
