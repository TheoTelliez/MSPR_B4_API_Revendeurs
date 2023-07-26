package xyz.msprpayetonkawa.apierp.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import xyz.msprpayetonkawa.apierp.relations.OrderProduct;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private String uid;
    private String name;
    private String description;
    private Float price;
    private Integer stock;
}

