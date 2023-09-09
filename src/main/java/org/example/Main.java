package org.example;

import org.apache.log4j.Logger;
import org.example.bd.OsbbCrud;

import java.io.IOException;
import java.sql.SQLException;

final class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    private Main() {
    }
    public static void main(final String[] args) {
        LOGGER.info("Started prgram");

       try (OsbbCrud osbbCrud = new OsbbCrud()) {

       }
    }
}
