package api.address.domain.service;

import api.address.datasource.mapper.AddressEntityMapper;
import api.address.datasource.model.AddressEntity;
import api.address.datasource.repository.AddressRepository;
import api.address.domain.model.Address;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AddressService {
    AddressRepository addressRepository;


    @Transactional(readOnly = true)
    public Optional<AddressEntity> getAddressByCountryAndCityAndStreet(Address address) {
        String country = address.getCountry();
        String city = address.getCity();
        String street = address.getStreet();
        return addressRepository.findByCountryAndCityAndStreet(country, city, street);
    }


    @Transactional
    public AddressEntity addAddress(Address address) {
        AddressEntity addressEntity = AddressEntityMapper.toDatalayer(address);
        addressRepository.save(addressEntity);
        return addressEntity;
    }
}
