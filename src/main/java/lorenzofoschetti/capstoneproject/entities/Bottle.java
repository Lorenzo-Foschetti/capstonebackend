package lorenzofoschetti.capstoneproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lorenzofoschetti.capstoneproject.enums.BottleCategory;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bottles")
@Getter
@Setter
@ToString
@NoArgsConstructor

public class Bottle {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    private String collection;
    private int productionYear;
    private double price;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String urlImage;
    @ManyToMany(mappedBy = "bottles", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Order> orders;
    @Enumerated(EnumType.STRING)
    private BottleCategory bottleCategory;


    public Bottle(BottleCategory bottleCategory, String description, double price, int productionYear, String name, String collection) {
        this.bottleCategory = bottleCategory;

        this.urlImage = urlImage;
        this.description = description;
        this.productionYear = productionYear;
        this.name = name;
        this.price = price;
        this.collection = collection;
    }
}
