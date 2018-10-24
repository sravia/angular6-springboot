package com.neo.util;

import com.neo.dto.CountryDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CountryParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryParser.class);
    private static final int TIMEOUT = 3000;
    private static final String WIKI_URL = "https://en.wikipedia.org/wiki/List_of_country_calling_codes";
    private Document document;

    public CountryParser() {
        try {
            java.net.URL url = new URL(WIKI_URL);
            document = Jsoup.parse(url, TIMEOUT);
        } catch (IOException e) {
            LOGGER.error("Unable to parse wiki: " + e.getMessage());
        }
    }

    public List<CountryDTO> parseCountries() {
        return this.parseCountries(document);
    }

    public List<CountryDTO> parseCountries(Document document) {
        List<CountryDTO> countries = new ArrayList<>();
        Element table = document.select("table").get(1);
        table.select("tr").stream()
                .map(row -> row.select("td"))
                .filter(tds -> tds.size() == 4)
                .collect(Collectors.toList())
                .forEach(element -> {
                    String name = element.get(0).text();
                    String code = element.get(1).select("a").text();
                    Pattern p = Pattern.compile("(\\+([0-9]+\\s?)*)");
                    Matcher m = p.matcher(code);
                    while (m.find()) {
                        int countryCode = Integer.parseInt(m.group(0).replaceAll("[\\s+\\+]", ""));
                        CountryDTO country = new CountryDTO(name, countryCode);
                        countries.add(country);
                    }
                });
        return countries;
    }

    public Document getDocument() {
        return this.document;
    }

}
