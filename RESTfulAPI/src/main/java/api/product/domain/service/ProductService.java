package api.product.domain.service;

import api.exceptions.ProductNotFoundException;
import api.exceptions.SupplierNotFoundException;
import api.image.datasource.model.ImageEntity;
import api.image.datasource.mapper.ImageEntityMapper;
import api.image.datasource.repository.ImageRepository;
import api.image.domain.model.Image;
import api.product.datasource.mapper.ProductEntityMapper;
import api.product.datasource.model.ProductEntity;
import api.product.datasource.repository.ProductRepository;
import api.product.domain.mapper.ProductDomainMapper;
import api.product.domain.model.Product;
import api.product.domain.model.ProductNoSupplier;
import api.supplier.domain.model.Supplier;
import api.supplier.domain.service.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final SupplierService supplierService;

    private ProductEntity getProduct(UUID id) throws ProductNotFoundException {
        Optional<ProductEntity> existsProductEntityOpt = productRepository.findById(id);
        if (existsProductEntityOpt.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }
        return existsProductEntityOpt.get();
    }

    @Transactional
    public Product addProduct(ProductNoSupplier product) throws SupplierNotFoundException {
        Supplier supplier = supplierService.getSupplier(product.getSupplierId());
        Product productForSave = ProductDomainMapper.toProductWithSupplier(product, supplier);
        ProductEntity entity = ProductEntityMapper.toDatalayer(productForSave);
        return ProductEntityMapper.toDomain(productRepository.save(entity));
    }

    @Transactional
    public Product increaseStock(UUID productUuid, Integer amount) throws ProductNotFoundException {
        Product existsProduct = getProductByUuid(productUuid);
        Integer newStock = existsProduct.getAvailableStock() + amount;
        existsProduct.setAvailableStock(newStock);
        existsProduct.setLastUpdateDate(LocalDate.now());
        return  ProductEntityMapper.toDomain(
                productRepository.save(
                        ProductEntityMapper.toDatalayer(existsProduct)
                )
        );
    }

    @Transactional
    public Product reduceStock(UUID productUuid, Integer amount) throws ProductNotFoundException {
        ProductEntity entity = getProduct(productUuid);
        Integer availableStock = entity.getAvailableStock();
        if ((availableStock - amount) < 0) {
            throw new IllegalArgumentException("Amount too big. Stock may be greater then zero.");
        }
        entity.setAvailableStock(availableStock - amount);
        return ProductEntityMapper.toDomain(productRepository.save(entity));
    }


    @Transactional(readOnly = true)
    public Product getProductByUuid(UUID productUuid) throws ProductNotFoundException {
        return ProductEntityMapper.toDomain(this.getProduct(productUuid));
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        List<ProductEntity> products = productRepository.findAllAvailable();
        return products.stream().map(ProductEntityMapper::toDomain).toList();
    }

    @Transactional
    public Product deleteProduct(UUID id) throws ProductNotFoundException {
        ProductEntity existsProduct = getProduct(id);
        productRepository.deleteById(id);
        return ProductEntityMapper.toDomain(existsProduct);
    }

    @Transactional
    public Product addImage(Image image, UUID productUuid) throws ProductNotFoundException {
        if (image.getImage() == null || image.getImage().length == 0) {
            throw new IllegalArgumentException("Invalid image");
        }
        ImageEntity imageEntity = imageRepository.save(
                ImageEntityMapper.toDataLayer(image)
        );
        return updateImage(ImageEntityMapper.toDomain(imageEntity), productUuid);

    }

    private Product updateImage(Image image, UUID productUuid) throws ProductNotFoundException {
        Product product = this.getProductByUuid(productUuid);
        product.setImage(image);
        return ProductEntityMapper.toDomain(
                productRepository.save(
                        ProductEntityMapper.toDatalayer(product)
                )
        );
    }


}
