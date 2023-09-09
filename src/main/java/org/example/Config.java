package org.example;

import org.apache.log4j.Logger;
import org.flywaydb.core.Flyway;

final class Config {
    private static final Logger LOGGER = Logger.getLogger(Config.class);

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/osbb";
    private static final String BD_USERNAME = "root";
    private static final String BD_PASSWORD = "root";

    public void flyway() {
        LOGGER.info("\nStarted Flyway");
        Flyway.configure()
                .dataSource(JDBC_URL, BD_USERNAME, BD_PASSWORD)
                .locations("classpath:flyway")
                .baselineOnMigrate(true)
                .load()
                .migrate();

        LOGGER.info("\nFlyway: Ok");
    }
}
