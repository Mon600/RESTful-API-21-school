package api.address.datasource.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "address", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_address_country_city_street",
                columnNames = {"country", "city", "street"}
        )
    }
)
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "country", nullable = false, length = 32)
    private String country;

    @Column(name = "city", nullable = false, length = 64)
    private String city;

    @Column(name = "street", nullable = false, length = 128)
    private String street;

}
