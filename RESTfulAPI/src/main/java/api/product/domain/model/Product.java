package api.product.domain.model;

import api.image.domain.model.Image;
import api.supplier.domain.model.Supplier;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.UUID;


@Setter
@Getter
public class Product {
    private UUID id = null;
    private String name;
    private String category;
    private Integer price;
    private Integer availableStock;
    private LocalDate lastUpdateDate;
    private Supplier supplier;
    private Image image = null;

    public Product(String name, String category, Integer price, Integer availableStock, Supplier supplier) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.availableStock = availableStock;
        this.supplier = supplier;
        this.lastUpdateDate = LocalDate.now();
    }

    public Product(UUID id, String name, String category, Integer price, Integer availableStock, LocalDate lastUpdateDate, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.availableStock = availableStock;
        this.lastUpdateDate = lastUpdateDate;
        this.supplier = supplier;
    }
}
