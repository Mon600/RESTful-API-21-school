package api.address.web.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(format = "json", description = "Адрес")
@Getter
@Setter
public class RequestAddress {
    @Size(max = 32, message = "max length of city is 32 characters")
    @NotBlank(message = "country is required field")
    @Schema(description = "Страна", example = "Russia")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$")
    private String country;

    @Size(max = 64, message = "max length of city is 64 characters")
    @NotBlank(message = "city is required field")
    @Schema(description = "Город", example = "Moscow")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$")
    private String city;

    @Size(max = 128, message = "max length of street name is 128 characters")
    @NotBlank(message = "street is required field")
    @Schema(description = "Улица", example = "Lenina 5")
    private String street;
}
