package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

    // ── Database Variables ───────────────────────────────────────────────────
    private static final String URL      = "jdbc:postgresql://localhost:5432/staff_db";
    private static final String USER     = "postgres";
    private static final String PASSWORD = "12345";
    // ─────────────────────────────────────────────────────────────────────────

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public static void initializeTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS staff_members (" +
                        "    id          INTEGER PRIMARY KEY,"        +
                        "    type        VARCHAR(30)  NOT NULL,"      +
                        "    name        VARCHAR(100) NOT NULL,"      +
                        "    address     VARCHAR(200) NOT NULL,"      +
                        "    salary      DOUBLE PRECISION,"           +
                        "    bonus       DOUBLE PRECISION,"           +
                        "    hour_worked INTEGER,"                    +
                        "    rate        DOUBLE PRECISION"            +
                        ")";

        try (Connection conn = getConnection();
             Statement  stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("[DB] Connected to PostgreSQL – table ready.");
        } catch (SQLException e) {
            System.err.println("[DB] Initialization failed: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}