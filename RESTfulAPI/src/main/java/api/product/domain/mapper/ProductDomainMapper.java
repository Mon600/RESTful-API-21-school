package api.product.domain.mapper;

import api.product.domain.model.Product;
import api.product.domain.model.ProductNoSupplier;
import api.supplier.domain.model.Supplier;

public class ProductDomainMapper {

    public static Product toProductWithSupplier(ProductNoSupplier product , Supplier supplier){
        return new Product(
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getAvailableStock(),
                supplier
        );
    }
}
