package database;
/*Benefits
	Faster performance
	Lower memory + CPU load
	Avoids “too many connections” DB errors
	Thread-safe and scalable

	HikariCP → fastest, preferred,modern, widely used
	C3P0 → old but stable
	Apache DBCP2 → Apache supported, used in Tomcat
*/

//HikariCP Example
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HikariExample {
    public static void main(String[] args) throws Exception {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/testdb");
        config.setUsername("root");
        config.setPassword("password");
        config.setMaximumPoolSize(10);

        HikariDataSource ds = new HikariDataSource(config);

        try (Connection con = ds.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM students")) {

            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2));
            }
        }

        ds.close();
    }
}

// Example
/*
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.*;

public class C3P0Example {
    public static void main(String[] args) throws Exception {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setDriverClass("com.mysql.cj.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/testdb");
        ds.setUser("root");
        ds.setPassword("password");
        ds.setMaxPoolSize(10);

        try (Connection con = ds.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM students")) {
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }
        }

        ds.close();
    }
}
*/


//Example
/*
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;

public class DBCPExample {
    public static void main(String[] args) throws Exception {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/testdb");
        ds.setUsername("root");
        ds.setPassword("password");
        ds.setMaxTotal(10);

        try (Connection con = ds.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM students")) {

            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }
        }

        ds.close();
    }
}
*/