package com.gmail.pashasimonpure.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.gmail.pashasimonpure.repository.ConnectionRepository;
import com.gmail.pashasimonpure.service.util.ConfigUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionRepositoryImpl implements ConnectionRepository {

    private static final Logger logger = LogManager.getLogger(ConnectionRepositoryImpl.class);
    private static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final String DATABASE_URL = "db.url";
    private static final String DATABASE_USERNAME = "db.username";
    private static final String DATABASE_PASSWORD = "db.password";

    private String configPath;
    private ConfigUtil config;

    public ConnectionRepositoryImpl(String configPath) {
        this.configPath = configPath;
        this.config = new ConfigUtil(this.configPath);
    }

    @Override
    public Connection getConnection() {
        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            return DriverManager.getConnection(
                    config.getProperty(DATABASE_URL),
                    config.getProperty(DATABASE_USERNAME),
                    config.getProperty(DATABASE_PASSWORD)
            );
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("App cannot get connection to database");
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("App cannot find MySQL driver at classpath");
        }
    }

}