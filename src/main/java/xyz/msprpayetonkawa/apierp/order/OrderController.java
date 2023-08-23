package xyz.msprpayetonkawa.apierp.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping()
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> toReturn = orderService.getOrders();
        return new ResponseEntity<>(toReturn, HttpStatus.OK);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Order> getOrderByUid(@PathVariable("uid") String uid) {
        Order toReturn = orderService.getOrder(uid);
        return new ResponseEntity<>(toReturn, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Order> addOrder(@RequestBody Order order){
        Order toReturn = orderService.saveOrders(order);
        return new ResponseEntity<>(toReturn, HttpStatus.CREATED);
    }

}
