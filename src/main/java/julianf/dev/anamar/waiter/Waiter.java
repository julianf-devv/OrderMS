package julianf.dev.anamar.waiter;


import jakarta.persistence.*;
import julianf.dev.anamar.address.Address;
import julianf.dev.anamar.table.RestaurantTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Waiter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    @Column(name = "join_date")
    private LocalDate joinDate;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assignedWaiter", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Column(name = "assigned_tables")
    private List<RestaurantTable> assignedTables;

    @ElementCollection
    @AttributeOverrides({
            @AttributeOverride(name = "addressLine1", column = @Column(name = "house_number")),
            @AttributeOverride(name = "addressLine2", column = @Column(name = "street"))
    })
    private Set<Address> addresses = new HashSet<>();

    @PrePersist
    private void prePersist() {
        this.joinDate = LocalDate.now();
    }
}
