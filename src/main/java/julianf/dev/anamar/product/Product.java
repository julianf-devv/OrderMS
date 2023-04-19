package julianf.dev.anamar.product;

import jakarta.persistence.*;
import julianf.dev.anamar.item.Item;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Short id;

    private String name;

    private String description;

    private double unitPrice;

    private Integer stock;

    private LocalDateTime addedDate;

    private ProductType type;


    @PrePersist
    private void prePersist() {
        this.addedDate = LocalDateTime.now();
    }
}
