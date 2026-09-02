package api.product.web.DTO;

import api.supplier.web.DTO.RequestSupplier;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(format = "json", description = "Товар")
public class RequestProduct {
    @NotBlank
    @Size(max = 64, message = "Name of product must be shorter then 64 characters")
    @Schema(description = "Имя товара")
    private String name;

    @NotBlank
    @Size(max = 64, message = "Name of category must be shorter then 64 characters")
    @Schema(description = "Категория товара")
    private String category;

    @NotNull
    @Min(1)
    @Schema(description = "Цена товара")
    private Integer price;

    @NotNull
    @Min(0)
    @Schema(description = "Остаток товара на складе")
    private Integer availableStock;

    @NotNull
    @Schema(format = "uuid", description = "UUID поставщика")
    private UUID supplierId;
}
