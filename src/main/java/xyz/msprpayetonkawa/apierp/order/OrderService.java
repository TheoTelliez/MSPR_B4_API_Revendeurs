package xyz.msprpayetonkawa.apierp.order;

import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.msprpayetonkawa.apierp.client.CustomerRepository;
import xyz.msprpayetonkawa.apierp.product.Product;
import xyz.msprpayetonkawa.apierp.product.ProductRepository;
import xyz.msprpayetonkawa.apierp.relations.OrderProduct;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Order getOrder(final String uid) {
        return orderRepository.findByUid(uid);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }



    @Transactional(rollbackOn = Exception.class)
    public Order saveOrders(Order order) {
        order.setUid(String.valueOf(UUID.randomUUID()));
        order.setDateCreated(LocalDateTime.now());
        order.setCustomer(customerRepository.findByUid(order.getCustomer().getUid()));
        ArrayList<OrderProduct> products = new ArrayList<>();
        order.getProductList().forEach(orderProduct -> {
            Product productToAdd = productRepository.findByUid(orderProduct.getProduct().getUid());
            OrderProduct orderProductToAdd = new OrderProduct(order,productToAdd,25);
            orderProductToAdd.setQuantityOrdered(orderProduct.getQuantityOrdered());
            products.add(orderProductToAdd);
        });
        order.setProductList(products);
        return orderRepository.save(order);
    }


}