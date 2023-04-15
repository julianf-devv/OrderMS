package julianf.dev.anamar.order;

import julianf.dev.anamar.order.dto.AddItemToOrderDTO;
import julianf.dev.anamar.item.ItemRepository;
import julianf.dev.anamar.order.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    OrderRepository orderRepository;

    @Override
    public void save(Orders order) {

    }

    @Override
    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream().map(OrderDTO::new).toList();
    }

    @Override
    public List<OrderDTO> findAllNotFinished() {
        log.info("log {}", orderRepository.findAllByOrderFinishedFalse());
        return orderRepository.findAllByOrderFinishedFalse().stream().map(OrderDTO::new).toList();
    }

    @Override
    public OrderDTO getOrder(Long id) {
        return new OrderDTO(orderRepository.findById(id)
                .orElseThrow());
    }

    @Override
    public void deleteOrder(Orders order) {
            orderRepository.delete(order);
    }

    @Override
    public Orders addItemToOrder(Long id, List<AddItemToOrderDTO> items) {
        var actualorders = orderRepository.findById(id).orElseThrow();
        log.info("actualorders: {}", actualorders);
        for(var item: items){
            var itemorders = itemRepository.findById(Long.valueOf(item.id())).orElseThrow();
            itemorders.setAmount(item.amount());
            actualorders.getItems().add(itemorders);
        }
        return orderRepository.save(actualorders);
    }

    @Override
    public void deleteItemFromOrder(Long idorders, Long idItem) {
        var actualorders = orderRepository.findById(idorders).orElseThrow();
        var itemsorders = actualorders.getItems();
        itemsorders.removeIf(item -> item.getId() == idItem);
        orderRepository.save(actualorders);
    }


}
