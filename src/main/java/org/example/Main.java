package org.example;

import org.apache.log4j.Logger;
import org.example.bd.OsbbCrud;

import java.sql.SQLException;

final class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);

    private Main() {
    }

    public static void main(final String[] args) throws RuntimeException {
        LOGGER.info("Started program");

        try (OsbbCrud osbbCrud = new OsbbCrud()) {
            osbbCrud.printAllOwnersWithNotEnteTheTerritoryToConsole();
            osbbCrud.printAllOwnersWithNotEnterTheTerritoryToFile("OwnersWithNotEnterTheTerritory.txt");
        } catch (SQLException e) {
            LOGGER.fatal(e);
        }
    }
}
