package com.neo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.dto.CountryDTO;
import com.neo.util.CountryParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HTTPRequestTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    private List<CountryDTO> countries;

    @Before
    public void setup() {
        CountryParser countryParser = new CountryParser();
        countries = countryParser.parseCountries();
    }

    @Test
    public void testGetCountries() throws Exception {
        String response = this.restTemplate.getForObject("http://localhost:" + port + "/api/countries",
                String.class);

        assertEquals(MAPPER.writeValueAsString(countries), response);
    }

    @Test
    public void testGetCountry() throws Exception {
        CountryDTO countryDTO = new CountryDTO("Latvia", 371);
        String response = this.restTemplate.getForObject("http://localhost:" + port + "/api/countries/" + countryDTO.getCountryCode(), String.class);

        assertEquals(MAPPER.writeValueAsString(countryDTO), response);
    }
}