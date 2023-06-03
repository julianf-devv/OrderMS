package julianf.dev.anamar.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import julianf.dev.anamar.order.dto.AddItemToOrderDTO;
import julianf.dev.anamar.order.dto.OrderDTO;
import julianf.dev.anamar.order.dto.OrderNoItemsDTO;
import julianf.dev.anamar.order.dto.RemoveItemFromOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/api")
@Tag(name = "Order", description = "Order APIs manage all the orders and it related operations")
@Validated
public class OrderResource {

    private final OrderService orderService;

    @Autowired
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Get all the orders", description = "Get all the orders active in the system", responses = @ApiResponse(responseCode = "201", description = "Successfully retrieved all the orders"))
    @GetMapping("/v1/orders")
    public List<OrderNoItemsDTO> getOrders() {
        log.info("getOrders");
        return orderService.findAll();
    }

    @GetMapping("/v1/orders/{id}")
    public OrderDTO getOrders(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @PutMapping("/v1/orders/{id}")
    @Operation(summary = "Add item(s) to an order")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Item(s) added to order", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(mediaType = "application/json"))})
    @Validated
    public void addItemToOrder(@PathVariable Long id, @RequestBody @Parameter(description = "Item(s) to be added to the order") @Valid List<AddItemToOrderDTO> items) {
        log.info("items: {}", items);
        orderService.addItemToOrder(id, items);
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Item(s) deleted from order", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(mediaType = "application/json"))})
    @Transactional
    @DeleteMapping("/v1/orders/{id}")
    @Operation(summary = "Remove item(s) from an order")
    public ResponseEntity<Void> deleteItemFromOrder(@PathVariable Long id, @RequestBody @Validated List<@Valid RemoveItemFromOrderDTO> itemsToDelete) {
        log.info("Delete items API call - {}", itemsToDelete);
        orderService.deleteItemFromOrder(id, itemsToDelete);
        return ResponseEntity.ok().build();
    }

}
