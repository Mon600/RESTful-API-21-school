package api.image.web.mapper;

import api.exceptions.ImageNotFoundException;
import api.image.domain.model.Image;
import api.image.web.DTO.RequestImage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;

import java.io.IOException;

public class ImageMapper {
    public static Image toDomain(RequestImage request) throws IOException {
        if (request.getFile() == null || request.getFile().getBytes().length == 0) {
            throw new IllegalArgumentException("Invalid image");
        }
        return new Image(request.getFile().getBytes());
    }

    public static ResponseEntity<byte[]> toWeb(Image image) throws ImageNotFoundException {
        if (image == null || image.getImage().length == 0) {
            throw new ImageNotFoundException("Image not found");
        }
        byte[] imageBytes = image.getImage();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(
                ContentDisposition.attachment()
                .filename(String.format("image_%s.png", image.getId()))
                .build()
        );
        headers.setContentLength(image.getImage().length);
        return ResponseEntity.ok()
                .headers(headers)
                .body(imageBytes);
    }
}
