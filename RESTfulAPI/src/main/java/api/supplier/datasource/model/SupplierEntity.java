package api.supplier.datasource.model;


import api.address.datasource.model.AddressEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "supplier",
    uniqueConstraints = @UniqueConstraint(
            name = "uk_name_address_id_phone_supplier",
            columnNames = {"name", "address_id", "phone"}
    )
)
public class SupplierEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private AddressEntity address;

    @Column(name = "phone_number", length = 12, nullable = false)
    private String number;
}
