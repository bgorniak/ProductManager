package app;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa DataSubject implementuje wzorzec Obserwator, pozwalając na powiadamianie obserwatorów o zmianach w danych.
 */
public class DataSubject {
    private List<ProductObserver> observers = new ArrayList<>();
    private List<Product> masterProducts = new ArrayList<>();

    /**
     * Dodaje obserwatora do listy obserwatorów.
     */
    public void addObserver(ProductObserver observer) {
        observers.add(observer);
    }

    /**
     * Usuwa obserwatora z listy obserwatorów.
     */
    public void removeObserver(ProductObserver observer) {
        observers.remove(observer);
    }

    /**
     * Powiadamia wszystkich obserwatorów o zmianach, przekazując przetworzoną listę produktów.
     */
    public void notifyObserversWith(List<Product> processedProducts) {
        for (ProductObserver observer : observers) {
            observer.update(processedProducts);
        }
    }

    /**
     * Dodaje produkt do głównej listy produktów i powiadamia obserwatorów.
     */
    public void addProduct(Product product) {
        masterProducts.add(product);
        notifyObserversWith(masterProducts);
    }

    /**
     * Usuwa produkt z głównej listy produktów i powiadamia obserwatorów.
     */
    public void removeProduct(Product product) {
        masterProducts.remove(product);
        notifyObserversWith(masterProducts);
    }

    /**
     * Ustawia nową główną listę produktów i powiadamia obserwatorów.
     */
    public void setMasterProducts(List<Product> products) {
        this.masterProducts = products;
        notifyObserversWith(masterProducts);
    }

    /**
     * Pobiera główną listę produktów.
     */
    public List<Product> getMasterProducts() {
        return masterProducts;
    }
}