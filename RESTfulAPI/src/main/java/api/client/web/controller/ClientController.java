package api.client.web.controller;

import api.address.web.DTO.RequestAddress;
import api.address.web.mapper.AddressMapper;
import api.client.domain.service.ClientService;
import api.client.web.DTO.RequestClient;
import api.client.web.DTO.ResponseClient;
import api.client.web.mapper.ClientMapper;
import api.exceptions.ClientNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clients")
@AllArgsConstructor
@Validated
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить клиента")
    public ResponseClient addClient(
            @RequestBody
            @Valid
            RequestClient requestClient
        ) {
        return ClientMapper.toWeb(clientService.addClient(ClientMapper.toDomain(requestClient)));
    }


    @DeleteMapping("/{clientUuid}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Удалить клиента")
    public ResponseClient deleteClient(
            @PathVariable
            @Schema(format = "uuid", description = "UUID клиента")
            UUID clientUuid
    ) throws ClientNotFoundException {
        return ClientMapper.toWeb(clientService.deleteClient(clientUuid));
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Найти клиента")
    public List<ResponseClient> getClient(
            @RequestParam
            @Size(max=64, message = "max length of name is 64 characters")
            @Schema(format="string", description = "Имя клиента")
            String name,

            @RequestParam(required = false)
            @Size(max = 64, message = "max length of surname is 64 characters")
            @Schema(format="string", description = "Фамилия клиента")
            String surname
            ) {
        return clientService.getClients(
                name, surname).stream()
                .map(ClientMapper::toWeb)
                .toList();
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить всех клиентов")
    public List<ResponseClient> getAllClients(
            @RequestParam(required = false)
            @Min(1)
            Integer limit,

            @RequestParam(required = false)
            @Min(0)
            Integer offset
            ) {
        if (limit == null || offset == null) {
            return clientService.getAll().stream()
                    .map(ClientMapper::toWeb)
                    .toList();
        }
        return clientService.getAllWithPagination(limit, offset).stream()
                .map(ClientMapper::toWeb)
                .toList();
    }

    @PatchMapping("/{clientUuid}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Сменить адрес клиента")
    public ResponseClient changeAddress(
            @PathVariable
            @Schema(format = "uuid", description = "UUID клиента")
            UUID clientUuid,

            @RequestBody
            @Valid
            RequestAddress newAddress
    ) throws ClientNotFoundException {
        return ClientMapper.toWeb(
                clientService.changeAddress(
                        clientUuid, AddressMapper.toDomain(newAddress)
                )
        );
    }
}
