package julianf.dev.anamar.order.dto;

import julianf.dev.anamar.table.dto.RestaurantTableDTO;

import java.time.LocalDateTime;

public record OrderNoItemsDTO(Long id, String customerName, Double total, boolean orderFinished, RestaurantTableDTO table, LocalDateTime startDate) {

}
