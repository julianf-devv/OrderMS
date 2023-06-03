package julianf.dev.anamar.product;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductDTO(String name, String description, Double unitPrice, ProductType productType, int quantity, double total) {
}
