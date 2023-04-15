package julianf.dev.anamar.table;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class TableServiceImpl implements TableService {

    @Autowired
    private TableRepository tableRepository;

    @Override
    public RestaurantTable save(RestaurantTable table) {
        return null;
    }

    @Override
    public RestaurantTable getTableById(Short id) throws NoSuchElementException {
        log.info("TableServiceImpl.getTableById: " + tableRepository.findById(id).orElse(null));
        return tableRepository.findById(id).orElseThrow();
    }


    @Override
    public List<RestaurantTable> findAll() {
        return tableRepository.findAll();
    }
}
