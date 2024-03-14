package org.beauty.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        try {
            File file = new File("database.db");
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Connection connection = DatabaseConnection.getInstance().getConnection();
        createTables(connection);
    }

    private static void createTables(Connection connection) {
        String createOrderEventTableSQL = """
                CREATE TABLE IF NOT EXISTS order_events  (
                    event_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    event_type VARCHAR(255) NOT NULL,
                    event_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    order_id BIGINT,
                    client_id BIGINT,
                    employee_id BIGINT,
                    est_delivery_time TIMESTAMP,
                    item_id BIGINT,
                    price DECIMAL,
                    cancellation_reason VARCHAR(255)
                );
                """;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createOrderEventTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
