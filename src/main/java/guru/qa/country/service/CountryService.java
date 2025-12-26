package guru.qa.country.service;

import guru.qa.country.controller.CountryDto;

import java.util.List;
import java.util.UUID;

public interface CountryService {
    List<CountryDto> getAllCountries();
    CountryDto addCountry(CountryDto country);
    UUID editCountry(String code, CountryDto country);
}