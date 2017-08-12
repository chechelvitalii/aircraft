package com.cv.aircraft.repository;

import com.cv.aircraft.model.AirportEntity;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AirportRepositoryIT {

    @Autowired
    private AirportRepository airportRepository;

    @Test
    public void shouldFindAirportEntityByIataCode() throws Exception {
        //GIVEN
        String iataCode = "IEV";
        //WHEN
        AirportEntity airportEntity = airportRepository.findAirportEntityByIataCode(iataCode);
        //THEN
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
                .forEach(airportEntity ->assertSatisfyAirportEntity(airportEntity));
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