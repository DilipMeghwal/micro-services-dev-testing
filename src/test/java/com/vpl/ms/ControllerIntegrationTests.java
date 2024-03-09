package com.vpl.ms;

import com.vpl.ms.entity.Country;
import jakarta.persistence.Entity;
import jdk.jfr.ContentType;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerIntegrationTests {

    @Test
    @Order(1)
    public void getCountriesTest() throws JSONException {
        String exRes = "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"countryName\": \"India\",\n" +
                "        \"countryCapital\": \"New Delhi\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"countryName\": \"UK\",\n" +
                "        \"countryCapital\": \"London\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 3,\n" +
                "        \"countryName\": \"Germany\",\n" +
                "        \"countryCapital\": \"Berlin\"\n" +
                "    }\n" +
                "]";

        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> res = restTemplate.getForEntity("http://localhost:8090/getCountries", String.class);
        System.out.println(res.getStatusCode());
        System.out.println(res.getBody());

        JSONAssert.assertEquals(exRes, res.getBody(), false);
    }

    @Test
    @Order(2)
    public void addCountryTest() throws JSONException {
        Country country = new Country(4, "Sri Lanka", "Colombo");

        String exRes = "{\n" +
                "        \"id\": 4,\n" +
                "        \"countryName\": \"Sri Lanka\",\n" +
                "        \"countryCapital\": \"Colombo\"\n" +
                "    }";

        TestRestTemplate template = new TestRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Country> req = new HttpEntity<Country>(country, headers);

        ResponseEntity<String> res = template.postForEntity("http://localhost:8090/addCountry", req, String.class);

        System.out.println(res.getStatusCode());
        System.out.println(res.getBody());

        Assertions.assertEquals(HttpStatus.CREATED, res.getStatusCode(), "Verify status code");

        JSONAssert.assertEquals(exRes, res.getBody(), false);
    }

    @Test
    @Order(3)
    public void updateCountryTest() throws JSONException {
        Country country = new Country(4, "Sri Lanka", "Colombo1");

        String exRes = "{\n" +
                "        \"id\": 4,\n" +
                "        \"countryName\": \"Sri Lanka\",\n" +
                "        \"countryCapital\": \"Colombo1\"\n" +
                "    }";

        TestRestTemplate template = new TestRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Country> req = new HttpEntity<Country>(country, headers);

        ResponseEntity<String> res = template.exchange("http://localhost:8090/updateCountry", HttpMethod.PUT, req, String.class);

        System.out.println(res.getStatusCode());
        System.out.println(res.getBody());

        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode(), "Verify status code");

        JSONAssert.assertEquals(exRes, res.getBody(), false);
    }
}
