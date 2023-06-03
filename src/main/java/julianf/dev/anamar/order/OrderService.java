package julianf.dev.anamar.order;

import julianf.dev.anamar.order.dto.AddItemToOrderDTO;
import julianf.dev.anamar.order.dto.OrderDTO;
import julianf.dev.anamar.order.dto.OrderNoItemsDTO;
import julianf.dev.anamar.order.dto.RemoveItemFromOrderDTO;

import java.util.List;

public interface OrderService {
    void save(Orders order);

    /**
     * Returns a list of all orders without item details.
     *
     * @return a list of all orders
     * @author Julian Fernandez
     */
    List<OrderNoItemsDTO> findAll();


    /**
     * Returns an order with all its items.
     *
     * @return a list of all orders
     * @author Julian Fernandez
     */
    OrderDTO getOrder(Long id);

    void deleteOrder(Orders order);

    /**
     * Returns a list of all orders.
     *
     * @param items  a list of items to add to the order with their respective quantities and productId
     * @param id    the id of the order to add the items to
     * @author Julian Fernandez
     */
    void addItemToOrder(Long id, List<AddItemToOrderDTO> items);

    /**
        * Deletes a list of items from an order.
        * @param orderID the id of the order to delete the items from
        * @param itemsToDelete a list of items to delete from the order with their respective quantities and productId
        * @author julian.hernandez
     */
    void deleteItemFromOrder(Long orderID, List<RemoveItemFromOrderDTO> itemsToDelete);

}
