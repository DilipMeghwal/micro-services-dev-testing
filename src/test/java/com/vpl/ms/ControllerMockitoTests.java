package com.vpl.ms;

import com.vpl.ms.controllers.CountryController;
import com.vpl.ms.dto.ResponseMessage;
import com.vpl.ms.entity.Country;
import com.vpl.ms.services.CountryService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ControllerMockitoTests.class})
public class ControllerMockitoTests {
    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    List<Country> countries;
    Country country;

    @Test
    @Order(1)
    public void testGetAllCountries(){
        countries = new ArrayList<>();
        countries.add(new Country(1, "India", "New Delhi"));
        countries.add(new Country(2, "UK", "London"));

        when(countryService.getAllCountries()).thenReturn(countries);
        ResponseEntity<List<Country>> res = countryController.getCountries();

        Assertions.assertEquals(HttpStatus.FOUND, res.getStatusCode(), "Verify status code");
        Assertions.assertEquals(2, res.getBody().size(), "Verify response size");
    }

    @Test
    @Order(2)
    public void testGetCountryById(){
        countries = new ArrayList<>();
        countries.add(new Country(1, "India", "New Delhi"));
        countries.add(new Country(2, "UK", "London"));

        country = countries.get(0);
        when(countryService.getCountry(1)).thenReturn(country);
        ResponseEntity<Country> res = countryController.getCountry(1);

        Assertions.assertEquals(HttpStatus.FOUND, res.getStatusCode(), "Verify status code");
        Assertions.assertEquals(1, res.getBody().getId(), "Verify response country id");
    }

    @Test
    @Order(3)
    public void testGetCountryByName(){
        countries = new ArrayList<>();
        countries.add(new Country(1, "India", "New Delhi"));
        countries.add(new Country(2, "UK", "London"));

        country = countries.get(1);
        when(countryService.getCountryByName("UK")).thenReturn(country);
        ResponseEntity<Country> res = countryController.getCountryByName("UK");

        Assertions.assertEquals(HttpStatus.FOUND, res.getStatusCode(), "Verify status code");
        Assertions.assertEquals("UK", res.getBody().getCountryName(), "Verify response country name");
    }

    @Test
    @Order(4)
    public void testAddCountry(){
        countries = new ArrayList<>();
        countries.add(new Country(1, "India", "New Delhi"));
        countries.add(new Country(2, "UK", "London"));

        country = new Country(3, "Germany", "Berlin");

        when(countryService.addCountry(country)).thenReturn(country);
        ResponseEntity<Country> res = countryController.addCountry(country);

        Assertions.assertEquals(HttpStatus.CREATED, res.getStatusCode(), "Verify status code");
        Assertions.assertEquals(country.getCountryName(), res.getBody().getCountryName(), "Verify response country name");
    }

    @Test
    @Order(5)
    public void testUpdateCountry(){
        countries = new ArrayList<>();
        countries.add(new Country(1, "India", "New Delhi"));
        countries.add(new Country(2, "UK", "London"));

        country = new Country(3, "Japan", "Tokyo");

        when(countryService.getCountry(3)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);
        ResponseEntity<Country> res = countryController.updateCountry(country);

        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode(), "Verify status code");
        Assertions.assertEquals(country.getCountryName(), res.getBody().getCountryName(), "Verify response country name");
    }

    @Test
    @Order(6)
    public void testDeleteCountry(){
        countries = new ArrayList<>();
        countries.add(new Country(1, "India", "New Delhi"));
        countries.add(new Country(2, "UK", "London"));

        country = new Country(3, "Japan", "Tokyo");

        when(countryService.getCountry(3)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);
        ResponseEntity<ResponseMessage> res = countryController.deleteCountry(3);

        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode(), "Verify status code");
    }
}
