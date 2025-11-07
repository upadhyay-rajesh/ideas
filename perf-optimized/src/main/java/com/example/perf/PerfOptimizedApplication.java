package com.example.perf;
/*
Why Does Spring Boot Start Slowly?
Spring Boot startup time increases due to:
⛔ Classpath scanning
⛔ Component scanning (thousands of classes)
⛔ Bean creation & wiring
⛔ Autoconfiguration
⛔ Database connection initialization
⛔ JPA entity scanning
⛔ Actuator + Metrics + Converters
For large apps, startup may take 6–30 seconds.
We fix this using:
spring-context-indexer
 Lazy Initialization
 Selective Auto-config
 AOT Processing (Spring Native)
 Excluding unused starters
 
 Problem:
Normally Spring scans the entire classpath for @Component, @Service, etc.
Solution:
spring-context-indexer creates a static index file during compile time.

Before indexer:
•	Spring scans all packages
•	Identifies beans at runtime
•	Slow for large apps
After indexer:
•	AnnotationProcessor creates index
•	Spring loads index instead of reflection scanning
•	Much faster

*/
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Disable Unused Auto-Configurations
 * @SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    SecurityAutoConfiguration.class
})

Limit Component Scan
@ComponentScan(basePackages = "com.myapp.core")
Avoids scanning third-party dependencies

Turn Off JPA Entity Scan (if not needed)
spring.jpa.open-in-view=false
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=false

Disable Devtools in Production
Devtools slows classpath scanning.

 */

@SpringBootApplication
public class PerfOptimizedApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PerfOptimizedApplication.class);
        // Programmatic way (properties already enabled): app.setLazyInitialization(true);
        app.run(args);
        
        /*
         * When Not to Use Lazy Initialization
❌ For background services
❌ For event listeners that must start at boot
❌ For security filters
❌ For DB connection pool init
❌ For apps with high request volume immediately at boot

         */
    }
}
/*
Spring Boot Actuator provides production-ready monitoring for:
•	Performance
•	Health
•	CPU & memory
•	Thread pool
•	GC
•	Database connections
•	Startup time
•	HTTP request timings
•	Custom business metrics
It exposes these metrics via:
HTTP Endpoints
 JMX
 Micrometer (Prometheus, Graphite, Datadog, New Relic, CloudWatch)
 
 
Add Actuator Dependency

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
________________________________________
Enable Performance Endpoints
Add in application.properties:
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always

________________________________________
Important Performance Metrics
Actuator exposes Micrometer metrics at:
GET /actuator/metrics
GET /actuator/prometheus (for Prometheus)


HTTP Request Performance
Endpoint:
/actuator/metrics/http.server.requests

JVM Memory Usage
/actuator/metrics/jvm.memory.used
/actuator/metrics/jvm.gc.pause
Detect memory leaks
Monitor GC pauses
Helps tune heap size

CPU Metrics
/actuator/metrics/system.cpu.usage
/actuator/metrics/process.cpu.usage
Detect overloaded node
Autoscaling decisions

Thread Pool Metrics
/actuator/metrics/jvm.threads.live
/actuator/metrics/jvm.threads.daemon
/actuator/metrics/jvm.threads.peak
Detect thread starvation
Monitor async executor pools
________________________________________
DataSource Metrics (HikariCP)
/actuator/metrics/hikaricp.connections
/actuator/metrics/hikaricp.connections.active
/actuator/metrics/hikaricp.connections.pending
Detect connection exhaustion
 Detect slow queries
Pool misconfiguration
________________________________________
Cache Metrics
For Redis / Caffeine / Ehcache:
/actuator/metrics/cache.gets
/actuator/metrics/cache.puts
/actuator/metrics/cache.evictions
Monitor hit/miss ratio
See if cache is helping
________________________________________
Redis, Kafka & RabbitMQ Metrics
Redis:
/actuator/metrics/redis.commands
Kafka:
/actuator/metrics/kafka.producer.record.send.total
/kafka.consumer.records.consumed.total
Critical for microservices performance
________________________________________
GC, Heap & Non-Heap Metrics
/actuator/metrics/jvm.gc.max.data.size
/actuator/metrics/jvm.gc.live.data.size
Detect memory pressure
Helps tune G1 / ZGC

 */
