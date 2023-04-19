package julianf.dev.anamar.order;

import jakarta.transaction.Transactional;
import julianf.dev.anamar.item.Item;
import julianf.dev.anamar.order.dto.AddItemToOrderDTO;
import julianf.dev.anamar.item.ItemRepository;
import julianf.dev.anamar.order.dto.OrderDTO;
import julianf.dev.anamar.mapper.OrderMapper;
import julianf.dev.anamar.order.dto.OrderNoItemsDTO;
import julianf.dev.anamar.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper,
                            ProductRepository productRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public void save(Orders order) {

    }

    @Override
    public List<OrderNoItemsDTO> findAll() {//
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
        var itemList = order.getItems();
        items.forEach(itemDTO -> {
            boolean itemFound = itemList.stream()
                    .filter(item -> Objects.equals(itemDTO.productId(), item.getProduct().getId()))
                    .findFirst()
                    .map(item -> {
                        item.setAmount(item.getAmount() + itemDTO.amount());
                        return true;
                    })
                    .orElse(false);
            if (!itemFound) {
                var item = new Item();
                item.setProduct(productRepository.findById(itemDTO.productId()).orElseThrow());
                item.setAmount(itemDTO.amount());
                itemList.add(item);
            }
        });
        order.setCustomerName("Pedro"
        );
        order.setTotal(order.getItems().stream().mapToDouble(item -> item.getProduct().getUnitPrice() * item.getAmount()).sum());
        order.setItems(itemList);
        orderRepository.save(order);

    }
    @Override
    public void deleteItemFromOrder(Long idorders, Long idItem) {
        var actualorders = orderRepository.findById(idorders).orElseThrow();
        var itemsorders = actualorders.getItems();
        itemsorders.removeIf(item -> item.getId() == idItem);
        orderRepository.save(actualorders);
    }


}
