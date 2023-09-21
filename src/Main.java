import cinema.Cinema;

public class Main {
    public static void main(String[] args) {
        Cinema cinema = new Cinema(10, 15, 5.11d);
        cinema.printMovies();
        /*System.out.println(cinema.reserve("Garfield",'a', 1, 10.01d, "John", "0033140563487", "john@mail.com"));
        System.out.println(cinema.reserve("Garfield",'C', 1, 100d, "Maya", "0033453621578", "maya@mail.com"));
        System.out.println(cinema.reserve("Garfield",'C', 2, 20d, "Jack", "0033123456789", "jack@mail.com"));
        System.out.println(cinema.reserve("Garfield",'C', 3, 20d, "Jane", "0033123456788", "jane@mail.com"));
        cinema.updateRowAndColumn("Garfield",'C', 3, "Jane", "0033123456788", "jane@mail.com");
        System.out.println(cinema);
        cinema.printReservations("Garfield");*/
       // System.out.println(cinema.incomePerMovie("Garfield"));
    }
}
