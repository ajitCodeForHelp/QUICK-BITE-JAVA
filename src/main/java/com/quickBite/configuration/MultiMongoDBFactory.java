package com.quickBite.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.quickBite.exception.BadRequestException;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiMongoDBFactory {

    private MongoClient mongoClient(final String dbUri) {
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(dbUri))
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    private MongoTemplate getDatabase(String dbUri, String pDataBaseName) {
        MongoClient mongoClient = mongoClient(dbUri);
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, pDataBaseName);
        return mongoTemplate;
    }

    // BrandUUID > MongoTemplate
    Map<String, MongoTemplate> dataBaseObjects = new HashMap<>();
    // TODO > Future > Redis
    // TODO > Future > dataBaseObjects.clear() || dataBaseObjects > Connection Pooling

    public MongoTemplate getClientDbConnection(String clientUuid) throws BadRequestException {
        MongoTemplate mongoTemplate;
        if (dataBaseObjects.containsKey(clientUuid)) {
            mongoTemplate = dataBaseObjects.get(clientUuid);
            return mongoTemplate;
        }
        // TODO > In case of Brand Inactive or Delete > just remove connection from dataBaseObjects
        try {
//            Brand brand = SpringBeanContext.getBean(BrandService.class).findByUuidWithActiveAndDeletedValidation(brandUuid);
//            Database database = brand.getDatabaseDetails();
//            if (database == null) {
//                throw new InvalidDatabaseAccessException("Invalid DB credentials");
//            }
//            if (TextUtils.isEmpty(database.getDatabaseUri())) {
//                throw new InvalidDatabaseAccessException("Invalid DB path provided.");
//            }
//            if (TextUtils.isEmpty(database.getDatabaseName())) {
//                throw new InvalidDatabaseAccessException("Invalid DB name provided.");
//            }
//            mongoTemplate = getDatabase(database.getDatabaseUri(), database.getDatabaseName());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
//        dataBaseObjects.put(brandUuid, mongoTemplate);
//        return mongoTemplate;
        return null;
    }

    public void removeClientBrandDbConnection(String brandUuid) {
//        dataBaseObjects.remove(brandUuid);
    }

    // TODO > Check Connection counts are not increasing gradually
}