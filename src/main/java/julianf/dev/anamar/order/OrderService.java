package julianf.dev.anamar.order;

import jakarta.persistence.criteria.Order;
import julianf.dev.anamar.order.dto.AddItemToOrderDTO;
import julianf.dev.anamar.order.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    void save(Orders order);

    List<OrderDTO> findAll();

    List<OrderDTO> findAllNotFinished();

    OrderDTO getOrder(Long id);

    void deleteOrder(Orders order);

    Orders addItemToOrder(Long id, List<AddItemToOrderDTO> items);

    void deleteItemFromOrder(Long id, Long idItem);

}
