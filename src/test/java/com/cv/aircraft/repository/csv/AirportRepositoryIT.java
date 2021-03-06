package com.cv.aircraft.repository.csv;

import com.cv.aircraft.model.AirportEntity;
import com.cv.aircraft.repository.csv.AirportRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@Ignore
public class AirportRepositoryIT {

    @Autowired
    private AirportRepository airportRepository;

    @Test
    public void shouldFindAirportEntityByIataCode() throws Exception {
        //GIVEN
        String iataCode = "IEV";
        //WHEN
        Optional<AirportEntity> maybeAirportEntity = airportRepository.findAirportEntityByIataCode(iataCode);
        //THEN
        assertTrue(maybeAirportEntity.isPresent());
        AirportEntity airportEntity = maybeAirportEntity.get();
        assertThat(airportEntity.getIataCode(), is(iataCode));
        assertThat(airportEntity.getCityEng(), is("Kiev"));
        assertTrue(airportEntity.getCityRus().isPresent());
        assertThat(airportEntity.getCityRus().get(), is("Киев"));
        assertThat(airportEntity.getCountryEng(), is("Ukraine"));
        assertThat(airportEntity.getCountryRus(), is("Украина"));
    }

    @Test
    public void shouldCheckThatOnlyCityRusFieldsMayBeEmpty() throws Exception {
        //GIVEN
        String iataCode = "IEV";
        //WHEN
        airportRepository.findAirportEntityByIataCode(iataCode);
        Map<String, AirportEntity> cacheAirportEntities = airportRepository.getCacheAirportEntities();
        //THEN
        cacheAirportEntities.values().stream()
                .forEach(airportEntity -> assertSatisfyAirportEntity(airportEntity));
    }

    private void assertSatisfyAirportEntity(AirportEntity airportEntity) {
        assertNotNull(airportEntity);
        assertThat(airportEntity.getIataCode(), not(isEmptyString()));
        assertThat(airportEntity.getCityEng(), not(isEmptyString()));
        airportEntity.getCityRus()
                .ifPresent(cityRus -> assertThat(cityRus, not(isEmptyString())));
        assertThat(airportEntity.getCountryEng(), not(isEmptyString()));
        assertThat(airportEntity.getCountryRus(), not(isEmptyString()));
    }
}