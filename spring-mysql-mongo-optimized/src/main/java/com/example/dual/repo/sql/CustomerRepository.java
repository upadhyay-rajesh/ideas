package com.example.dual.repo.sql;

import com.example.dual.domain.sql.Customer;
import com.example.dual.domain.sql.OrderSQL;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.time.Instant;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findByNameContainingIgnoreCase(String q, Pageable pageable);
    Customer findByEmail(String email);
}

