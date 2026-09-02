package api.product.datasource.model;


import api.image.datasource.model.ImageEntity;
import api.supplier.datasource.model.SupplierEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "product", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_name_supplier_id",
                columnNames = {"name", "supplier_id"}
        )
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "category", nullable = false, length = 64)
    private String category;

    @Column(name = "price", nullable = false, columnDefinition = "INTEGER CHECK (price > 0)")
    private Integer price;

    @Column(name = "available_stock", nullable = false, columnDefinition = "INTEGER CHECK (available_stock >= 0)")
    private Integer availableStock;

    @Column(name = "last_update_date", nullable = false)
    private LocalDate lastUpdateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierEntity supplier;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", nullable = true)
    private ImageEntity image = null;

}
