package guru.qa.country.controller;

import guru.qa.country.repository.CountryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryDto {
    private UUID id;
    private String name;
    private String code;

    public static CountryDto fromEntity(CountryEntity entity) {
        return CountryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .code(entity.getCode())
                .build();
    }
}