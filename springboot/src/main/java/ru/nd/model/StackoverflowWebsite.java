package ru.nd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data // activate Lombok and generate default constructor by default, getters and setters
@AllArgsConstructor // create constructor with all arguments only if not set @NoArgsConstructor
@NoArgsConstructor // create default constructor anyway
@Document // for MongoDB
public class StackoverflowWebsite {
    @Id
    private String id;
    private String website;
    private String iconImageUrl;
    private String title;
    private String description;
}
