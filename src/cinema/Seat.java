package cinema;

public class Seat {
    private final int number;
    private boolean isFree;
    private double price;

    public Seat(int number, double price) {
        this.number = number;
        this.isFree = true;
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public double getPrice() {
        return price;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setOccupied() {
        isFree = false;
    }

    @Override
    public String toString() {
        return number + "(" + (isFree ? " " : "\u2022") + ")";
    }
}
