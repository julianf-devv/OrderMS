package julianf.dev.anamar.order;

import jakarta.servlet.http.HttpServletResponse;
import julianf.dev.anamar.order.dto.AddItemToOrderDTO;
import julianf.dev.anamar.item.Item;
import julianf.dev.anamar.order.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api")
public class OrderResource {
    private final OrderRepository orderRepository;

    private final OrderService orderService;

    @Autowired
    public OrderResource(OrderService orderService,
                         OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/v1/orders")
    public List<OrderDTO> getOrders(HttpServletResponse response) {
        response.setHeader("trace-id", UUID.randomUUID().toString().replace("-", ":"));
        log.info("{}",orderService.findAllNotFinished());
        return orderService.findAllNotFinished();
    }

    @GetMapping("/v1/orders/{id}")
    public OrderDTO getOrders(@PathVariable Long id) {
        return orderService.getOrder(id);
    }


    @PutMapping("/v1/orders/{id}")
    @Transactional
    public Orders addItemToOrder(@PathVariable Long id, @RequestBody List<AddItemToOrderDTO> items) {
        log.info("items: {}", items);
        return orderService.addItemToOrder(id, items);
    }

    @DeleteMapping("/v1/orders/{idOrder}/items/{idItem}")
    @Transactional
    public ResponseEntity<?> deleteItemFromOrder(@PathVariable Long idOrder, @PathVariable Long idItem) {
        var order = orderRepository.findById(idOrder).get();
            List<Item> items = orderRepository.findById(idOrder).get().getItems();
            items.removeIf(item -> item.getId() == idItem);
            order.setItems(items);
            orderService.save(order);
            return ResponseEntity.ok().build();
    }
    @DeleteMapping("/v2/orders/{idOrder}/items/{idItem}")
    @Transactional
    public ResponseEntity<Void> eliminarItemorders2(@PathVariable long idOrder, @PathVariable Long idItem) {
            orderService.deleteItemFromOrder(idOrder, idItem);
            return ResponseEntity.ok().build();
    }
}
