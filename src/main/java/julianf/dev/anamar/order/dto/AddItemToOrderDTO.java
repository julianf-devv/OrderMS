package julianf.dev.anamar.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(hidden = true)
public class AddItemToOrderDTO {
    private Short productId;

    @Max(50)
    private int amount;

    public Short productId() {
        return productId;
    }

    public void setProductId(Short productId) {
        this.productId = productId;
    }

    public int amount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}