package julianf.dev.anamar.item;

import julianf.dev.anamar.item.dto.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<ItemDTO> getAllItems() {
//        return itemRepository.findAll().stream().map(ItemDTO::new).toList();
        return null;
    }

    @Override
    public ItemDTO getItemById(Long id) {
        return null;
    }

    @Override
    public void save(ItemDTO itemDTO) {

    }

    @Override
    public void deleteItemById(Long id) {

    }
}
