package org.example;

import org.apache.log4j.Logger;
import org.example.bd.OsbbCrud;

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
