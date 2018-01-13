package ru.nd.web;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nd.Application;
import ru.nd.model.StackoverflowWebsite;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // for Spring boot
//@SpringApplicationConfiguration(Application.class)
//@WebIntegrationTest
public class StackoverflowControllerIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void init() {
        mongoTemplate.dropCollection(StackoverflowWebsite.class);
        mongoTemplate.save(new StackoverflowWebsite("website1", "website", "icon",
                "title", "description"));
        mongoTemplate.save(new StackoverflowWebsite("website2", "website", "icon",
                "title", "description"));
    }

    @Test
    public void getListOfProviders() throws Exception {
        //test
        ResponseEntity<List<StackoverflowWebsite>> responseEntity = restTemplate.exchange(
                "http://localhost:777/api/stackoverflow", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<StackoverflowWebsite>>() {
                });
        List<StackoverflowWebsite> actualList = responseEntity.getBody();
        //validate
        assertThat(actualList.size(), is(2));
        // Returning immutable list using stream
        List<String> actualIds = actualList.stream()
                .map(StackoverflowWebsite::getId)
                .collect(collectingAndThen(toList(), ImmutableList::copyOf));
        assertThat(actualIds, containsInAnyOrder("website2", "website1"));
    }
}