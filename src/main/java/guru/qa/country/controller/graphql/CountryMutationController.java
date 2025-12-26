package guru.qa.country.controller.graphql;

import guru.qa.country.controller.CountryDto;
import guru.qa.country.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CountryMutationController {

    private final CountryService countryService;

    @MutationMapping
    public CountryDto addCountry(@Argument String name, @Argument String code) {
        return countryService.addCountry(CountryDto.builder()
                .name(name)
                .code(code)
                .build());
    }

    @MutationMapping
    public UUID editCountry(@Argument CountryDto country) {
        return countryService.editCountry(country.getCode(), country);
    }
}
