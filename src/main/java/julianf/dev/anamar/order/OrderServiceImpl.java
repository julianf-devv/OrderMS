package julianf.dev.anamar.order;

import jakarta.transaction.Transactional;
import julianf.dev.anamar.item.Item;
import julianf.dev.anamar.item.dto.ItemDTO;
import julianf.dev.anamar.mapper.OrderMapper;
import julianf.dev.anamar.order.dto.AddItemToOrderDTO;
import julianf.dev.anamar.order.dto.OrderDTO;
import julianf.dev.anamar.order.dto.OrderNoItemsDTO;
import julianf.dev.anamar.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.productRepository = productRepository;
    }

    @Override
    public void save(Orders order) {

    }

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
        items.forEach(itemtoAdd -> currentItemList.stream()
                .filter(existingItem -> Objects.equals(itemtoAdd.productId(), existingItem.getProduct().getId()))
                .findFirst()
                .map(existingItem -> {
                    existingItem.setAmount(existingItem.getAmount() + itemtoAdd.amount());
                    return existingItem;
        }).orElseGet(() -> addNewItem(currentItemList, itemtoAdd)));
        updateOrderTotal(order);
        order.setItems(currentItemList);
        orderRepository.save(order);
    }
    private Item addNewItem(Set<Item> itemList, AddItemToOrderDTO itemToAdd) {
        Item newItem = new Item();
        newItem.setProduct(productRepository.findById(itemToAdd.productId()).orElseThrow());
        newItem.setAmount(itemToAdd.amount());
        itemList.add(newItem);
        return newItem;
    }
    private void updateOrderTotal(Orders order) {
        double totalCost = order.getItems().stream().mapToDouble(item -> item.getProduct().getUnitPrice() * item.getAmount()).sum();
        order.setTotal(totalCost);
    }

    @Override
    public void deleteItemFromOrder(Long idorders, Long idItem) {
        var actualorders = orderRepository.findById(idorders).orElseThrow();
        var itemsorders = actualorders.getItems();
        itemsorders.removeIf(item -> item.getId() == idItem);
        orderRepository.save(actualorders);
    }


}
