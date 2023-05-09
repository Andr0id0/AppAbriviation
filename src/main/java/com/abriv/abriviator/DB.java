package com.abriv.abriviator;


import java.sql.*;


public class DB {

    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "dbSP";
    private final String LOGIN = "root";
    private final String PASS = "";

    private Connection dbConn = null;

    public Connection getDBConnect() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?characterEncoding=UTF-8&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    public void isConnected() throws SQLException, ClassNotFoundException {
        dbConn = getDBConnect();
        System.out.println(dbConn.isValid(1000));

    }


    public void addLink(String Abriviation, String link) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO abriviation (abriviation, link) VALUES (?, ?)";

        PreparedStatement preparedStatement = getDBConnect().prepareStatement(sql);
        preparedStatement.setString(1, Abriviation);
        preparedStatement.setString(2, link);
        preparedStatement.executeUpdate();
    }

    public ResultSet getLinks() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM abriviation";
        Statement statement = getDBConnect().createStatement();
        return statement.executeQuery(sql);
    }

}
