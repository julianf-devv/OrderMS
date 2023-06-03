package julianf.dev.anamar.mapper;


import julianf.dev.anamar.item.Item;
import julianf.dev.anamar.order.Orders;
import julianf.dev.anamar.order.dto.OrderDTO;
import julianf.dev.anamar.order.dto.OrderNoItemsDTO;
import julianf.dev.anamar.product.ProductDTO;
import julianf.dev.anamar.table.RestaurantTable;
import julianf.dev.anamar.table.dto.RestaurantTableDTO;
import julianf.dev.anamar.waiter.Waiter;
import julianf.dev.anamar.waiter.dto.WaiterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "orderItems", expression = "java(mapToProductDTOs(order.getItems()))")
    OrderDTO orderToOrderDTO(Orders order);

    @Mapping(source = "associatedTable", target = "table")
    OrderNoItemsDTO orderToOrderNoItemsDTO(Orders order);

    List<OrderNoItemsDTO> orderToOrderNoItemsDTO(List<Orders> order);
    @Mapping(source = "assignedWaiter", target = "waiter")
    @Mapping(source = "tableNumber", target = "id")
    RestaurantTableDTO restaurantTableToRestaurantTableDTO(RestaurantTable restaurantTable);

    WaiterDTO waiterToWaiterDTO(Waiter waiter);

    List<OrderDTO> ordersToOrderDTOs(List<Orders> orders);

    default Set<ProductDTO> mapToProductDTOs(Set<Item> items) {
        Map<String, Item> productMap = new HashMap<>();
        for (Item item : items) {
            String productName = item.getProduct().getName();
            Item existingItem = productMap.get(productName);
            if (existingItem != null) {
                existingItem.setAmount(existingItem.getAmount() + item.getAmount());
            } else {
                productMap.put(productName, item);
            }
        }
        return productMap.values().stream().map(item -> new ProductDTO(
                item.getProduct().getName(),
                item.getProduct().getDescription(),
                item.getProduct().getUnitPrice(),
                item.getProduct().getType(),
                item.getAmount(),
                item.getAmount() * item.getProduct().getUnitPrice()
        )).collect(Collectors.toSet());
    }

}

