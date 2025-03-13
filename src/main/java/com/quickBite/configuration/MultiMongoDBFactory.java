package com.quickBite.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.quickBite.exception.BadRequestException;
import com.quickBite.exception.InvalidDatabaseAccessException;
import com.quickBite.primary.pojo.Vendor;
import com.quickBite.primary.service.VendorService;
import com.quickBite.utils.TextUtils;
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

    public MongoTemplate getVendorDbConnection(String vendorId) throws BadRequestException {
        MongoTemplate mongoTemplate;
        if (dataBaseObjects.containsKey(vendorId)) {
            mongoTemplate = dataBaseObjects.get(vendorId);
            return mongoTemplate;
        }
        try {
            Vendor vendor = SpringBeanContext.getBean(VendorService.class).findById(vendorId);
            Vendor.Database database = vendor.getDatabaseDetail();
            if (!vendor.isActive()) {
                dataBaseObjects.remove(vendorId);
                throw new InvalidDatabaseAccessException("Vendor Is Temporary Blocked Please Contact To Admin.");
            }
            if (database == null) {
                throw new InvalidDatabaseAccessException("Invalid DB credentials.");
            }
            if (TextUtils.isEmpty(database.getDatabaseUri())) {
                throw new InvalidDatabaseAccessException("Invalid DB path provided.");
            }
            if (TextUtils.isEmpty(database.getDatabaseName())) {
                throw new InvalidDatabaseAccessException("Invalid DB name provided.");
            }
            mongoTemplate = getDatabase(database.getDatabaseUri(), database.getDatabaseName());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        dataBaseObjects.put(vendorId, mongoTemplate);
        return mongoTemplate;
    }
}