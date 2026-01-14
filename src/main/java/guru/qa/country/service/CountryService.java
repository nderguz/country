package guru.qa.country.service;

import guru.qa.country.controller.CountryDto;

import java.util.List;
import java.util.UUID;

public interface CountryService {
    List<CountryDto> all();
    CountryDto add(CountryDto country);
    UUID update(String code, CountryDto country);
}