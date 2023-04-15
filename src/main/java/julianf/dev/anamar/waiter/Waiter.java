package julianf.dev.anamar.waiter;


import jakarta.persistence.*;

import julianf.dev.anamar.table.RestaurantTable;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
public class Waiter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private LocalDate joinDate;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assignedWaiter", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<RestaurantTable> assignedTables;



    @PrePersist
    private void prePersist() {
        this.joinDate = LocalDate.now();
    }
}
