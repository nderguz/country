package guru.qa.country.service;

import guru.qa.country.controller.CountryDto;
import guru.qa.country.repository.CountryEntity;
import guru.qa.country.repository.CountryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public List<CountryDto> all() {
        return countryRepository.findAll().stream()
                .map(e -> new CountryDto(e.getId(), e.getName(), e.getCode()))
                .toList();
    }

    @Override
    public CountryDto add(CountryDto country) {

        checkIfCountryAlreadyExists(country);

        var entityToSave = CountryEntity.builder()
                .code(country.getCode())
                .name(country.getName())
                .build();
        countryRepository.save(entityToSave);
        return CountryDto.fromEntity(entityToSave);
    }

    @Override
    public UUID update(String code, CountryDto country) {
        var foundEntity = countryRepository.findByCode(code);
        if (foundEntity.isPresent()) {
            foundEntity.get().setName(country.getName());
            countryRepository.save(foundEntity.get());
            return foundEntity.get().getId();
        } else {
            throw new EntityNotFoundException("Country with code %s not found".formatted(country.getCode()));
        }
    }

    private void checkIfCountryAlreadyExists(CountryDto country) {
        if (country.getId() != null) {
            throw new IllegalArgumentException("Country ID should be null");
        }

        var foundEntity = countryRepository.findByNameOrCode(country.getName(), country.getCode());

        if (foundEntity.isPresent()) {
            throw new IllegalArgumentException("Country with name %s and code %s already exists".formatted(country.getName(), country.getCode()));
        }
    }
}
