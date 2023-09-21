package cinema;

public class Movie {
    private final int id;
    private final String name;
    private double price;
    private boolean isAvailable;
    private static int LAST_ID = 1;

    public Movie(String name, double price, boolean isAvailable) {
        this.id = LAST_ID++;
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
    }
    public Movie(int id, String name, double price, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public static int getLastId() {
        return LAST_ID;
    }

    @Override
    public String toString() {
        return String.format("id:%3d - name: %-30s, price $%.2f", id, name, price);
    }
}
