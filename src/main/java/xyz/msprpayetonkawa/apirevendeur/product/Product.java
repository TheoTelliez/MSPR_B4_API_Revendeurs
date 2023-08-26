package xyz.msprpayetonkawa.apirevendeur.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import xyz.msprpayetonkawa.apirevendeur.retailer.Retailer;

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
    @ManyToOne
    @JsonIgnoreProperties("products")
    private Retailer retailer;
    private Integer stock;
    private String image;
    private String couleur;

    public Product(String name, String description, Float price, Integer stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }
}

