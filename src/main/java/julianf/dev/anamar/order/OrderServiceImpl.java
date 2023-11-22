package julianf.dev.anamar.order;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import julianf.dev.anamar.item.Item;
import julianf.dev.anamar.item.ItemRepository;
import julianf.dev.anamar.mapper.OrderMapper;
import julianf.dev.anamar.order.dto.AddItemToOrderDTO;
import julianf.dev.anamar.order.dto.OrderDTO;
import julianf.dev.anamar.order.dto.OrderNoItemsDTO;
import julianf.dev.anamar.order.dto.RemoveItemFromOrderDTO;
import julianf.dev.anamar.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
  private final ProductRepository productRepository;

  private final OrderRepository orderRepository;

  private final OrderMapper orderMapper;

  private final ItemRepository itemRepository;

  @Autowired
  public OrderServiceImpl(
      OrderRepository orderRepository,
      OrderMapper orderMapper,
      ProductRepository productRepository,
      ItemRepository itemRepository) {
    this.orderRepository = orderRepository;
    this.orderMapper = orderMapper;
    this.productRepository = productRepository;
    this.itemRepository = itemRepository;
  }

  @Override
  public void save(Orders order) {}

  @Override
  public List<OrderNoItemsDTO> findAll() {
    log.info("getOrders");
    return orderMapper.orderToOrderNoItemsDTO(orderRepository.findAll());
  }

  @Override
  public OrderDTO getOrder(Long id) {
    return orderMapper.orderToOrderDTO(orderRepository.findById(id).orElseThrow());
  }

  @Override
  public void deleteOrder(Orders order) {
    orderRepository.delete(order);
  }

  @Override
  @Transactional
  public void addItemToOrder(Long id, List<AddItemToOrderDTO> items) {
    var order = orderRepository.findById(id).orElseThrow();
    var currentItemList = order.getItems();
    items.forEach(
        itemToAdd ->
            currentItemList.stream()
                .filter(
                    existingItem ->
                        Objects.equals(itemToAdd.productId(), existingItem.getProduct().getId()))
                .findFirst()
                .ifPresentOrElse(
                    existingItem -> setExistingItemAmount(existingItem, itemToAdd.amount()),
                    () -> addNewItem(currentItemList, itemToAdd)));
    updateOrderTotal(order);
    orderRepository.save(order);
  }

  private void setExistingItemAmount(Item existingItem, int amountToAdd) {
    existingItem.setAmount(existingItem.getAmount() + amountToAdd);
  }

  private void addNewItem(Set<Item> itemList, AddItemToOrderDTO itemToAdd) {
    Item newItem = new Item();
    newItem.setProduct(productRepository.findById(itemToAdd.productId()).orElseThrow());
    newItem.setAmount(itemToAdd.amount());
    itemList.add(newItem);
  }

  private void updateOrderTotal(Orders order) {
    double totalCost =
        order.getItems().stream()
            .mapToDouble(item -> item.getProduct().getUnitPrice() * item.getAmount())
            .sum();
    order.setTotal(totalCost);
  }

  @Override
  public void deleteItemFromOrder(Long orderID, List<RemoveItemFromOrderDTO> itemsToDelete) {
    var order = orderRepository.findById(orderID).orElseThrow();
    var items = order.getItems();
    for (RemoveItemFromOrderDTO itemToDelete : itemsToDelete) {
      items.stream()
          .filter(
              i -> Objects.equals(itemToDelete.productId().shortValue(), i.getProduct().getId()))
          .findFirst()
          .ifPresent(
              item -> {
                if (itemToDelete.removeAllFlag() || item.getAmount() <= itemToDelete.amount()) {
                  items.remove(item);
                  itemRepository.delete(item);
                } else {
                  item.setAmount(item.getAmount() - itemToDelete.amount());
                }
              });
    }
    double totalCost =
        items.stream()
            .mapToDouble(item -> item.getProduct().getUnitPrice() * item.getAmount())
            .sum();
    order.setTotal(totalCost);
    orderRepository.save(order);
  }
}
