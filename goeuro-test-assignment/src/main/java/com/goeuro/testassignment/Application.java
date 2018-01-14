package com.goeuro.testassignment;

import com.goeuro.testassignment.service.CsvSuggestionConverter;
import com.goeuro.testassignment.service.CsvSuggestionWriter;
import com.goeuro.testassignment.service.GoEuroApiClient;
import com.google.common.collect.ImmutableList;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private CsvSuggestionWriter csvSuggestionWriter;
    @Autowired
    private GoEuroApiClient goEuroApiClient;
    @Autowired
    private CsvSuggestionConverter csvSuggestionConverter;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build()));
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .bannerMode(Banner.Mode.OFF) // without Spring Boot banner
                .run(args);
        // if have class wich implements CommandLineRunner the Spring see method "run"
    }

    @Override
    public void run(String... args) throws Exception {
        String cityName = args[0].trim();
        String filename = cityName + ".csv";

        csvSuggestionWriter.write(filename, goEuroApiClient.findSuggestionsByCity(cityName).stream()
                .map(csvSuggestionConverter::toCsvSuggestionDto)
                .collect(collectingAndThen(toList(), ImmutableList::copyOf)));
    }
}
