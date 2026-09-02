package api.address.web.mapper;

import api.address.domain.model.Address;
import api.address.web.DTO.RequestAddress;
import api.address.web.DTO.ResponseAddress;

public class AddressMapper {
    static public Address toDomain(RequestAddress addressDTO) {
        return new Address(
                addressDTO.getCountry(),
                addressDTO.getCity(),
                addressDTO.getStreet()
        );
    }

    static public ResponseAddress toWeb(Address address) {
        return new ResponseAddress(
                address.getAddress_id(),
                address.getCountry(),
                address.getCity(),
                address.getStreet()
        );
    }
}
