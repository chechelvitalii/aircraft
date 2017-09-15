package com.cv.aircraft.repository.csv;

import com.cv.aircraft.model.AirportEntity;
import com.opencsv.CSVReader;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AirportRepository {

    @Value("${airport.storage.file}")
    private String airportStorageFile;

    @Getter(AccessLevel.PACKAGE)
    private final Map<String, AirportEntity> cacheAirportEntities = new HashMap();

    public Optional<AirportEntity> findAirportEntityByIataCode(String iataCode) {
        if (cacheAirportEntities.isEmpty()) {
            parsAndCacheAirportEntities();
        }
        return Optional.ofNullable(cacheAirportEntities.get(iataCode));
    }

    private void parsAndCacheAirportEntities() {
        try {
            InputStream resource = this.getClass().getClassLoader().getResourceAsStream(airportStorageFile);
            CSVReader reader = new CSVReader(new InputStreamReader(resource), '|');
            String[] line;
            while ((line = reader.readNext()) != null) {
                AirportEntity airportEntity = AirportEntity.builder()
                        .iataCode(line[1])
                        .cityRus(Optional.ofNullable(line[3].isEmpty() ? null : line[3]))
                        .cityEng(line[5])
                        .countryRus(line[7])
                        .countryEng(line[9])
                        .build();
                cacheAirportEntities.put(airportEntity.getIataCode(), airportEntity);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't read from path:" + airportStorageFile, e);
        }
    }
}
