package julianf.dev.anamar.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import julianf.dev.anamar.item.ItemRepository;
import julianf.dev.anamar.order.dto.AddItemToOrderDTO;
import julianf.dev.anamar.item.Item;
import julianf.dev.anamar.order.dto.OrderDTO;
import julianf.dev.anamar.order.dto.OrderNoItemsDTO;
import julianf.dev.anamar.order.dto.RemoveItemFromOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api")
@Tag(name = "Order", description = "Order APIs manage all the orders and it related operations")
public class OrderResource {
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    private final OrderService orderService;

    @Autowired
    public OrderResource(OrderService orderService,
                         OrderRepository orderRepository,
                         ItemRepository itemRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }
    @Operation(
            summary = "Get all the orders",
            description = "Get all the orders active in the system",
            responses = @ApiResponse(responseCode = "201", description = "Successfully retrieved all the orders")
    )

    @GetMapping("/v1/orders")
    public List<OrderNoItemsDTO> getOrders(HttpServletResponse response) {
        response.setHeader("trace-id", UUID.randomUUID().toString().replace("-", ":"));
        return orderService.findAll();
    }

    @GetMapping("/v1/orders/{id}")
    public OrderDTO getOrders(@PathVariable Long id) {
        return orderService.getOrder(id);
    }


    @PutMapping("/v1/orders/{id}")
    @Operation(summary = "Add item(s) to an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item(s) added to order",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(mediaType = "application/json"))
    })
    public void addItemToOrder(@PathVariable Long id, @RequestBody @Parameter(description = "Item(s) to be added to the order") List<AddItemToOrderDTO> items) {
        log.info("items: {}", items);
        orderService.addItemToOrder(id, items);
    }

    @DeleteMapping("/v1/orders/{id}")
    @Operation(summary = "Add item(s) to an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item(s) deleted from order",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(mediaType = "application/json"))
    })

    @Transactional
    public ResponseEntity<?> deleteItemFromOrder(@PathVariable Long id, @RequestBody List<RemoveItemFromOrderDTO> itemsToDelete) {
        var order = orderRepository.findById(id).get();
        var items = order.getItems();
        var itemsCopy = new HashSet<>(items);
        itemsToDelete.forEach(itemToDelete -> items.forEach(item -> {
            if (item.getId() == itemToDelete.productId()) {
                if(itemToDelete.removeAll())
                {
                    itemRepository.deleteById((long) item.getId());
                    itemsCopy.remove(item);
                }
                else
                {
                    item.setAmount(item.getAmount() - itemToDelete.amount());
                    if(item.getAmount() <= 0) {
                        itemRepository.deleteById((long) item.getId());
                        itemsCopy.remove(item);
                    }
                }
            }
        }));
        order.setItems(itemsCopy);
        order.setTotal(order.getItems().stream().mapToDouble(item -> item.getProduct().getUnitPrice() * item.getAmount()).sum());
        orderRepository.save(order);
        return ResponseEntity.ok().build();
    }

}
