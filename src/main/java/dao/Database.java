package dao;

import java.sql.*;

public class Database {

    private static final String CONNECTION_URL = "jdbc:h2:./homework5";
    private static final Database INSTANCE = new Database();
    private Connection connection;

    private Database() {
        try {
            connection = DriverManager.getConnection(CONNECTION_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        try {
            if (!connection.isClosed()) {
                return connection;
            } else {
                connection = DriverManager.getConnection(CONNECTION_URL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public ResultSet executeQuery(String sql) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
