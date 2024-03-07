package com.vpl.ms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpl.ms.controllers.CountryController;
import com.vpl.ms.dto.ResponseMessage;
import com.vpl.ms.entity.Country;
import com.vpl.ms.services.CountryService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.vpl.ms")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = {ControllerMockitoTests.class})
public class ControllerMockMvcTests {
    @Autowired
    MockMvc mockMvc;

    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    List<Country> countries;
    Country country;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    @Test
    @Order(1)
    public void getAllCountries() throws Exception {
        countries = new ArrayList<>();
        countries.add(new Country(1, "India", "New Delhi"));
        countries.add(new Country(2, "UK", "London"));

        when(countryService.getAllCountries()).thenReturn(countries);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/getCountries"))
                .andExpect(status().isFound())
                .andDo(print());
    }

    @Test
    @Order(2)
    public void getCountryById() throws Exception {
        countries = new ArrayList<>();
        countries.add(new Country(1, "India", "New Delhi"));
        countries.add(new Country(2, "UK", "London"));

        when(countryService.getCountry(1)).thenReturn(countries.get(0));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/getCountries/{id}", 1))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
                    .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("India"))
                    .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("New Delhi"))
                .andDo(print());
    }

    @Test
    @Order(3)
    public void getCountryByName() throws Exception {
        countries = new ArrayList<>();
        countries.add(new Country(1, "India", "New Delhi"));
        countries.add(new Country(2, "UK", "London"));

        when(countryService.getCountryByName("India")).thenReturn(countries.get(0));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/getCountries/countryName").param("name","India"))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("India"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("New Delhi"))
                .andDo(print());
    }

    @Test
    @Order(4)
    public void getAddCountry() throws Exception {
        countries = new ArrayList<>();
        countries.add(new Country(1, "India", "New Delhi"));
        countries.add(new Country(2, "UK", "London"));

        country = new Country(3, "Germany", "Berlin");

        when(countryService.addCountry(country)).thenReturn(country);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(country);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/addCountry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Germany"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Berlin"))
                .andDo(print());
    }

    @Test
    @Order(5)
    public void getUpdateCountry() throws Exception {
        countries = new ArrayList<>();
        countries.add(new Country(1, "India", "New Delhi"));
        countries.add(new Country(2, "UK", "London"));

        country = countries.get(1);
        country.setCountryName("United Kingdom");
        country.setCountryCapital("London");

        when(countryService.getCountry(2)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(country);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/updateCountry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("United Kingdom"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("London"))
                .andDo(print());
    }

    @Test
    @Order(6)
    public void getDeleteCountry() throws Exception {
        countries = new ArrayList<>();
        countries.add(new Country(1, "India", "New Delhi"));
        country = countries.get(0);

        ResponseMessage res = new ResponseMessage();
        res.setMessage("Country Deleted");
        res.setId(country.getId());

        when(countryService.deleteCountry(1)).thenReturn(res);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/deleteCountry/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath(".message").value("Country Deleted"))
                .andDo(print());
    }
}
