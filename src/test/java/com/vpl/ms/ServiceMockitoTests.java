package com.vpl.ms;

import com.vpl.ms.entity.Country;
import com.vpl.ms.repositories.CountryRepository;
import com.vpl.ms.services.CountryService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ServiceMockitoTests.class})
public class ServiceMockitoTests {
    @Mock
    CountryRepository countryRepository;

    @InjectMocks
    CountryService countryService;

    List<Country> countries;

    @Test
    @Order(1)
    public void testGetAllCountries(){
        countries = new ArrayList<>();
        countries.add(new Country(1, "India", "New Delhi"));
        countries.add(new Country(2, "UK", "London"));

        when(countryRepository.findAll()).thenReturn(countries);
        Assertions.assertEquals(2, countryService.getAllCountries().size(), "Verify total count of records");
    }

    @Test
    @Order(2)
    public void testGetCountryById(){
        countries = new ArrayList<>();
        countries.add(new Country(1, "India", "New Delhi"));
        countries.add(new Country(2, "UK", "London"));

        when(countryRepository.findById(1)).thenReturn(Optional.ofNullable(countries.get(0)));
        Assertions.assertEquals(1, countryService.getCountry(1).getId(), "Verify get country by id ids are same");
    }

    @Test
    @Order(3)
    public void testGetCountryByName(){
        countries = new ArrayList<>();
        countries.add(new Country(1, "India", "New Delhi"));
        countries.add(new Country(2, "UK", "London"));

        when(countryRepository.findAll()).thenReturn(countries);
        Assertions.assertEquals("London", countryService.getCountryByName("UK").getCountryCapital(), "Verify country capital");
    }

    @Test
    @Order(4)
    public void testAddCountry(){
        Country country = new Country(3, "USA", "Washington");

        when(countryRepository.save(country)).thenReturn(country);

        Assertions.assertEquals(country, countryService.addCountry(country), "Verify add country");
    }

    @Test
    @Order(5)
    public void testUpdateCountry(){
        Country country = new Country(3, "Germany", "Berlin");

        when(countryRepository.save(country)).thenReturn(country);

        Assertions.assertEquals(country, countryService.addCountry(country), "Verify update country");
    }

    @Test
    @Order(6)
    public void testDeleteCountry(){
        countries = new ArrayList<>();
        countries.add(new Country(1, "India", "New Delhi"));
        countries.add(new Country(2, "UK", "London"));
        countryService.deleteCountry(1);
        verify(countryRepository, times(1)).deleteById(1);

        Assertions.assertEquals("Country Deleted", countryService.deleteCountry(1).getMessage(), "Verify country deleted message");
    }
}
