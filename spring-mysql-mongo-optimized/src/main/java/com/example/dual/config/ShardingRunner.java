package com.example.dual.config;

import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShardingRunner implements CommandLineRunner {

    private final MongoClient mongoClient;
    private final boolean enable;

    public ShardingRunner(MongoClient mongoClient,
                          @Value("${app.mongo.sharding.enable:false}") boolean enable) {
        this.mongoClient = mongoClient;
        this.enable = enable;
    }

    @Override
    public void run(String... args) {
        if (!enable) return;
        try {
            mongoClient.getDatabase("admin").runCommand(new Document("enableSharding", "dualdb"));
            Document shardCmd = new Document("shardCollection", "dualdb.products")
                    .append("key", new Document("category", 1).append("sku", 1));
            mongoClient.getDatabase("admin").runCommand(shardCmd);
            System.out.println("Sharding ensured for dualdb.products on {category:1, sku:1}");
        } catch (Exception e) {
            System.out.println("Sharding commands failed (likely not on mongos): " + e.getMessage());
        }
    }
}
