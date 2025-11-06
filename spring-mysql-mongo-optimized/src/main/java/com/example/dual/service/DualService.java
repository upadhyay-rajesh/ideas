package com.example.dual.service;

import com.example.dual.domain.sql.*;
import com.example.dual.domain.mongo.*;
import com.example.dual.repo.sql.*;
import com.example.dual.repo.mongo.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class DualService {
    private final CustomerRepository customerRepo;
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;

    public DualService(CustomerRepository c, OrderRepository o, ProductRepository p) {
        this.customerRepo = c; this.orderRepo = o; this.productRepo = p;
    }

    @Transactional public Customer upsertCustomer(Customer c) { return customerRepo.save(c); }

    public Page<Customer> searchCustomers(String q, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return customerRepo.findByNameContainingIgnoreCase(q, pageable);
    }

    public List<Object[]> orderTotalsSince(Instant from) { return orderRepo.sumAmountGroupedByStatus(from); }

    @Transactional public OrderSQL createOrder(OrderSQL o) { return orderRepo.save(o); }

    public Page<OrderSQL> findOrders(Long customerId, Instant from, Instant to, int page, int size) {
        return orderRepo.findByCustomerAndDateRange(customerId, from, to,
            PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    public Page<Product> productsByCategory(String category, int page, int size) {
        return productRepo.findByCategoryOrderByPriceAsc(category, PageRequest.of(page, size));
    }

    public List<Map<String, Object>> avgPrice(String category) { return productRepo.avgPriceByCategory(category); }

    public Product upsertProduct(Product p) { return productRepo.save(p); }
}
