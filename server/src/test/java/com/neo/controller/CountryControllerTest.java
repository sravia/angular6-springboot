package com.neo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.dto.CountryDTO;
import com.neo.service.CountryService;
import com.neo.util.CountryParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CountryController.class)
public class CountryControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CountryService countryService;
    private List<CountryDTO> countries;

    @Before
    public void setup() {
        CountryParser countryParser = new CountryParser();
        countries = countryParser.parseCountries();
    }

    @Test
    public void testGetCountries() throws Exception {
        when(countryService.getCountries()).thenReturn(countries);

        MockHttpServletResponse response = mockMvc.perform(get("/api/countries")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        List<CountryDTO> countries = MAPPER.readValue(response.getContentAsString(), new TypeReference<List<CountryDTO>>() {
        });
        assertThat(countryService.getCountries().size(), is(countries.size()));
    }

    @Test
    public void testGetCountry() throws Exception {
        CountryDTO countryDTO = new CountryDTO("Latvia", 371);
        when(countryService.getCountry(371)).thenReturn(Optional.of(countryDTO));

        MockHttpServletResponse response = mockMvc.perform(get("/api/countries/" + countryDTO.getCountryCode())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        CountryDTO responseCountryDTO = MAPPER.readValue(response.getContentAsString(), CountryDTO.class);
        assertEquals(countryDTO.getCountryCode(), responseCountryDTO.getCountryCode());
        assertEquals(countryDTO.getName(), responseCountryDTO.getName());
    }

}