package julianf.dev.anamar.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Send this to add an item to an order")
public record AddItemToOrderDTO(Short productId,
                                @Schema(description = "Amount of items to add to the order") int amount) {
}