package julianf.dev.anamar.item;

import julianf.dev.anamar.item.dto.ItemDTO;

import java.util.List;

public interface ItemService {

    public List<ItemDTO> getAllItems();

    public ItemDTO getItemById(Long id);

    public void save (ItemDTO itemDTO);

    public void deleteItemById(Long id);

}
