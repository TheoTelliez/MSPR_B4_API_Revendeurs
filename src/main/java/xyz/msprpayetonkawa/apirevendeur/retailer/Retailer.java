package xyz.msprpayetonkawa.apirevendeur.retailer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import xyz.msprpayetonkawa.apirevendeur.product.Product;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name="retailer")
public class Retailer {
    @Id
    @JsonIgnore
    @GeneratedValue
    private Long id;
    private String uid;
    private String name;
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String role;
    @OneToMany(mappedBy = "retailer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("retailer")
    private List<Product> products;

}
