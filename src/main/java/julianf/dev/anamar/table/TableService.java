package julianf.dev.anamar.table;

import java.util.List;

public interface TableService {

    RestaurantTable save(RestaurantTable table);

    RestaurantTable getTableById(Short id);

    List<RestaurantTable> findAll();

}
