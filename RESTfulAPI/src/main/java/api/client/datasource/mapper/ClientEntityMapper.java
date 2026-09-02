package api.client.datasource.mapper;

import api.address.datasource.mapper.AddressEntityMapper;
import api.address.datasource.model.AddressEntity;
import api.client.datasource.model.ClientEntity;
import api.client.domain.model.Client;

import java.time.OffsetDateTime;

public class ClientEntityMapper {

    static public ClientEntity toDatalayer(Client client) {
        ClientEntity entity = new ClientEntity();
        entity.setName(client.getName());
        entity.setSurname(client.getSurname());
        entity.setBirthday(client.getBirthday());
        entity.setGender(client.getGender());
        AddressEntity addressEntity = AddressEntityMapper.toDatalayer(client.getAddress());
        entity.setAddress(addressEntity);
        return entity;
    }

    static public Client toDomain(ClientEntity entity) {
        return new Client(
                entity.getId(),
                entity.getName(),
                entity.getSurname(),
                entity.getBirthday(),
                entity.getGender(),
                entity.getRegistrationDate().toLocalDate(),
                AddressEntityMapper.toDomain(entity.getAddress())
        );
    }
}
