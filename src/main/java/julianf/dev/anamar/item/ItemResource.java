package julianf.dev.anamar.item;

import julianf.dev.anamar.item.dto.ItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class ItemResource {

    @Autowired
    private ItemService itemService;



    @GetMapping("/v1/items")
    public List<ItemDTO> getItems() {
        return itemService.getAllItems();
    }

    @PostMapping("/v1/items")
    public void saveItem(@RequestBody ItemDTO item) {
        itemService.save(item);
    }


}
