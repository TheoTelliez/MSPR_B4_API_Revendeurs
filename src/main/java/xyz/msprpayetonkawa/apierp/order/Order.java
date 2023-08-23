package xyz.msprpayetonkawa.apierp.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import xyz.msprpayetonkawa.apierp.client.Customer;
import xyz.msprpayetonkawa.apierp.relations.OrderProduct;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private String uid;
    private LocalDateTime dateCreated;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("order")
    private List<OrderProduct> productList;
    @ManyToOne()
    private Customer customer;

}
