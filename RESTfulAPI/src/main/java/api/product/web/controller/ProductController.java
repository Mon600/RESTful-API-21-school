package api.product.web.controller;

import api.exceptions.ProductNotFoundException;
import api.exceptions.SupplierNotFoundException;
import api.product.domain.service.ProductService;
import api.product.web.DTO.RequestProduct;
import api.product.web.DTO.ResponseProduct;
import api.product.web.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.osgi.annotation.bundle.Header;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@AllArgsConstructor
@RequestMapping("api/v1/products")
@Validated
public class ProductController {
    ProductService productService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить товар")
    public ResponseProduct addProduct(
            @RequestBody
            @Valid
            RequestProduct product
    ) throws SupplierNotFoundException {
        return ProductMapper.toWeb(productService.addProduct(ProductMapper.toDomain(product)));
    }

    @PatchMapping("/{productUuid}/stock")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Увеличить количество товара на складе")
    public ResponseProduct increaseProductStock(
            @PathVariable
            @Schema(format = "uuid", description = "UUID товара")
            UUID productUuid,

            @RequestBody
            @Min(1)
            @Schema(description = "Количество товара для пополнения")
            Integer amount
    ) throws ProductNotFoundException {
        return ProductMapper.toWeb(productService.increaseStock(productUuid, amount));
    }

    @PatchMapping("/{productUuid}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Уменьшить количество товара на складе")
    public ResponseProduct reduceStock(
            @PathVariable
            @Schema(format = "uuid", description = "UUID товара")
            UUID productUuid,

            @RequestBody
            @Schema(description = "Количество товара для вычитания")
            Integer amount
    ) throws ProductNotFoundException {
        return ProductMapper.toWeb(productService.reduceStock(productUuid, amount));
    }

    @GetMapping("/{productUuid}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить информацию о товаре")
    public ResponseProduct getProductByUuid(
            @PathVariable
            @Schema(format = "uuid", description = "UUID товара")
            UUID productUuid
    ) throws  ProductNotFoundException {
        return ProductMapper.toWeb(productService.getProductByUuid(productUuid));
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить список всех доступных товаров")
    public List<ResponseProduct> getAllProducts() {
        return productService.getAllProducts().stream().map(ProductMapper::toWeb).toList();
    }

    @DeleteMapping("/{productUuid}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Удалить информацию о товаре")
    public ResponseProduct deleteProduct(
            @PathVariable
            @Schema(format = "uuid", description = "UUID товара")
            UUID productUuid
    ) throws ProductNotFoundException {
        return ProductMapper.toWeb(productService.deleteProduct(productUuid));
    }
}
