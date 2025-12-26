package guru.qa.country.controller.rest;

import guru.qa.country.controller.CountryDto;
import guru.qa.country.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @PostMapping
    public ResponseEntity<CountryDto> addCountry(@RequestBody CountryDto country) {
        return ResponseEntity.status(HttpStatus.CREATED).body(countryService.addCountry(country));
    }

    @PatchMapping("/{code}")
    public ResponseEntity<Void> editCountry(@PathVariable("code") String code,
                                            @RequestBody CountryDto country) {
        countryService.editCountry(code, country);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
