package sample;

import java.sql.*;

public class Connection {
    private String url = "jdbc:mysql://localhost:3306/FinalProjectTA?serverTimezone=GMT";
    private String username = "root";
    private String password = "davin123";
    //private String password = "";


    public java.sql.Connection connection;


    // Class constructor of Connection class
    public Connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Function that return prepStat
    public PreparedStatement getPrepStat(String query) {
        try {
            return connection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
