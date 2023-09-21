package cinema;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DataBase {
    private String DB_NAME = "cinema.db";
    private String DB_PATH = "jdbc:sqlite:E:\\05. java\\05. repositories\\Cinema\\" + DB_NAME;
    private Connection conn;

    public boolean openConnection(){
        try {
            conn = DriverManager.getConnection(DB_PATH);
            return true;
        } catch (SQLException e){
            System.err.println("openConnection: " + e.getMessage());
            return false;
        }
    }

    public void closeConnection(){
        try {
            if (conn != null){
                conn.close();
            }
        } catch (SQLException e){
            System.err.println("closeConnection: " + e.getMessage());
        }
    }


    private void createTable(String name) throws SQLException{
        String command = "CREATE TABLE IF NOT EXISTS " +
                name + " (row TEXT NOT NULL, number INTEGER NOT NULL, name TEXT, phone TEXT, email TEXT)";
        Statement statement = conn.createStatement();
        statement.execute(command);
        statement.close();
    }
    public void addField(String table, char row, int number, String name, String phone, String email) throws SQLException{
        createTable(table);
        if (queryEntry(table, name) != null){
            return;
        }
        String command = "INSERT INTO " + table  + "(row, number, name, phone, email)" +
                " VALUES('" + (row+"") + "', " + number + ", '" + name + "', '" + phone + "', '" + email + "')";
        Statement statement = conn.createStatement();
        statement.execute(command);
        statement.close();
    }
    public void updateEntry(String table, char row, int number, String name, String phone, String email) throws SQLException{
        String command = "UPDATE " + table + " SET row = '" + row + "', number = " + number +
        ", phone ='" + phone + "', email = '" + email + "' WHERE name = '" + name + "'";
        System.out.println(command);
        Statement statement = conn.createStatement();
        statement.execute(command);
        statement.close();
    }

    private Reservation queryEntry(String table, String name) throws SQLException{
        String command = "SELECT * FROM " + table + " WHERE " +table + ".name = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(command);
        preparedStatement.setString(1, name);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        preparedStatement.close();
        if (resultSet.next()){
            return new Reservation(resultSet.getString(1).charAt(0),
                    resultSet.getInt(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5));
        }
        return null;
    }

    public List<Reservation> queryReservationsForMovie(String movie) throws SQLException{
        String command = "SELECT * FROM " + movie;
        Statement statement = conn.createStatement();
        statement.execute(command);
        ResultSet results = statement.getResultSet();
        statement.close();
        List<Reservation> reservations = new LinkedList<>();
        while (results.next()){
            char row = results.getString(1).charAt(0);
            int number = results.getInt(2);
            String name = results.getString(3);
            String phoneNumber = results.getString(4);
            String email = results.getString(5);
            reservations.add(new Reservation(row, number, name, phoneNumber, email));
        }
        return reservations;
    }

    public int countReservationsPerMovie(String movie) throws SQLException{
        String command = "SELECT COUNT (*) FROM " + movie;

        Statement statement = conn.createStatement();
        statement.execute(command);
        ResultSet resultSet = statement.getResultSet();
        statement.close();
        if (resultSet.next()){
            return resultSet.getInt(1);
        }
        return -1;
    }

    public List<String> getEntriesFromTable(String tableName) throws SQLException{
        String command = "SELECT * FROM " + tableName;
        Statement statement = conn.createStatement();
        statement.execute(command);
        ResultSet resultSet = statement.getResultSet();
        statement.close();
        List<String> list = new LinkedList<>();
        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public Connection getConn() {
        return conn;
    }
}
