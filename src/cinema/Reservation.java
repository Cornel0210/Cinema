package cinema;

public class Reservation {
    private final char row;
    private final int number;
    private final String phoneNumber;
    private final String name;
    private final String email;

    public Reservation(char row, int number, String name, String phoneNumber, String email) {
        this.row = row;
        this.number = number;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.email = email;
    }

    public char getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
