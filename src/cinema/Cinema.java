package cinema;

import java.sql.SQLException;
import java.util.*;

public class Cinema {

    Movies movies = new Movies();
    private final Map<Character, List<Seat>> seats;
    private DataBase db = new DataBase();

    public Cinema() {
        this.seats = new HashMap<>();
    }

    public Cinema(int rows, int columns, double pricePerSeat){
        this();
        for (int i = 0; i < rows; i++) {
            for (int j = 1; j <= columns; j++) {
                char key = (char)('A' + i);
                if (seats.putIfAbsent(key, new LinkedList<>()) != null){
                    seats.get(key).add(new Seat(j, pricePerSeat));
                } else {
                    seats.get(key).add(new Seat(j, pricePerSeat));
                }

            }
        }
        movies.loadMovies(db);

    }

    public double reserve(String movieName, char row, int number, double amount, String name, String phoneNumber, String email){
        double restOfTheAmount = amount;
        if ((row+"").toLowerCase().matches("[a-z]")){
            row = (row+"").toUpperCase().charAt(0);
        }
        Seat seat = findSeat(row, number);
        if (seat != null && seat.isFree()){
            restOfTheAmount = pay(seat, amount);
            System.out.println(seat + " has been reserved successfully!");
            updateDB(movieName, row, number, name, phoneNumber, email);
        }
        return restOfTheAmount;
    }

    private void reserve(Seat seat){
        seat.setOccupied();
    }
    private double pay(Seat seat, double amount){
        if (seat.getPrice() <= amount){
            reserve(seat);
            return amount-seat.getPrice();
        } else {
            System.out.println("Seat's price is $" + seat.getPrice() + " \n" +
                    "You only have $" + amount);
        }
        return amount;
    }


    private Seat findSeat(char row, int number){
        Set<Character> rows = seats.keySet();
        if (!rows.contains(row)){
            return null;
        }
        for (Seat seat : seats.get(row)){
            if (seat.getNumber() == number){
                return seat;
            }
        }
        System.out.println("There is no such seat.");
        return null;
    }

    public void updateRowAndColumn(String table, char row, int number, String name, String phone, String email){
        try {
            db.openConnection();
            db.updateEntry(table, row, number, name, phone, email);
        } catch (SQLException e){
            System.out.println("Can't query the dataBase " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
    }

    public void printReservations(String movie){
        try {
            db.openConnection();
            List<Reservation> reservations = db.queryReservationsForMovie(movie);
            for (Reservation reservation : reservations){
                System.out.printf("%s (%s, %s) -> %S%d\n", reservation.getName(), reservation.getPhoneNumber(),
                        reservation.getEmail(), reservation.getRow(), reservation.getNumber());
            }
        } catch (SQLException e){
            System.out.println("Can't query the dataBase " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
    }
    public double incomePerMovie(String movie){
        int income = -1;
        try {
            db.openConnection();
            income = db.countReservationsPerMovie(movie);
        } catch (SQLException e){
            System.out.println("incomePerMovie: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        return income*5.11d;
    }


    private void updateDB(String movieName, char row, int number, String name, String phoneNumber, String email){
        try {
            db.openConnection();
            db.addField(movieName, row, number, name, phoneNumber, email);
        } catch (SQLException e){
            System.out.println("Can't update the dataBase " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
    }

    public void printMovies(){
        getAvailableMovies().forEach(System.out::println);
    }
    private List<Movie> getAvailableMovies(){
        return movies.getMovies(db);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Character c : seats.keySet()){
            seats.get(c).forEach(seat -> sb.append(c).append(seat).append(" "));
            sb.append("\n");
        }
        return sb.toString();
    }
}
