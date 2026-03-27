package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

    private static final String URL      = "jdbc:postgresql://localhost:5432/staff_db";
    private static final String USER     = "postgres";
    private static final String PASSWORD = "12345";

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
                        "    id          SERIAL PRIMARY KEY,"         +
                        "    type        VARCHAR(30)  NOT NULL,"      +
                        "    name        VARCHAR(100) NOT NULL,"      +
                        "    address     VARCHAR(200) NOT NULL,"      +
                        "    salary      DOUBLE PRECISION,"           +
                        "    bonus       DOUBLE PRECISION,"           +
                        "    hour_worked INTEGER,"                    +
                        "    rate        DOUBLE PRECISION"            +
                        ")";

        try {
            Connection conn = getConnection();
            Statement  stmt = conn.createStatement();
            stmt.execute(sql);
            // Ensure the id column has a proper auto-increment sequence
            stmt.execute("CREATE SEQUENCE IF NOT EXISTS staff_members_id_seq OWNED BY staff_members.id");
            stmt.execute("ALTER TABLE staff_members ALTER COLUMN id SET DEFAULT nextval('staff_members_id_seq')");
            stmt.execute("SELECT setval('staff_members_id_seq', COALESCE((SELECT MAX(id) FROM staff_members), 0), true)");
            stmt.close();
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