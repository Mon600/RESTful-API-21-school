package api.image.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class Image {
    private UUID id = null;
    private byte[] image;

    public Image(byte[] image) {
        this.image = image;
    }
}
