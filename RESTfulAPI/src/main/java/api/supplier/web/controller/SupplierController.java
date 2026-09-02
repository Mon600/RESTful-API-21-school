package api.supplier.web.controller;


import api.address.web.DTO.RequestAddress;
import api.address.web.mapper.AddressMapper;
import api.exceptions.DTO.ErrorDTO;
import api.exceptions.SupplierNotFoundException;
import api.supplier.domain.service.SupplierService;
import api.supplier.web.mapper.SupplierMapper;
import api.supplier.web.DTO.RequestSupplier;
import api.supplier.web.DTO.ResponseSupplier;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/suppliers")
@AllArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Новый поставщик")
    @ApiResponse(
            responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseSupplier.class)
            ),
            description = "Success"
    )
    public ResponseSupplier addSupplier(
            @RequestBody
            @Valid
            RequestSupplier supplier
    ) {
        return SupplierMapper.toWeb(
                supplierService.addSupplier(
                        SupplierMapper.toDomain(supplier)
                )
        );
    }

    @PatchMapping("/{supplierUuid}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Изменить адрес поставщика")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseSupplier.class)
            ),
            description = "Success"
    )
    public ResponseSupplier changeAddress(
            @PathVariable
            @Schema(format = "UUID", description = "UUID потсавщика", example = "3afba4e5-4aaa-4abe-ad7a-cbf9f3bada77")
            UUID supplierUuid,

            @RequestBody
            @Valid
            RequestAddress newAddress
    ) throws SupplierNotFoundException {
        return SupplierMapper.toWeb(
                supplierService.changeAddress(
                        supplierUuid, AddressMapper.toDomain(newAddress)
                )
        );
    }

    @DeleteMapping("/{supplierUuid}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Удалить поставщика")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = ResponseSupplier.class)
            ),
            description = "Success"
    )
    public ResponseSupplier deleteSupplier(
            @PathVariable
            @Schema(format = "UUID", description = "UUID потсавщика")
            UUID supplierUuid
    ) throws SupplierNotFoundException {
        return SupplierMapper.toWeb(
                supplierService.deleteSupplier(
                        supplierUuid
                )
        );
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(
                                    implementation = ResponseSupplier.class
                            )
                    )
            ),
            description = "Success"
    )
    public List<ResponseSupplier> getAllSuppliers() {
        return supplierService.getAll().stream()
                .map(SupplierMapper::toWeb)
                .toList();
    }

    @GetMapping("/{supplierUuid}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить поставщика по UUID")
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = ResponseSupplier.class)
            ),
            description = "Success"
    )
    public  ResponseSupplier getSupplier(
            @PathVariable
            @Schema(format = "UUID", description = "UUID потсавщика")
            UUID supplierUuid
    ) throws SupplierNotFoundException {
        return SupplierMapper.toWeb(
                supplierService.getSupplier(
                        supplierUuid
                )
        );
    }
}
