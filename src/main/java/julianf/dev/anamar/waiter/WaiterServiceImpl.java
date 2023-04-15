package julianf.dev.anamar.waiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaiterServiceImpl implements WaiterService {

    @Autowired
    private WaiterRepository waiterRepository;

    public Waiter save(Waiter waiter) {
        return waiterRepository.save(waiter);
    }

    public List<Waiter> findAll() {
        return waiterRepository.findAll();
    }

    public Waiter getMeseroById(Long id) {
        return waiterRepository.findById(id).orElse(null);
    }


}
