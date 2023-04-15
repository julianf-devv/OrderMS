package julianf.dev.anamar.item.dto;

import julianf.dev.anamar.item.Item;

public record ItemDTO(Short id) {

    public ItemDTO(Item item) {
        this(item.getId());
    }
}
