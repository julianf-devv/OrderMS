package julianf.dev.anamar.table.dto;

import julianf.dev.anamar.table.RestaurantTable;
import julianf.dev.anamar.waiter.dto.WaiterDTO;

public record RestaurantTableDTO(short id, WaiterDTO waiter) {
    public RestaurantTableDTO(RestaurantTable table) {
        this(table.getTableNumber(), new WaiterDTO(table.getAssignedWaiter()));
    }
}
