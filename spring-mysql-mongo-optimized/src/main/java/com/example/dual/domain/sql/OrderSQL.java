package com.example.dual.domain.sql;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "orders", indexes = {
    @Index(name="idx_orders_customer", columnList = "customer_id"),
    @Index(name="idx_orders_created_at", columnList = "createdAt")
})
public class OrderSQL {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional=false, fetch=FetchType.LAZY) @JoinColumn(name="customer_id")
    private Customer customer;
    @Column(nullable=false, scale=2, precision=12)
    private BigDecimal amount;
    @Column(nullable=false, length=24) private String status;
    @Column(nullable=false) private Instant createdAt = Instant.now();
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public Customer getCustomer(){return customer;} public void setCustomer(Customer c){this.customer=c;}
    public BigDecimal getAmount(){return amount;} public void setAmount(BigDecimal a){this.amount=a;}
    public String getStatus(){return status;} public void setStatus(String s){this.status=s;}
    public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant t){this.createdAt=t;}
}
