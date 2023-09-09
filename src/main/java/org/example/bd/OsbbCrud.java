package org.example.bd;

import org.apache.log4j.Logger;
import org.example.bd.ui.Owners;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public final class OsbbCrud implements Closeable {
    private static final Logger LOGGER = Logger.getLogger(OsbbCrud.class);
    private static final String NAMES_COLUMS = "| Прізвище | Ім'я | По батькові | Моб.Телефон  | Електрона пошта  | Вулиця  | Будинок | Квартира | Площадь |"
            + "\n" + "|----------|------|-------------|--------------|------------------|---------|---------|----------|---------|" + "\n";
    private Connection connection;
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
    public void printOwnersWithnotEnteTheTerritoryToConsole() {
        try (this) {
            System.out.print(NAMES_COLUMS);
            for (Owners owners : getOwnersWithnotEnteTheTerritory()) {
                String result = "|   "
                        + owners.getSurname()
                        + "   | "
                        + owners.getName()
                        + " |     "
                        + owners.getPatronymic()
                        + "    |  "
                        + owners.getPhoneNumber()
                        + "  |  "
                        + owners.getEmail()
                        + "  | "
                        + owners.getHouseAddress()
                        + " |    "
                        + owners.getHouseNumber()
                        + "    |   "
                        + owners.getApartmentNumber()
                        + "    |   "
                        + owners.getApartmentSquare()
                        + "  |   ";

                System.out.println(result);
            }
        } catch (SQLException e) {
            LOGGER.fatal(e);
        }
    }

    public void printOwnersWithNotEnterTheTerritoryToFile(String filePath) throws SQLException {
        try {
            connectionDatabases();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(NAMES_COLUMS);

                for (Owners owners : getOwnersWithnotEnteTheTerritory()) {
                    String result = "|   "
                            + owners.getSurname()
                            + "   | "
                            + owners.getName()
                            + " |     "
                            + owners.getPatronymic()
                            + "    |  "
                            + owners.getPhoneNumber()
                            + "  |  "
                            + owners.getEmail()
                            + "  | "
                            + owners.getHouseAddress()
                            + " |    "
                            + owners.getHouseNumber()
                            + "    |   "
                            + owners.getApartmentNumber()
                            + "    |   "
                            + owners.getApartmentSquare()
                            + "  |   ";

                    writer.write(result);
                    writer.newLine();
                }
            } catch (IOException e) {
                LOGGER.fatal("Failed to write to file: " + e.getMessage());
            }
        } finally {
            close();
        }
    }

    private List<Owners> getOwnersWithnotEnteTheTerritory() throws SQLException {
        String sqlOwnersWithAutoNotAllowedQuery = """
                SELECT
                    MO.`surname` AS `Прізвище`,
                    MO.`name` AS `Ім'я`,
                    MO.`patronymic` AS `По батькові`,
                    MO.`phone_number` AS `Телефон`,
                    MO.`email` AS `Електрона пошта`,
                   \s
                    B.`address` AS `Вулиця`,
                    B.`number` AS `Будинок`,
                   \s
                    A.`number` AS `Квартира`,
                    A.`square` AS `Площадь`
                FROM residents AS `R`
                JOIN members_osbb `MO` ON R.`members_osbb_id` = MO.`id`
                JOIN apartments `A` ON R.`apartments_id` = A.`id`
                JOIN houses `B` ON A.`houses_id` = B.`id`
                WHERE(
                \tSELECT  COUNT(*)\s
                \tFROM residents AS `R`\s
                \tWHERE R.`members_osbb_id` = MO.`id`
                ) < '2'\s
                AND NOT R.`entry_rights_territory`
                ORDER BY MO.`id`""";

        final List<Owners> result = new LinkedList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlOwnersWithAutoNotAllowedQuery)) {
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(new Owners()
                        .setPhoneNumber(resultSet.getInt("Телефон"))
                        .setHouseNumber(resultSet.getInt("Будинок"))
                        .setSurname(resultSet.getString("Прізвище"))
                        .setApartmentNumber(resultSet.getInt("Квартира"))
                        .setName(resultSet.getString("Ім'я"))
                        .setPatronymic(resultSet.getString("По батькові"))
                        .setEmail(resultSet.getString("Електрона пошта"))
                        .setHouseAddress(resultSet.getString("Вулиця"))
                        .setApartmentSquare(resultSet.getFloat("Площадь"))
                );

            }
        }
        return result;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.fatal(e);
        }
    }
}