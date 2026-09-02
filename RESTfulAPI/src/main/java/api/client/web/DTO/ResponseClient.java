package api.client.web.DTO;


import api.address.web.DTO.ResponseAddress;
import api.common.domain.enums.Gender;
import lombok.AllArgsConstructor;


import java.time.LocalDate;
import java.util.UUID;


@AllArgsConstructor
public class ResponseClient {
    public UUID client_id;
    public String name;
    public String surname;
    public LocalDate birthday;
    public Gender gender;
    public LocalDate registrationDate;
    public ResponseAddress address;
}
