package org.beauty.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        createTables(connection);
    }

    private static void createTables(Connection connection) {
        String createOrderTableSQL = """
                CREATE TABLE IF NOT EXISTS orders (
                order_id INT AUTO_INCREMENT PRIMARY KEY,
                current_status INT NOT NULL,
                client_id INT NOT NULL,
                est_time_to_deliver TIMESTAMP NOT NULL,
                item_id INT NOT NULL,
                price INT,
                created_at TIMESTAMP,
                delete_reason VARCHAR(255) DEFAULT NULL
                
                );
                """;
        String createOrderEventTableSQL = """
                CREATE TABLE IF NOT EXISTS order_events (
                event_id INT AUTO_INCREMENT PRIMARY KEY,
                order_id INT NOT NULL,
                employee_id INT NOT NULL,
                event_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                event_type VARCHAR(255) NOT NULL,
                FOREIGN KEY (order_id) REFERENCES orders(order_id)
                );
                """;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createOrderTableSQL);
            statement.executeUpdate(createOrderEventTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
