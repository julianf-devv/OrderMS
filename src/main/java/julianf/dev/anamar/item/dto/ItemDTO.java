package julianf.dev.anamar.item.dto;


import julianf.dev.anamar.product.ProductDTO;

import java.util.Set;


public record ItemDTO( Set<ProductDTO> products, int amount, Double total) {

    public ItemDTO(Set<ProductDTO> products, int amount, Double total) {
        this.products = products;
        this.amount = amount;
        this.total = 3.3;
    }
}
