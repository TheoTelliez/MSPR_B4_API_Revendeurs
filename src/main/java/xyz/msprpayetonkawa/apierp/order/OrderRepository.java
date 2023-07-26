package xyz.msprpayetonkawa.apierp.order;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.xml.transform.Result;

public interface OrderRepository extends JpaRepository<Order,Long> {


    Order findByUid(String uid);
}
