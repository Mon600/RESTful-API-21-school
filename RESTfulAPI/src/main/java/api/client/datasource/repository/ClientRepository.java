package api.client.datasource.repository;

import api.client.datasource.model.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {


    @Query(value = "SELECT * FROM client WHERE (:name IS NULL OR client_name = :name) AND (:surname IS NULL OR client_surname = :surname)", nativeQuery = true)
    public List<ClientEntity> findByNameAndSurname(@Param("name") String name, @Param("surname")String surname);

    @Query(value = "SELECT * FROM client ORDER BY id LIMIT :limit OFFSET :offset", nativeQuery = true)
    public List<ClientEntity> findAll(@Param("limit") Integer limit, @Param("offset") Integer offset);
}
