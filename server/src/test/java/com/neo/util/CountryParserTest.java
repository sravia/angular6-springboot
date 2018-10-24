package com.neo.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.dto.CountryDTO;
import org.hamcrest.collection.IsEmptyCollection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class CountryParserTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private List<CountryDTO> countriesFromJson;

    @Before
    public void setup() throws URISyntaxException, IOException {
        String countriesJSON = new String(Files.readAllBytes(Paths.get(getClass().getResource("/testCountriesResponse.json").toURI())));
        countriesFromJson = MAPPER.readValue(countriesJSON, new TypeReference<List<CountryDTO>>() {});
    }

    @Test
    public void testParseCountries() throws URISyntaxException, IOException {
        String countriesWikiHTML = new String(Files.readAllBytes(Paths.get(getClass().getResource("/testCountriesWiki.html").toURI())));
        Document document = Jsoup.parse(countriesWikiHTML);
        CountryParser countryParser = new CountryParser();

        List<CountryDTO> parsedCountries = countryParser.parseCountries(document);
        assertThat(parsedCountries, not(IsEmptyCollection.empty()));
        assertThat(countriesFromJson, not(IsEmptyCollection.empty()));
        assertThat(parsedCountries.size(), is(countriesFromJson.size()));
    }

    @Test
    public void testParserWiki() {
        CountryParser countryParser = new CountryParser();
        List<CountryDTO> parsedCountries = countryParser.parseCountries();

        assertThat(parsedCountries, not(IsEmptyCollection.empty()));
        assertThat(countriesFromJson, not(IsEmptyCollection.empty()));
        assertThat(parsedCountries.size(), is(countriesFromJson.size()));
    }
}