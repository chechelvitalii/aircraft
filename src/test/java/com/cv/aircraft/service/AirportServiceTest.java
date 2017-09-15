package com.cv.aircraft.service;

import com.cv.aircraft.model.AirportEntity;
import com.cv.aircraft.repository.AirportRepository;
import com.cv.aircraft.service.AirportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class AirportServiceTest {
    @InjectMocks
    private AirportService airportService;
    @Mock
    private AirportRepository airportRepository;

    @Test
    public void shouldGetCityAndCountryNameByIataCode() throws Exception {
        //GIVEN
        String iataCode = "IataCode";
        AirportEntity airportEntity = AirportEntity.builder()
                .cityEng("city")
                .countryEng("country").build();
        when(airportRepository.findAirportEntityByIataCode(iataCode)).thenReturn(of(airportEntity));
        //WHEN
        String cityAndCountryName = airportService.getCityAndCountryNameByIataCode(iataCode);
        //THEN
        assertThat(cityAndCountryName, is("city (country)"));
    }

    @Test
    public void shouldGetNOTFOUNDCityAndCountryNameIfAirportNotFoundByIataCode() throws Exception {
        //GIVEN
        String iataCode = "WrongIataCode";
        when(airportRepository.findAirportEntityByIataCode(iataCode)).thenReturn(ofNullable(null));
        //WHEN
        String cityAndCountryName = airportService.getCityAndCountryNameByIataCode(iataCode);
        //THEN
        assertThat(cityAndCountryName, is("NotFoundCity (NotFoundCountry)"));
    }

}