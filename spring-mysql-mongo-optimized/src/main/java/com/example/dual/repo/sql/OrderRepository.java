package com.example.dual.repo.sql;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.dual.domain.sql.OrderSQL;

public interface OrderRepository extends JpaRepository<OrderSQL, Long> {
    @Query("select o from OrderSQL o where o.customer.id = :customerId and o.createdAt between :from and :to")
    Page<OrderSQL> findByCustomerAndDateRange(@Param("customerId") Long customerId,
                                              @Param("from") Instant from,
                                              @Param("to") Instant to,
                                              Pageable pageable);

    @Query("select o.status as status, sum(o.amount) as total from OrderSQL o where o.createdAt >= :from group by o.status")
    List<Object[]> sumAmountGroupedByStatus(@Param("from") Instant from);
}