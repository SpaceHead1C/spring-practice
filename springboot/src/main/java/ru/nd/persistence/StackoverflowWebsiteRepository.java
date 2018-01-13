package ru.nd.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.nd.model.StackoverflowWebsite;

import java.util.List;

@Repository
public interface StackoverflowWebsiteRepository extends MongoRepository<StackoverflowWebsite, String> {
    // Spring create special method by name of my method
    List<StackoverflowWebsite> findByWebsite(String website);

    //StackoverflowWebsite findFirstByWebsite(String website);

//    public List<StackoverflowWebsite> findAll() {
//        return mongoTemplate.findAll(StackoverflowWebsite.class);
//    }
}
