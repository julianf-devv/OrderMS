package julianf.dev.anamar.order;

import julianf.dev.anamar.order.dto.AddItemToOrderDTO;
import julianf.dev.anamar.order.dto.OrderDTO;
import julianf.dev.anamar.order.dto.OrderNoItemsDTO;
import julianf.dev.anamar.order.dto.RemoveItemFromOrderDTO;

import java.util.List;

public interface OrderService {
    void save(Orders order);

    List<OrderNoItemsDTO> findAll();

    OrderDTO getOrder(Long id);

    void deleteOrder(Orders order);

    void addItemToOrder(Long id, List<AddItemToOrderDTO> items);

    void deleteItemFromOrder(Long orderID, List<RemoveItemFromOrderDTO> itemsToDelete);

}
