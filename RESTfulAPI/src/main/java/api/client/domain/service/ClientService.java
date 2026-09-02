package api.client.domain.service;


import api.address.datasource.model.AddressEntity;

import api.address.domain.model.Address;
import api.address.domain.service.AddressService;
import api.client.datasource.mapper.ClientEntityMapper;
import api.client.datasource.model.ClientEntity;
import api.client.datasource.repository.ClientRepository;
import api.client.domain.model.Client;


import api.exceptions.ClientNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@AllArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final AddressService addressService;

    @Transactional
    public Client addClient(Client client) {
        Address address = client.getAddress();
        Optional<AddressEntity> existAddressEntityOpt = addressService.getAddressByCountryAndCityAndStreet(client.getAddress());
        AddressEntity addressEntity;
        addressEntity = existAddressEntityOpt.orElseGet(() -> addressService.addAddress(address));
        ClientEntity newClient = ClientEntityMapper.toDatalayer(client);
        newClient.setAddress(addressEntity);
        ClientEntity savedClient = clientRepository.save(newClient);
        return ClientEntityMapper.toDomain(savedClient);
    }

    @Transactional
    public Client deleteClient(UUID clientId) throws ClientNotFoundException {
        Optional<ClientEntity> existsClient = clientRepository.findById(clientId);
        if (existsClient.isEmpty()) {
            throw new ClientNotFoundException("Client not found");
        }
        clientRepository.deleteById(clientId);
        return ClientEntityMapper.toDomain(existsClient.get());
    }

    @Transactional(readOnly = true)
    public List<Client> getClients(String name, String surname) {
        List<ClientEntity> clients = clientRepository.findByNameAndSurname(name, surname);
        return clients.stream().map(ClientEntityMapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public List<Client> getAll() {
        List<ClientEntity> clients = clientRepository.findAll();
        return clients.stream().map(ClientEntityMapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public List<Client> getAllWithPagination(Integer limit, Integer offset) {
        List<ClientEntity> clients = clientRepository.findAll(limit, offset);
        return clients.stream().map(ClientEntityMapper::toDomain).toList();
    }

    @Transactional
    public Client changeAddress(UUID clientId, Address address) throws ClientNotFoundException {
        Optional<ClientEntity> existsClient = clientRepository.findById(clientId);
        if (existsClient.isEmpty()) {
            throw new ClientNotFoundException("Client not found");
        }
        ClientEntity clientEntity = existsClient.get();

        Optional<AddressEntity> existsAddress = addressService.getAddressByCountryAndCityAndStreet(address);
        AddressEntity addressEntity = existsAddress.orElseGet(() -> addressService.addAddress(address));
        clientEntity.setAddress(addressEntity);

        ClientEntity savedClient = clientRepository.save(clientEntity);
        return ClientEntityMapper.toDomain(savedClient);
    }
}
