package app;

import java.util.ArrayList;
import java.util.List;

public class DataSubject {
    private List<ProductObserver> observers = new ArrayList<>();
    private List<Product> masterProducts = new ArrayList<>();

    public void addObserver(ProductObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ProductObserver observer) {
        observers.remove(observer);
    }

    public void notifyObserversWith(List<Product> processedProducts) {
        for (ProductObserver observer : observers) {
            observer.update(processedProducts);
        }
    }

    public void addProduct(Product product) {
        masterProducts.add(product);
        notifyObserversWith(masterProducts);
    }

    public void removeProduct(Product product) {
        masterProducts.remove(product);
        notifyObserversWith(masterProducts);
    }

    public void setMasterProducts(List<Product> products) {
        this.masterProducts = products;
        notifyObserversWith(masterProducts);
    }

    public List<Product> getMasterProducts() {
        return masterProducts;
    }
}
