package api.product.domain.model;


import lombok.Getter;


import java.time.LocalDate;
import java.util.UUID;


@Getter
public class ProductNoSupplier {
    private final UUID id = null;
    private final String name;
    private final String category;
    private final Integer price;
    private final Integer availableStock;
    private final UUID supplierId;

    public ProductNoSupplier(String name, String category, Integer price, Integer availableStock, UUID supplierId) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.availableStock = availableStock;
        this.supplierId = supplierId;
    }


}
