package guru.qa.country.service;

import com.google.protobuf.Empty;
import guru.qa.country.repository.CountryEntity;
import guru.qa.country.repository.CountryRepository;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.grpc.server.service.GrpcService;
import quru.qa.country.*;

@GrpcService
@RequiredArgsConstructor
public class CountryGrpcService extends CountryServiceGrpc.CountryServiceImplBase {

    private final CountryRepository countryRepository;

    @Override
    public void all(Empty request, StreamObserver<CountryResponse> responseObserver) {
        var countries = countryRepository.findAll(Sort.by("name")).stream()
                .map(entity -> Country.newBuilder()
                        .setId(entity.getId().toString())
                        .setCode(entity.getCode())
                        .setName(entity.getName())
                        .build())
                .toList();

        responseObserver.onNext(
                CountryResponse.newBuilder()
                        .addAllCountry(countries)
                        .build()
        );

        responseObserver.onCompleted();
    }

    @Override
    public void add(NewCountry request, StreamObserver<Country> responseObserver) {
        checkIfCountryAlreadyExists(request.getName(), request.getCode());
        var newCountry = CountryEntity.builder()
                .name(request.getName())
                .code(request.getCode())
                .build();
        countryRepository.save(newCountry);
        responseObserver.onNext(
                Country.newBuilder()
                        .setId(newCountry.getId().toString())
                        .setName(newCountry.getName())
                        .setCode(newCountry.getCode())
                        .build()
        );

        responseObserver.onCompleted();
    }

    @Override
    public void update(Country request, StreamObserver<Country> responseObserver) {
        var countryEntity = countryRepository.findByNameOrCode(request.getName(), request.getCode())
                .orElseThrow(() -> new EntityNotFoundException("Country with name %s and code %s not found".formatted(request.getName(), request.getCode())));
        countryEntity.setName(request.getName());
        countryEntity.setCode(request.getCode());
        countryRepository.save(countryEntity);

        responseObserver.onNext(
                Country.newBuilder()
                        .setId(countryEntity.getId().toString())
                        .setName(countryEntity.getName())
                        .setCode(countryEntity.getCode())
                        .build()
        );

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<NewCountry> updateStream(StreamObserver<Count> responseObserver) {
        return new StreamObserver<>() {
            private int counter = 0;

            @Override
            public void onNext(NewCountry newCountry) {
                checkIfCountryAlreadyExists(newCountry.getName(), newCountry.getCode());
                var countryEntity = CountryEntity.builder()
                        .name(newCountry.getName())
                        .code(newCountry.getCode())
                        .build();
                countryRepository.save(countryEntity);
                counter++;
            }

            @Override
            public void onError(Throwable throwable) {
                throw new RuntimeException("Exception while streaming countries");
            }

            @Override
            public void onCompleted() {
                Count response = Count.newBuilder()
                        .setCount(counter)
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    private void checkIfCountryAlreadyExists(String name, String code) {
        var foundEntity = countryRepository.findByNameOrCode(name, code);

        if (foundEntity.isPresent()) {
            throw new IllegalArgumentException("Country with name %s and code %s already exists".formatted(name, code));
        }
    }
}
