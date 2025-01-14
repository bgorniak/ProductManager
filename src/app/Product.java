package app;

/**
 * Klasa reprezentująca produkt z nazwą i ceną.
 */
public class Product {
    private String name;
    private double price;

    /**
     * Konstruktor inicjalizujący produkt z podaną nazwą i ceną.
     */
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Pobiera nazwę produktu.
     */
    public String getName() {
        return name;
    }

    /**
     * Ustawia nazwę produktu.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Pobiera cenę produktu.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Ustawia cenę produktu.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Zwraca reprezentację produktu w formie łańcucha znaków.
     */
    @Override
    public String toString() {
        return "Product{name='" + name + "', price=" + price + '}';
    }
}