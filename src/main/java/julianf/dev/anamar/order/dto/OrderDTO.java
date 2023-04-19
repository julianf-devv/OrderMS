package julianf.dev.anamar.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import julianf.dev.anamar.item.dto.ItemDTO;
import julianf.dev.anamar.product.ProductDTO;

import java.time.LocalDateTime;
import java.util.Set;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderDTO(
        Long id,
        String customerName,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime startDate,
        Double total,
        Set<ProductDTO> orderItems, boolean orderFinished) {

        public OrderDTO(Long id, String customerName, LocalDateTime startDate, Double total, Set<ProductDTO> orderItems, boolean orderFinished) {
                this.id = id;
                this.customerName = customerName;
                this.startDate = startDate;
                this.orderItems = orderItems;
                this.total = orderItems.stream().mapToDouble(product -> product.unitPrice() * product.quantity()).sum();
                this.orderFinished = orderFinished;

        }
}
