package api.address.web.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@AllArgsConstructor
@Getter
@Setter
public class ResponseAddress {
    private UUID address_id;

    private String country;

    private String city;

    private String street;

}
