package api.address.datasource.mapper;

import api.address.datasource.model.AddressEntity;
import api.address.domain.model.Address;

public class AddressEntityMapper {
    static public AddressEntity toDatalayer(Address address) {
        AddressEntity entity = new AddressEntity();
        entity.setCountry(address.getCountry());
        entity.setCity(address.getCity());
        entity.setStreet(address.getStreet());
        return entity;
    }

    static public Address toDomain(AddressEntity entity) {
        return new Address(
                entity.getId(),
                entity.getCountry(),
                entity.getCity(),
                entity.getStreet()
        );
    }

}
