package api.supplier.domain.service;


import api.address.datasource.model.AddressEntity;
import api.address.domain.model.Address;
import api.address.domain.service.AddressService;
import api.exceptions.SupplierNotFoundException;
import api.supplier.datasource.mapper.SupplierEntityMapper;
import api.supplier.datasource.model.SupplierEntity;
import api.supplier.datasource.repository.SupplierRepository;
import api.supplier.domain.model.Supplier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final AddressService addressService;

    private SupplierEntity getExistsSupplier(UUID supplierUuid) throws SupplierNotFoundException {
        Optional<SupplierEntity> existsSupplier = supplierRepository.findById(supplierUuid);
        if (existsSupplier.isEmpty()) {
            throw new SupplierNotFoundException("Supplier not found");
        }
        return existsSupplier.get();
    }


    @Transactional
    public Supplier addSupplier(Supplier supplier) {
        Address address = supplier.getAddress();
        Optional<AddressEntity> existsAddressOpt = addressService.getAddressByCountryAndCityAndStreet(address);
        AddressEntity addressEntity;
        addressEntity = existsAddressOpt.orElseGet(() -> addressService.addAddress(address));
        SupplierEntity supplierEntity = SupplierEntityMapper.toDatalayer(supplier);
        supplierEntity.setAddress(addressEntity);
        return SupplierEntityMapper.toDomain(supplierRepository.save(supplierEntity));

    }

    @Transactional
    public Supplier changeAddress(UUID supplierUuid, Address address) throws SupplierNotFoundException {
        SupplierEntity supplierEntity = getExistsSupplier(supplierUuid);

        Optional<AddressEntity> existsAddress = addressService.getAddressByCountryAndCityAndStreet(address);
        AddressEntity addressEntity = existsAddress.orElseGet(() -> addressService.addAddress(address));
        supplierEntity.setAddress(addressEntity);

        SupplierEntity savedSupplier = supplierRepository.save(supplierEntity);
        return SupplierEntityMapper.toDomain(savedSupplier);
    }

    @Transactional
    public Supplier deleteSupplier(UUID supplierUuid) throws SupplierNotFoundException {
        SupplierEntity supplierEntity = getExistsSupplier(supplierUuid);
        supplierRepository.deleteById(supplierUuid);
        return SupplierEntityMapper.toDomain(supplierEntity);
    }

    @Transactional
    public Supplier getSupplier(UUID supplierUuid) throws SupplierNotFoundException {
        SupplierEntity supplierEntity = getExistsSupplier(supplierUuid);
        return SupplierEntityMapper.toDomain(supplierEntity);
    }

    @Transactional(readOnly = true)
    public List<Supplier> getAll() {
        return supplierRepository.findAll().stream()
                .map(SupplierEntityMapper::toDomain)
                .toList();
    }

}
