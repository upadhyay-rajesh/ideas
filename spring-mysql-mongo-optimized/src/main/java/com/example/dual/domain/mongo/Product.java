package com.example.dual.domain.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "products")
@CompoundIndexes({
    @CompoundIndex(name="cat_price_idx", def="{ 'category': 1, 'price': 1 }")
})
public class Product {
    @Id private String id;
    @Indexed(unique = true, sparse = true) private String sku;
    @Indexed private String category;
    private String name; private double price;
    @Indexed(expireAfterSeconds = 0, partialFilter = "{ 'ttlAt': { $exists: true } }")
    private Instant ttlAt;
    @Indexed private Instant createdAt = Instant.now();
    public String getId(){return id;} public void setId(String id){this.id=id;}
    public String getSku(){return sku;} public void setSku(String sku){this.sku=sku;}
    public String getCategory(){return category;} public void setCategory(String category){this.category=category;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public double getPrice(){return price;} public void setPrice(double price){this.price=price;}
    public Instant getTtlAt(){return ttlAt;} public void setTtlAt(Instant ttlAt){this.ttlAt=ttlAt;}
    public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant t){this.createdAt=t;}
}
