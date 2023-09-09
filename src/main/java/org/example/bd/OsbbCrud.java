package org.example.bd;

import org.apache.log4j.Logger;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class OsbbCrud implements Closeable {
    private static final Logger LOGGER = Logger.getLogger(OsbbCrud.class);
    private Connection connection = null;
    private final Config config;
    public OsbbCrud() {
        config = new Config();
        connectionDatabases();
    }

    private void connectionDatabases() {
        try {
            LOGGER.info("Started Connection databases");

            connection = DriverManager.getConnection(
                    config.getJdbcUrl(),
                    config.getBdUsername(),
                    config.getBdPassword());

            LOGGER.info("Connection databases: Ok");
        } catch (SQLException e) {
            LOGGER.fatal(e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            LOGGER.fatal(e);
        }
    }
}
