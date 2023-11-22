package julianf.dev.anamar.order.dto;

import java.time.LocalDateTime;
import julianf.dev.anamar.table.dto.RestaurantTableDTO;

public record OrderNoItemsDTO(
    Long id,
    String customerName,
    Double total,
    boolean orderFinished,
    RestaurantTableDTO table,
    LocalDateTime startDate) {}
