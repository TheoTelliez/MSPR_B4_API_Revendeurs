package xyz.msprpayetonkawa.apirevendeur.order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.msprpayetonkawa.apirevendeur.client.CustomerRepository;
import xyz.msprpayetonkawa.apirevendeur.product.Product;
import xyz.msprpayetonkawa.apirevendeur.product.ProductRepository;
import xyz.msprpayetonkawa.apirevendeur.relations.OrderProduct;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
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
