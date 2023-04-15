package julianf.dev.anamar.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import julianf.dev.anamar.item.dto.ItemDTO;
import julianf.dev.anamar.order.Orders;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderDTO(
        Long id,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime startDate,
        List<ItemDTO> orderItems) {

        public OrderDTO(Orders order) {
                this(order.getId(), order.getStartDate(),order.getItems().stream().map(ItemDTO::new).toList());
        }

}
