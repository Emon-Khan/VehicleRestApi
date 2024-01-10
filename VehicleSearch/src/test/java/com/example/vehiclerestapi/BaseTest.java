package com.example.vehiclerestapi;

import org.testcontainers.containers.MySQLContainer;

public class BaseTest {
    static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:latest")
            .withDatabaseName("test-db")
            .withUsername("testuser")
            .withPassword("pass");

    static {
        mySQLContainer.start();
    }
}
