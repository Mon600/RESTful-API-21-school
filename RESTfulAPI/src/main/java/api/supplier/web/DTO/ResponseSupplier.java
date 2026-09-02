package api.supplier.web.DTO;

import api.address.web.DTO.ResponseAddress;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSupplier {
    @Schema(example = "3afba4e5-4aaa-4abe-ad7a-cbf9f3bada77")
    public UUID id;
    @Schema(example = "Alex")
    public String name;
    public ResponseAddress address;
    @Schema(example = "73529132739")
    public String number;
}
