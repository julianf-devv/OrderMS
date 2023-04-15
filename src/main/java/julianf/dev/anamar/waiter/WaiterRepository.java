package julianf.dev.anamar.waiter;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaiterRepository extends JpaRepository<Waiter, Long> {


    @Transactional
    Waiter findByName(String name);


}
