package api.supplier.web.DTO;

import api.address.web.DTO.RequestAddress;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(format = "json", description = "Поставщик")
public class RequestSupplier {

    @NotBlank(message = "Name can't be empty")
    @Size(min = 0, max = 65)
    @Schema(format = "string", description = "Имя клиента", example = "Ben")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$")
    private String name;

    @NotNull
    @Schema(format = "json", description = "Адрес клиента")
    private RequestAddress address;

    @NotBlank
    @Size(max = 12)
    @Pattern(regexp = "^[0-9]{11,12}$")
    @Schema(format = "phone", description = "Номер телефона")
    private String phone;
}
