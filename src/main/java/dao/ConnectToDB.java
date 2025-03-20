package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDB {

    private static final String dbURI = "jdbc:mysql://localhost:3306/construction";
    private static final String dbUsername = "root";
    private static final String dbPassword = "admin";
    //private final String dbPassword = "0000";



    public static Connection getConnection () {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dbURI, dbUsername, dbPassword);
            System.out.println("database connected");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return con;
    }

}

