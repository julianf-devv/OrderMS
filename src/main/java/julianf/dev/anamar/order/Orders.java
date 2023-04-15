package julianf.dev.anamar.order;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import julianf.dev.anamar.item.Item;
import julianf.dev.anamar.table.RestaurantTable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double total;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
    private LocalDateTime lastUpdate;
    private String trace;
    private boolean orderFinished;
    private double tip;
    @ManyToOne
    @JoinColumn(name = "table_associated_id")
    private RestaurantTable associatedTable;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @PrePersist
    private void prePersist() {
        this.trace = UUID.randomUUID().toString().replace("-", ":");
        this.startDate = LocalDateTime.now();
    }
//
//    @PreUpdate
//    private void preUpdate() {
//        this.total = items.stream().mapToDouble(Item::getUnitPrice).sum();
//    }

}
