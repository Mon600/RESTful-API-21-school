package api.image.web.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Запрос на обработку изображения")
public class RequestImage {
    @Schema(type = "string", format = "binary", description = "Файл изображения")
    private MultipartFile file;
}
