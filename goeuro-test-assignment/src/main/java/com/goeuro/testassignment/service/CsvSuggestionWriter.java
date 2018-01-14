package com.goeuro.testassignment.service;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.goeuro.testassignment.dto.CsvSuggestionDto;
import lombok.Cleanup;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

@Component
public class CsvSuggestionWriter {
    private CsvMapper csvMapper = new CsvMapper();
    private CsvSchema schema = csvMapper
            .schemaFor(CsvSuggestionDto.class)
            .withHeader()
            .sortedBy("_id", "name", "type", "latitude", "longitude");

    public void write(@NonNull String filename, @NonNull List<CsvSuggestionDto> data) {
        try {
            // This annotation for generating code to close IO-objects
            @Cleanup Writer writer = new PrintWriter(new FileWriter(filename)); // with love from Lombok.
            doWrite(writer, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // method created for testing
    private void doWrite(@NonNull Writer writer, @NonNull List<CsvSuggestionDto> data) throws IOException {
        csvMapper.writer().with(schema).writeValues(writer).writeAll(data);
    }
}
