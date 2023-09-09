package org.example;

import org.apache.log4j.Logger;

final class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    private static final Config CONFIG = new Config();
    private Main() {
    }
    public static void main(final String[] args) {
        LOGGER.info("\nStarted...");

        CONFIG.flyway();
    }
}
