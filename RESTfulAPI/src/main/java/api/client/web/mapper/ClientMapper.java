package api.client.web.mapper;

import api.address.web.mapper.AddressMapper;
import api.client.domain.model.Client;
import api.client.web.DTO.RequestClient;
import api.client.web.DTO.ResponseClient;

public class ClientMapper {
    static public Client toDomain(RequestClient clientDTO) {
        return new Client(
                clientDTO.getName(),
                clientDTO.getSurname(),
                clientDTO.getBirthday(),
                clientDTO.getGender(),
                AddressMapper.toDomain(clientDTO.getAddress())
        );
    }

    static public ResponseClient toWeb(Client client) {
        return new ResponseClient(
                client.getClient_id(),
                client.getName(),
                client.getSurname(),
                client.getBirthday(),
                client.getGender(),
                client.getRegistrationDate(),
                AddressMapper.toWeb(client.getAddress())
        );
    }
}
