package com.example.dual.repo.mongo;

import com.example.dual.domain.mongo.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Map;

public interface ProductRepository extends MongoRepository<Product, String> {
    Page<Product> findByCategoryOrderByPriceAsc(String category, Pageable pageable);
    Product findBySku(String sku);

    @Aggregation(pipeline = {
        "{ $match: { category: ?0 } }",
        "{ $group: { _id: '$category', avgPrice: { $avg: '$price' }, count: { $sum: 1 } } }"
    })
    List<Map<String, Object>> avgPriceByCategory(String category);
}
