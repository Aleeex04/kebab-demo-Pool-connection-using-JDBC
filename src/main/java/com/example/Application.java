package com.example;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class Application {
    private static HikariDataSource dataSource;

    public static void main(String[] args) throws SQLException {
        try {
            initDatabaseConnectionPool();
            deleteData("%");
            readData();
            createData("Kebab mixto", "Delicioso kebab con pollo y ternera", 4.00);
            createData("Durum de pollo", "Pan de pita con pollo, verduras y salsa", 5.00);
            createData("Kebab vegetariano", "Kebab de verduras y falafel", 3.00);
            readData();
            updateData("Kebab mixto", 5.00);
            readData();
            deleteData("Kebab vegetariano");
            readData();

        } finally {
            closeDatabaseConnectionPool();
        }

    }

    private static void createData(String name, String description, double price) throws SQLException {
        System.out.println("Creating data...");
        int rowsInserted;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("""
                    insert into Kebabs(Itemname, Itemdescription, Itemprice)
                    values (?,?,?)
                    """)) {
                statement.setString(1, name);
                statement.setString(2, description);
                statement.setDouble(3, price);
                rowsInserted = statement.executeUpdate();
            }
        }
        System.out.println("Rows inserted: " + rowsInserted);
    }

    private static void readData() throws SQLException {
        System.out.println("Reading data... ");
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("""
                        select Itemname, Itemdescription, Itemprice
                        from Kebabs
                        order by Itemprice DESC 
                    """)) {
                ResultSet resultSet =statement.executeQuery();
                boolean empty = true;
                while (resultSet.next()) {
                    String name = resultSet.getString(1);
                    String description = resultSet.getString(2);
                    double price = resultSet.getDouble(3);
                    System.out.println("\t> " + name + ": " + description + " - " + price);
                    empty = false;
                }
                if (empty) {
                    System.out.printf("\t (no data)");
                }
            }
        }
    }
    private static void updateData(String name, double newPrice) throws SQLException {
        System.out.println("Updating data... ");
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("""
                    UPDATE Kebabs
                    SET Itemprice = ?
                    WHERE Itemname = ?
                    """)) {
                statement.setDouble(1, newPrice);
                statement.setString(2, name);
                int rowsUpdated = statement.executeUpdate();
                System.out.println("Rows updated: " + rowsUpdated);
            }
        }
    }

    private static void deleteData(String nameExpression) throws SQLException {
        System.out.println("Deleting data... ");
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("""
                        DELETE FROM Kebabs
                        WHERE  Itemname LIKE ?
                    """)) {
                statement.setString(1, nameExpression);
                int rowsDeleted= statement.executeUpdate();
                System.out.println("Rows deleted: " + rowsDeleted);
            }
        }

    }
    private static void initDatabaseConnectionPool() {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mariadb://localhost:3306/kebab");
        dataSource.setUsername("alexr");
        dataSource.setPassword("alex2004");

        // if (true) throw new RuntimeException("Simulated error! ");
    }

    private static void closeDatabaseConnectionPool() {
        dataSource.close();
    }
}
