package julianf.dev.anamar.waiter;

import java.util.List;

public interface WaiterService {

        Waiter save(Waiter waiter);

        Waiter getMeseroById(Long id);

        List<Waiter> findAll();
}
