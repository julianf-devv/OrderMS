package julianf.dev.anamar.item;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import julianf.dev.anamar.order.Orders;
import julianf.dev.anamar.product.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Entity
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties("orders")
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private short id;
    private int amount;
    //Sku
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;
    @ManyToOne(cascade = CascadeType.ALL)
    private Orders order;
    private Double total;

}
