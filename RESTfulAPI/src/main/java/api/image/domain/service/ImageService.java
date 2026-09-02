package api.image.domain.service;



import api.exceptions.ImageNotFoundException;
import api.exceptions.ProductNotFoundException;
import api.image.datasource.model.ImageEntity;
import api.image.datasource.mapper.ImageEntityMapper;
import api.image.datasource.repository.ImageRepository;
import api.image.domain.model.Image;
import api.product.domain.model.Product;
import api.product.domain.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ProductService productService;

    private ImageEntity getImageByUuidInternal(UUID imageUuid) throws ImageNotFoundException {
        Optional<ImageEntity> imageEntityOpt = imageRepository.findById(imageUuid);
        if (imageEntityOpt.isEmpty()) {
            throw new ImageNotFoundException("Image not found");
        }
        return imageEntityOpt.get();
    }

    @Transactional
    public void changeImage(Image image, UUID imageUuid) throws ImageNotFoundException {
        if (image.getImage() == null || image.getImage().length == 0) {
            throw new IllegalArgumentException("Invalid image");
        }
        ImageEntity existsImage = getImageByUuidInternal(imageUuid);
        existsImage.setImage(image.getImage());
        imageRepository.save(existsImage);
    }

    @Transactional
    public void deleteImage(UUID imageUuid) throws ImageNotFoundException {
        ImageEntity existsImage = getImageByUuidInternal(imageUuid);
        imageRepository.delete(existsImage);
    }

    @Transactional
    public Image getProductImage(UUID productUuid) throws ImageNotFoundException, ProductNotFoundException {
        Product existsProduct = productService.getProductByUuid(productUuid);
        if (existsProduct.getImage() == null) {
            throw new ImageNotFoundException(String.format("Image not exists for product with UUID: %s\n", productUuid));
        }
        return existsProduct.getImage();
    }

    @Transactional
    public Image getImageByUuid(UUID imageUuid) throws ImageNotFoundException {
        ImageEntity existsImage = getImageByUuidInternal(imageUuid);
        return ImageEntityMapper.toDomain(existsImage);
    }
}
