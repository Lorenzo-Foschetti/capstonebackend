package lorenzofoschetti.capstoneproject.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Order {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "bottles_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "bottle_id"))
    private List<Bottle> bottles = new ArrayList<>();
    @OneToOne
    @JsonBackReference
    private User user;

    private double totalPrice;


}
