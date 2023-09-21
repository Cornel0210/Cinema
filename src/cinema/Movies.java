package cinema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.IllegalFormatException;
import java.util.LinkedList;
import java.util.List;

public class Movies {
    private void addMovie(DataBase db, String tableName, Movie movie) {

        String command = "INSERT INTO " + tableName + "(movie, price, isAvailable) VALUES(?, ?, ?)";
        PreparedStatement prep = null;
        try {
            db.openConnection();
            Connection conn = db.getConn();
            prep = conn.prepareStatement(command);
            prep.setString(1, movie.getName());
            prep.setDouble(2, movie.getPrice());
            prep.setInt(3, movie.isAvailable() ? 1 : 0);
            prep.execute();
        } catch (SQLException e){
            System.out.println("addMovie: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (prep != null){
                try {
                    prep.close();
                } catch (SQLException e2){
                    System.out.println(e2.getMessage());
                }
            }
            db.closeConnection();
        }
    }
    public void loadMovies(DataBase db){
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("movies.txt");
            br = new BufferedReader(fr);

            createMovieTable(db, this.getClass().getSimpleName());
            String data;
            while ((data = br.readLine())!=null){
                Movie movie = parseToMovie(data);
                if (!hasMovie(db, movie.getName())){
                    addMovie(db, this.getClass().getSimpleName(), movie);
                }
            }

        } catch (IOException e){
            System.out.println(e.getClass());
            e.printStackTrace();
        } finally {
            if (fr != null){
                try {
                    fr.close();
                } catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }
            if (br != null){
                try {
                    br.close();
                } catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public List<Movie> getMovies(DataBase db){
        String command = "SELECT * FROM " + this.getClass().getSimpleName();
        Statement statement = null;
        try {
            db.openConnection();
            Connection conn = db.getConn();
            statement = conn.createStatement();
            statement.execute(command);
            ResultSet results = statement.getResultSet();
            List<Movie> movies = new LinkedList<>();
            while (results.next()){
                int id = results.getInt(1);
                String name = results.getString(2);
                double price = results.getDouble(3);
                boolean isAvailable = (results.getInt(4) == 1);
                if (isAvailable){
                    movies.add(new Movie(id, name, price, true));
                }
            }
            return movies;

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e2){
                    System.out.println(e2.getMessage());
                }
            }
            db.closeConnection();
        }
        return null;
    }

    private Movie parseToMovie(String data){
        String[] input = data.trim().split(",");
        String name = input[0].trim();
        double price;
        try {
            price = Double.parseDouble(input[1].trim());
        } catch (IllegalFormatException e){
            price =-1d;
        }
        boolean isAvailable;
        try {
            isAvailable = Boolean.parseBoolean(input[2].trim());
        } catch (IllegalFormatException e){
            isAvailable =false;
        }
        return new Movie(name, price, isAvailable);
    }


    private boolean hasMovie(DataBase db, String movie){
        String command = "SELECT COUNT(*) FROM " + this.getClass().getSimpleName() + " WHERE movie = ?";
        PreparedStatement prep = null;
        try {
            db.openConnection();
            Connection conn = db.getConn();
            prep = conn.prepareStatement(command);
            prep.setString(1, movie);
            prep.execute();
            ResultSet results = prep.getResultSet();
            if (results.next()){
                if (results.getInt(1) > 0){
                    return true;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (prep != null) {
                try {
                    prep.close();
                } catch (SQLException e2){
                    System.out.println(e2.getMessage());
                }

            }
            db.closeConnection();
        }
        return false;
    }
    private void createMovieTable(DataBase db, String tableName){

        String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (id INTEGER PRIMARY KEY, movie TEXT NOT NULL, price REAL NOT NULL, " +
                "isAvailable INTEGER NOT NULL)";

        Statement statement = null;
        try {
            db.openConnection();
            Connection conn = db.getConn();
            statement = conn.createStatement();
            statement.execute(command);
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e2){
                    System.out.println(e2.getMessage());
                }
            }
            db.closeConnection();
        }
    }
}
