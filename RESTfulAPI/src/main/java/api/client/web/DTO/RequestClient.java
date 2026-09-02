package api.client.web.DTO;

import api.address.web.DTO.RequestAddress;
import api.common.domain.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Schema(format = "json", description = "Клиент")
public class RequestClient{
    @Size(max=64, message = "max length of name is 64 characters")
    @Schema(format="string", description = "Имя клиента")
    private String name;

    @Size(max = 64, message = "max length of surname is 64 characters")
    @Schema(format="string", description = "Фамилия клиента")
    private String surname;

    @Past(message = "birthday must be past")
    @NotNull(message = "birthday is required field")
    @Schema(format = "date", description = "День рождения")
    private LocalDate birthday;

    @NotNull(message = "gender is required field")
    @Schema(format = "enum", description = "Гендер")
    private Gender gender;

    @NotNull(message = "address required field")
    private RequestAddress address;
}
