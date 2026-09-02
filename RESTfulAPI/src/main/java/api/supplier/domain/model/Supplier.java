package api.supplier.domain.model;

import api.address.domain.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    private UUID id = null;
    private String name;
    private Address address;
    private String number;

    public Supplier(String name, Address address, String number) {
        this.name = name;
        this.address = address;
        this.number = number;
    }
}
