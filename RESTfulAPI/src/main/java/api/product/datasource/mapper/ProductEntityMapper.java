package api.product.datasource.mapper;

import api.image.datasource.mapper.ImageEntityMapper;
import api.image.domain.model.Image;
import api.product.datasource.model.ProductEntity;
import api.product.domain.model.Product;
import api.supplier.datasource.mapper.SupplierEntityMapper;

public class ProductEntityMapper {
    public static Product toDomain(ProductEntity entity) {
        Product product = new Product(
                entity.getId(),
                entity.getName(),
                entity.getCategory(),
                entity.getPrice(),
                entity.getAvailableStock(),
                entity.getLastUpdateDate(),
                SupplierEntityMapper.toDomain(entity.getSupplier())
        );
        if (entity.getImage() != null) {
            product.setImage(
                    ImageEntityMapper.toDomain(
                            entity.getImage()
                    )
            );
        }
        return product;
    }

    public static ProductEntity toDatalayer(Product product) {
        return new ProductEntity(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getAvailableStock(),
                product.getLastUpdateDate(),
                SupplierEntityMapper.toDatalayer(product.getSupplier()),
                ImageEntityMapper.toDataLayer(product.getImage())
        );
    }
}
