package guru.qa.country.service;

import guru.qa.country.controller.CountryDto;

import java.util.List;

public interface CountryService {
    List<CountryDto> getAllCountries();
    CountryDto addCountry(CountryDto country);
    void editCountry(String code, CountryDto country);
}