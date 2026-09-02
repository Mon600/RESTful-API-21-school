package api.image.datasource.mapper;

import api.image.datasource.model.ImageEntity;
import api.image.domain.model.Image;

public class ImageEntityMapper {
    public static ImageEntity toDataLayer(Image image) {
        if (image == null) {
            return null;
        }
        return new ImageEntity(image.getId(), image.getImage());
    }

    public  static Image toDomain(ImageEntity entity) {
        return new Image(entity.getId(), entity.getImage());
    }
}
