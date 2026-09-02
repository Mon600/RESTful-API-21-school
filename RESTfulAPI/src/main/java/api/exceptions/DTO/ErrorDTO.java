package api.exceptions.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.ScriptAssert;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDTO {
    @Schema(description = "Код ошибки")
    private Integer code;
    @Schema(description = "Сообщение об ошибке")
    private String message;
    @Schema(description = "Время ошибки")
    private LocalDateTime date;

    public ErrorDTO(HttpStatus code, String message) {
        this.code = code.value();
        this.message = message;
        this.date = LocalDateTime.now();
    }
}
