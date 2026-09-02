package api.address.datasource.repository;

import api.address.datasource.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {

    public Optional<AddressEntity> findByCountryAndCityAndStreet(String country, String city, String street);
}
