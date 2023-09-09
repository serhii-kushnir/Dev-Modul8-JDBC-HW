package org.example;

import org.apache.log4j.Logger;
import org.example.bd.OsbbCrud;
import org.example.bd.ui.Owners;

import java.sql.SQLException;

final class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    private Main() {
    }
    public static void main(final String[] args) {
        LOGGER.info("Started prgram");

       try (OsbbCrud osbbCrud = new OsbbCrud()) {
           for (Owners owners : osbbCrud.getOwnersWithAutoNotAllowed()) {
               final StringBuilder resoult = new StringBuilder();
               resoult.append(owners.getSurname())
                       .append(" ")
                       .append(owners.getName())
                       .append(" ")
                       .append(owners.getPatronymic())
                       .append(" ")
                       .append(owners.getPhoneNumber())
                       .append(" ")
                       .append(owners.getEmail())
                       .append(" ")
                       .append(owners.getHouseAddress())
                       .append(" ")
                       .append(owners.getHouseNumber())
                       .append(" ")
                       .append(owners.getApartmentNumber())
                       .append(" ")
                       .append(owners.getApartmentSqare());

               System.out.println(resoult);
           }
       } catch (SQLException e) {
           LOGGER.fatal(e);
       }
    }
}
