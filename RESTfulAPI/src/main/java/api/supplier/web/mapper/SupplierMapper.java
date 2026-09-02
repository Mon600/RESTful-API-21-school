package api.supplier.web.mapper;

import api.address.web.mapper.AddressMapper;
import api.supplier.domain.model.Supplier;
import api.supplier.web.DTO.RequestSupplier;
import api.supplier.web.DTO.ResponseSupplier;

public class SupplierMapper {
    public static ResponseSupplier toWeb(Supplier supplier) {
        return new ResponseSupplier(
                supplier.getId(),
                supplier.getName(),
                AddressMapper.toWeb(supplier.getAddress()),
                supplier.getNumber()
        );
    }

    public static Supplier toDomain(RequestSupplier request) {
        return new Supplier(
                request.getName(),
                AddressMapper.toDomain(request.getAddress()),
                request.getPhone()
        );
    }
}
