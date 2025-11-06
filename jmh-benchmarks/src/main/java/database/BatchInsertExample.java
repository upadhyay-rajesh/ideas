package database;

//Best for bulk insert/update/delete
//Use PreparedStatement + addBatch() + executeBatch()
//Always use setAutoCommit(false) for performance

import java.sql.*;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class BatchInsertExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "root";
        String pass = "password";

        try (Connection con = DriverManager.getConnection(url, user, pass)) {

            con.setAutoCommit(false);  // IMPORTANT

            String sql = "INSERT INTO students(id, name) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            for (int i = 1; i <= 1000; i++) {
                ps.setInt(1, i);
                ps.setString(2, "Student-" + i);
                ps.addBatch();

                if (i % 100 == 0) {       // execute 100 at a time
                    ps.executeBatch();
                    ps.clearBatch();
                }
            }

            ps.executeBatch();  // leftover
            con.commit();

            System.out.println("Batch Insert Completed!");
            
         // Batch Update Example
            String sql3 = "UPDATE students SET marks = ? WHERE id = ?";

            PreparedStatement ps3 = con.prepareStatement(sql);

            for (int i = 1; i <= 500; i++) {
                ps3.setInt(1, 80);
                ps3.setInt(2, i);
                ps3.addBatch();
            }

            ps3.executeBatch();
            con.commit();

            // Batch Delete Example
            String sql2 = "DELETE FROM students WHERE id = ?";

            PreparedStatement ps2 = con.prepareStatement(sql);

            for (int i = 1; i <= 200; i++) {
                ps2.setInt(1, i);
                ps2.addBatch();
            }

            ps2.executeBatch();
            con.commit();

            // Batch with HikariCP (Best Production Setup)
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://localhost:3306/testdb");
            config.setUsername("root");
            config.setPassword("password");
            config.setMaximumPoolSize(10);

            HikariDataSource ds = new HikariDataSource(config);

            try (Connection con1 = ds.getConnection()) {
                con.setAutoCommit(false);
                PreparedStatement ps1 = con1.prepareStatement(
                        "INSERT INTO students(id, name) VALUES(?, ?)");

                for (int i = 1; i <= 1000; i++) {
                    ps1.setInt(1, i);
                    ps1.setString(2, "Student-" + i);
                    ps1.addBatch();
                }

                ps1.executeBatch();
                con.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



/*
 * WRITE PERFORMANCE TUNING (Very Important) Write performance matters for:
 * Order insertion Logging Events Large imports IoT data Payment systems
 * 
 * 4.1 Use Batch Inserts (JDBC)
 * 
 * Bad (slow): for(...) { stmt.executeUpdate(); } Good:
 * 
 * stmt.addBatch(); stmt.executeBatch();
 * 
 * 
 * 20x faster Less network round trips
 * 
 * Disable Auto-Commit conn.setAutoCommit(false); ... conn.commit();
 * 
 * 
 * 5–10x faster writes Fewer fsync operations
 * 
 * Use Bulk Operations
 * 
 * SQL bulk insert:
 * 
 * INSERT INTO orders (id, amount) VALUES (1, 100), (2, 200), (3, 300);
 * 
 * 
 * Single transaction Best for large imports
 * 
 * 4.4 Avoid Large Transactions
 * 
 * Bad: Huge transaction of 10M rows
 * 
 * Good: Break into chunks of 5k / 10k
 * 
 * Avoids locks Avoids memory explosion
 * 
 * Use Proper Indexing for Writes Indexes make reads faster but writes slower.
 * 
 * During heavy writes: Drop unnecessary indexes
 * 
 * 50–70% faster inserts
 * 
 * Use Sharding / Partitioning Partition example: orders_2025 orders_2026
 * orders_2027
 * 
 * 
 * Use Asynchronous Writes
 * 
 * Patterns:
 * 
 * Event queue (Kafka, RabbitMQ)
 * 
 * Write to Redis first → sync to DB later
 * 
 * Log tables
 * 
 * Zero-latency writes DB load reduced massively
 * 
 * Example:
 * 
 * Payment logs
 * 
 * Activity logs
 * 
 * Analytics
 * 
 * Use WAL (Write Ahead Logging)
 * 
 * PostgreSQL + MySQL both support WAL.
 * 
 * Sequential writes (fast) Crash recovery
 * 
 * Use Connection Pools for Write-Heavy Workload Optimize pool size:
 * 
 * Writes often need more DB connections
 * 
 * Use separate pools for:
 * 
 * READ
 * 
 * WRITE
 * 
 * 5. Real-World Architecture for High Write Performance Flow: Client → API →
 * Cache (Redis) → Queue (Kafka) → DB (Batced Inserts)
 * 
 * 6.3 Disable Expensive Constraints for Bulk Load
 * 
 * Disable: FKs Triggers Secondary indexes Unique constraints Bulk insert
 * Rebuild indexes Extremely faster writes (10–30x)
 * 
 * Write Performance Tuning Use batch inserts Disable auto-commit Reduce indexes
 * on write-heavy tables Use partitioning + sharding Use asynchronous writes
 * (queues) Keep transactions small Consider write-ahead logging
 */