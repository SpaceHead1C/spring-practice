package ru.nd.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nd.model.StackoverflowWebsite;
import ru.nd.service.StackoverflowService;

import java.util.List;

@RestController
@RequestMapping("/api/stackoverflow")
public class StackoverflowController {
    @Autowired
    private StackoverflowService stackoverflowService;

    @RequestMapping
    //Have @ResponseBody already cuz have @RestController
    public List<StackoverflowWebsite> getListOfProviders() {
        return stackoverflowService.findAll();
    }
}
