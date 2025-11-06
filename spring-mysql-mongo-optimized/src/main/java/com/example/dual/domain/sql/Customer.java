package com.example.dual.domain.sql;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "customers", indexes = {
    @Index(name="idx_customers_email", columnList = "email", unique = true),
    @Index(name="idx_customers_created_at", columnList = "createdAt")
})
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false) private String name;
    @Column(nullable=false, unique=true, length=180) private String email;
    @Column(nullable=false) private Instant createdAt = Instant.now();
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getEmail(){return email;} public void setEmail(String email){this.email=email;}
    public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant t){this.createdAt=t;}
}
