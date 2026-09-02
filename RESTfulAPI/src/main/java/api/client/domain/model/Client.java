package api.client.domain.model;

import api.address.domain.model.Address;

import api.common.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Client {
    private final UUID client_id;
    private String name;
    private String surname;
    private LocalDate birthday;
    private Gender gender;
    private LocalDate registrationDate;
    private Address address;


    public Client(String name, String surname, LocalDate birthday, Gender gender, Address address) {
        this.client_id = null;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.gender = gender;
        this.registrationDate = null;
        this.address = address;
    }
}
