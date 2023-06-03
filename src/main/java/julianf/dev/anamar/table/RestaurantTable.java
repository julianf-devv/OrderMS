package julianf.dev.anamar.table;


import jakarta.persistence.*;
import julianf.dev.anamar.waiter.Waiter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@ToString
@Getter @Setter
@jakarta.persistence.Table(name = "restaurant_table")
public class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short tableNumber;

    @Column(name = "occupied")
    private boolean occupied;

    private short capacity;

    @ManyToOne
    @JoinColumn(name = "attending_waiter_id")
    @ToString.Exclude
    private Waiter assignedWaiter;


    /**
     */
    public RestaurantTable() {
        // TODO document why this constructor is empty
    }
}