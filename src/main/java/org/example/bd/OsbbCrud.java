package org.example.bd;

import org.apache.log4j.Logger;
import org.example.bd.ui.Owners;

import java.io.Closeable;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public final class OsbbCrud implements Closeable {
    private static final Logger LOGGER = Logger.getLogger(OsbbCrud.class);

    private String sqlOWnersWithAutoNotAllowedQuery = "SELECT\n" +
            "    MO.`surname` AS `Прізвище`,\n" +
            "    MO.`name` AS `Ім'я`,\n" +
            "    MO.`patronymic` AS `По батькові`,\n" +
            "    MO.`phone_number` AS `Телефон`,\n" +
            "    MO.`email` AS `Електрона пошта`,\n" +
            "    \n" +
            "    B.`address` AS `Вулиця`,\n" +
            "    B.`number` AS `Будинок`,\n" +
            "    \n" +
            "    A.`number` AS `Квартира`,\n" +
            "    A.`sqare` AS `Площадь`\n" +
            "FROM residents AS `R`\n" +
            "JOIN members_osbb `MO` ON R.`members_osbb_id` = MO.`id`\n" +
            "JOIN apartments `A` ON R.`apartments_id` = A.`id`\n" +
            "JOIN houses `B` ON A.`houses_id` = B.`id`\n" +
            "WHERE(\n" +
            "\tSELECT  COUNT(*) \n" +
            "\tFROM residents AS `R` \n" +
            "\tWHERE R.`members_osbb_id` = MO.`id`\n" +
            ") < '2' \n" +
            "AND NOT R.`entry_rights_territory`\n" +
            "ORDER BY MO.`id`";
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

    public List<Owners> getOwnersWithAutoNotAllowed() throws SQLException {
        LOGGER.trace("Call getting Owners with auto not allowed method");

        final List<Owners> result = new LinkedList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlOWnersWithAutoNotAllowedQuery)) {
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
                                .setApartmentSqare(resultSet.getFloat("Площадь"))
                        );

            }
        }
        return result;
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
