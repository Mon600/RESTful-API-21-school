package api.image.web.controller;


import api.exceptions.ImageNotFoundException;
import api.exceptions.ProductNotFoundException;
import api.image.web.DTO.RequestImage;
import api.image.domain.service.ImageService;
import api.image.web.mapper.ImageMapper;
import api.product.domain.model.Product;
import api.product.domain.service.ProductService;
import api.product.web.DTO.ResponseProduct;
import api.product.web.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/images")
@AllArgsConstructor
public class ImageController {
    private final ProductService productService;
    private final ImageService imageService;

    @PostMapping(value = "/{productUuid}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка изображения")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseProduct addImage(
            @PathVariable
            @Schema(format = "uuid", description = "UUID товара")
            UUID productUuid,

            @ModelAttribute
            RequestImage image
    ) throws IOException, ProductNotFoundException {
        return ProductMapper.toWeb(productService.addImage(ImageMapper.toDomain(image), productUuid));
    }


    @PutMapping(value = "/{imageUuid}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Замена изображения")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> changeImage(
            @PathVariable
            @Schema(format = "uuid", description = "UUID изображения")
            UUID imageUuid,

            @ModelAttribute
            RequestImage image
    ) throws IOException, ImageNotFoundException {
        imageService.changeImage(ImageMapper.toDomain(image), imageUuid);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Image successfully changed");
    }

    @DeleteMapping(value = "/{imageUuid}")
    @Operation(summary = "Удаление изображения")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteImage(
            @PathVariable
            @Schema(format = "uuid", description = "UUID изображения")
            UUID imageUuid
    ) throws ImageNotFoundException {
        imageService.deleteImage(imageUuid);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Image successfully deleted");
    }

    @GetMapping(value = "products/{productUuid}/image")
    @Operation(summary = "Получение изображения по UUID товара")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getImageByProductUuid(
            @PathVariable
            @Schema(format = "uuid", description = "UUID товара")
            UUID productUuid
    ) throws ImageNotFoundException, ProductNotFoundException {
        return ImageMapper.toWeb(imageService.getProductImage(productUuid));
    }

    @GetMapping(value = "/{imageUuid}")
    @Operation(summary = "Получение изображения по UUID")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getImage(
            @PathVariable
            @Schema(format = "uuid", description = "UUID товара")
            UUID imageUuid
    ) throws ImageNotFoundException {
        return ImageMapper.toWeb(imageService.getImageByUuid(imageUuid));
    }
}
