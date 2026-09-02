package api.product.web.mapper;

import api.image.domain.model.Image;
import api.product.domain.model.Product;
import api.product.domain.model.ProductNoSupplier;
import api.product.web.DTO.RequestProduct;
import api.product.web.DTO.ResponseProduct;
import api.supplier.web.mapper.SupplierMapper;

import java.time.LocalDate;

public class ProductMapper {
    public static ResponseProduct toWeb(Product product) {
        ResponseProduct response = new ResponseProduct();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setCategory(product.getCategory());
        response.setAvailableStock(product.getAvailableStock());
        response.setSupplier(SupplierMapper.toWeb(product.getSupplier()));
        LocalDate lastUpdate = product.getLastUpdateDate();
        if (lastUpdate != null) {
            response.setLastUpdateDate(product.getLastUpdateDate());
        }
        Image image = product.getImage();
        if (image != null) {
            response.setImage_id(image.getId());
        }
        return response;
    }

    public static ProductNoSupplier toDomain(RequestProduct request) {
        return new ProductNoSupplier(
                request.getName(),
                request.getCategory(),
                request.getPrice(),
                request.getAvailableStock(),
                request.getSupplierId()
        );
    }
}
