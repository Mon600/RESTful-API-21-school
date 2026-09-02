package api.product.web.DTO;

import api.supplier.web.DTO.ResponseSupplier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseProduct {
    private UUID id;
    private String name;
    private String category;
    private Integer price;
    private Integer availableStock;
    private LocalDate lastUpdateDate;
    private ResponseSupplier supplier;
    private UUID image_id;
}
