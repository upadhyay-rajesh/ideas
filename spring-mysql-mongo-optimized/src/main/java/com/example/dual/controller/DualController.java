package com.example.dual.controller;

import com.example.dual.domain.sql.*;
import com.example.dual.domain.mongo.*;
import com.example.dual.service.DualService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DualController {

    private final DualService svc;
    public DualController(DualService svc) { this.svc = svc; }

    @PostMapping("/customers")
    public Customer upsertCustomer(@RequestBody Customer c) { return svc.upsertCustomer(c); }

    @GetMapping("/customers")
    public Page<Customer> searchCustomers(@RequestParam String q,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return svc.searchCustomers(q, page, size);
    }

    @PostMapping("/orders")
    public OrderSQL createOrder(@RequestBody OrderSQL o) { return svc.createOrder(o); }

    @GetMapping("/orders")
    public Page<OrderSQL> findOrders(@RequestParam Long customerId,
                                     @RequestParam String from,
                                     @RequestParam String to,
                                     @RequestParam(defaultValue="0") int page,
                                     @RequestParam(defaultValue="10") int size) {
        return svc.findOrders(customerId, Instant.parse(from), Instant.parse(to), page, size);
    }

    @GetMapping("/orders/totals")
    public List<Object[]> totalsSince(@RequestParam String from) { return svc.orderTotalsSince(Instant.parse(from)); }

    @PostMapping("/products")
    public Product upsertProduct(@RequestBody Product p) { return svc.upsertProduct(p); }

    @GetMapping("/products")
    public Page<Product> byCategory(@RequestParam String category,
                                    @RequestParam(defaultValue="0") int page,
                                    @RequestParam(defaultValue="10") int size) {
        return svc.productsByCategory(category, page, size);
    }

    @GetMapping("/products/avg")
    public List<Map<String, Object>> avgPrice(@RequestParam String category) { return svc.avgPrice(category); }
}
