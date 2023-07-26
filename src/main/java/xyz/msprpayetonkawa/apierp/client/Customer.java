package xyz.msprpayetonkawa.apierp.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private String uid;
    private String nom;
    private String prenom;
    private String email;
    private String company;
    private boolean prospect;

}
