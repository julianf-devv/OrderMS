package julianf.dev.anamar.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import julianf.dev.anamar.product.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Entity
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;
    private int amount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private Product product;

    @Column(name = "notes")
    private String notes;

    //Based on the notes if something extra or something its removed for the product it will adjust the price
    private double priceAdjustment;

}
