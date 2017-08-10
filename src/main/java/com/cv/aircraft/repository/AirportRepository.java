package com.cv.aircraft.repository;

import com.cv.aircraft.model.AirportEntity;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AirportRepository {
    private final Map<String, AirportEntity> cacheAirportEntities = new HashMap();

    public AirportEntity findAirportEntityByIataCode(String iataCode) {
        if (cacheAirportEntities.isEmpty()) {
            parsAndCacheAirportEntities();
        }
        return cacheAirportEntities.get(iataCode);
    }

    private void parsAndCacheAirportEntities() {
        String path = "rawDoc/airports.csv";
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(this.getClass().getResource(path).getFile()), '|');
            String[] line;
            while ((line = reader.readNext()) != null) {
                AirportEntity airportEntity = AirportEntity.builder()
                        .iataCode(line[1])
                        .cityRus(Optional.of(line[3]))
                        .cityEng(line[5])
                        .countryRus(line[7])
                        .countryEng(line[9])
                        .build();
                cacheAirportEntities.put(airportEntity.getIataCode(), airportEntity);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't read from path:" + path, e);
        }
    }
}
