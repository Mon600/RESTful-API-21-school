package api.supplier.datasource.mapper;

import api.address.datasource.mapper.AddressEntityMapper;
import api.address.domain.model.Address;

import api.supplier.datasource.model.SupplierEntity;
import api.supplier.domain.model.Supplier;

import java.util.UUID;


public class SupplierEntityMapper {
    public static SupplierEntity toDatalayer(Supplier supplier) {
        SupplierEntity entity = new SupplierEntity();
        UUID id = supplier.getId();
        if (id != null) {
            entity.setId(id);
        }
        entity.setName(supplier.getName());
        entity.setNumber(supplier.getNumber());
        entity.setAddress(AddressEntityMapper.toDatalayer(supplier.getAddress()));
        return entity;
    }

    public static Supplier toDomain(SupplierEntity entity) {
        Address address = AddressEntityMapper.toDomain(entity.getAddress());
        return new Supplier(
                entity.getId(),
                entity.getName(),
                address,
                entity.getNumber()
        );
    }
}
