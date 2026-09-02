package api.address.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
public class Address {
    private final UUID address_id;
    private String country;
    private String city;
    private String street;

    public Address(String country, String city, String street) {
        this.address_id = null;
        this.country = country;
        this.city = city;
        this.street = street;
    }
}
