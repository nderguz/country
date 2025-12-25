package guru.qa.country.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, UUID> {
    Optional<CountryEntity> findByNameOrCode(String name, String code);

    Optional<CountryEntity> findByCode(String code);
}