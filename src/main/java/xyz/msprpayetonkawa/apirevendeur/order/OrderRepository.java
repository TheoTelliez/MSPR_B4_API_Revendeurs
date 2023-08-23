package xyz.msprpayetonkawa.apirevendeur.order;


import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {


    Order findByUid(String uid);
}
