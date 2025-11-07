# Spring Boot — Actuator Metrics + Startup Optimizations

This project is **ready to run** and demonstrates:
- ✅ Spring Boot Actuator metrics
- ✅ Prometheus scrape endpoint (`/actuator/prometheus`)
- ✅ Custom Micrometer metrics (counter, timer, gauge)
- ✅ Startup time optimizations:
  - `spring-context-indexer` (compile-time component index)
  - Global **Lazy Initialization**

## Run

```bash
# Build
mvn -q -DskipTests package

# Run
java -jar target/perf-optimized-0.0.1-SNAPSHOT.jar
```

Now open:
- App endpoint: `http://localhost:8080/api/hello`
- Simulated work: `http://localhost:8080/api/work?millis=250`
- Actuator metrics index: `http://localhost:8080/actuator`
- Prometheus scrape: `http://localhost:8080/actuator/prometheus`

## Key Files

- `pom.xml` — Actuator, Prometheus, spring-context-indexer, lazy init enabled
- `application.properties` — exposes helpful endpoints + lazy init
- `DemoController` — sample endpoints + custom Micrometer metrics
- `MetricsConfig` — registers a custom Gauge
- `HeavyService` — annotated with `@Lazy` to demonstrate lazy construction

## Notes

- The **context indexer** generates `META-INF/spring.components` at build time to speed up startup by avoiding classpath scanning.
- **Lazy initialization** is enabled globally; Spring creates beans on first use. Disable if you want all beans ready at startup by removing `spring.main.lazy-initialization=true`.
- Add your own dependencies as needed. For DB metrics (HikariCP) include `spring-boot-starter-jdbc` and a datasource; Hikari metrics will appear automatically under `/actuator/metrics/hikaricp.*`.
