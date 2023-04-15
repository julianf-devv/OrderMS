package julianf.dev.anamar.table.dto;

import julianf.dev.anamar.table.RestaurantTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TableMapper {
    @Mapping(source = "tableNumber", target = "id")
    @Mapping(source = "assignedWaiter", target = "waiter")
    RestaurantTableDTO toDTO(RestaurantTable table);

    @Mapping(source = "id", target = "tableNumber")
    @Mapping(source = "waiter", target = "assignedWaiter")
    RestaurantTable toEntity(RestaurantTableDTO tableDTO);

}
