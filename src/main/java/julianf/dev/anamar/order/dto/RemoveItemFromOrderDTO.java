package julianf.dev.anamar.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;

@Schema(description = "Send this to add an item to an order", hidden = true)
public record RemoveItemFromOrderDTO(
    Long productId,
    @Schema(description = "Amount of items to add to the order") @Max(20) int amount,
    boolean removeAllFlag) {}
