package com.example.react_gametime.infrastructure.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DBInfoLogger {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void logDatabaseInfo() {
        try (Connection conn = dataSource.getConnection()) {
            var meta = conn.getMetaData();
            System.out.println("===== Actual Database Info =====");
            System.out.println("JDBC URL: " + meta.getURL());
            System.out.println("Driver: " + meta.getDriverName() + " " + meta.getDriverVersion());
            System.out.println("Database: " + meta.getDatabaseProductName() + " " + meta.getDatabaseProductVersion());
            System.out.println("Autocommit: " + conn.getAutoCommit());
            System.out.println("Isolation: " + conn.getTransactionIsolation());
            System.out.println("===============================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
