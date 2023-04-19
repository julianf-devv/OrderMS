package julianf.dev.anamar.order;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import julianf.dev.anamar.address.Address;
import julianf.dev.anamar.item.Item;
import julianf.dev.anamar.item.dto.ItemDTO;
import julianf.dev.anamar.product.Product;
import julianf.dev.anamar.product.ProductDTO;
import julianf.dev.anamar.table.RestaurantTable;
import julianf.dev.anamar.waiter.Waiter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString
@Slf4j
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String customerName;
    private double total;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @Column(name = "finish_date")
    private LocalDateTime finishDate;
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
    @Column(name = "order_finished")
    private boolean orderFinished;
    private double tip;
    @ManyToOne
    @JoinColumn(name = "table_associated_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RestaurantTable associatedTable;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    private Set<Item> items = new HashSet<>();



    @PrePersist
    private void prePersist() {
        this.startDate = LocalDateTime.now();
        if(this.items != null) {
              this.total = items.stream().mapToDouble(item -> item.getProduct().getUnitPrice() * item.getAmount()).sum();
        }

    }
    @PreUpdate
    private void preUpdate() {
        this.lastUpdate = LocalDateTime.now();
        if(this.items != null) {
            log.info("Updating total");
            this.total = items.stream().mapToDouble(item -> item.getProduct().getUnitPrice() * item.getAmount()).sum();
        }
    }


}