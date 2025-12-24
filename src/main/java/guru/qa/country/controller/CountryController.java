package guru.qa.country.controller;

import guru.qa.country.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/all")
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        return ResponseEntity.ok().body(countryService.getAllCountries());
    }

    @PostMapping("/add")
    public ResponseEntity<CountryDto> addCountry(@RequestBody CountryDto country) {
        return ResponseEntity.status(HttpStatus.CREATED).body(countryService.addCountry(country));
    }

    @PatchMapping("/edit")
    public ResponseEntity<Void> editCountry(@RequestBody CountryDto country) {
        countryService.editCountry(country);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
