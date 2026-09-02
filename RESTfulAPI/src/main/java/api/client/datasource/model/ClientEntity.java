package api.client.datasource.model;


import api.address.datasource.model.AddressEntity;
import api.common.domain.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "client",
        uniqueConstraints = {
            @UniqueConstraint(
               name = "uk_name_surname_birthday_address_id",
                    columnNames = {"name", "surname", "birthday", "address_id"}
            )
        }
    )
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "client_name", nullable = false, length = 64)
    private String name;

    @Column(name = "client_surname", nullable = true, length = 64)
    private String surname = null;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "gender_enum")
    @ColumnTransformer(write = "?::gender_enum")
    private Gender gender;

    @Column(name = "registration_date", nullable = false)
    private OffsetDateTime registrationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private AddressEntity address;


    @PrePersist
    public void prePersist() {
        if (this.registrationDate == null) {
            this.registrationDate = OffsetDateTime.now();
        }
    }
}
