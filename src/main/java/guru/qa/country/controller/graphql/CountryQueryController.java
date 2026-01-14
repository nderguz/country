package guru.qa.country.controller.graphql;

import guru.qa.country.controller.CountryDto;
import guru.qa.country.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CountryQueryController {

    private final CountryService countryService;

    @QueryMapping
    public List<CountryDto> getAllCountries() {
        return countryService.all();
    }
}
