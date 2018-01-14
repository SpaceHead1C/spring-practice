package com.goeuro.testassignment;

import com.google.common.collect.ImmutableList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        // if have class wich implements CommandLineRunner the Spring run method "run" automaticly
    }

    @Override
    public void run(String... args) throws Exception {
        String cityName = args[0].trim();
        String filename = cityName + ".csv";

        csvSuggestionWriter.write(filename, goEuroApiClient.findSuggestionsByCity().stream()
                .map(csvSuggestionConverter::toCsvSuggestionDto)
                .collect(collectingAndThen(toList(), ImmutableList::copyOf)));
    }
}
