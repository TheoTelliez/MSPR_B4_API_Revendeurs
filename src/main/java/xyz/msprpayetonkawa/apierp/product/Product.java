package xyz.msprpayetonkawa.apierp.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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

    public Product(String name, String description, Float price, Integer stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }
}

