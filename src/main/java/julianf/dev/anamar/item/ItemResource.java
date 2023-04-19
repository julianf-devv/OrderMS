package julianf.dev.anamar.item;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import julianf.dev.anamar.item.dto.ItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
@Tag(name = "Item", description = "Item APIs manage all to items and it related operations")
public class ItemResource {

    @Autowired
    private ItemService itemService;

    @GetMapping("/v1/items")
    @ApiResponse(responseCode = "200", description = "Get all items")
    public List<ItemDTO> getItems() {
        return itemService.getAllItems();
    }

    @PostMapping("/v1/items")
    @ApiResponse(responseCode = "200", description = "Get item by id")
    public void saveItem(@RequestBody @Parameter(description = "Nombre del usuario que está agregando la mascota", required = true)
                             ItemDTO item) {
        itemService.save(item);
    }


}
