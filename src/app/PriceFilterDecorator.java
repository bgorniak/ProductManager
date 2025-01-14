package app;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dekorator filtrowania produktów według przedziału cenowego.
 */
public class PriceFilterDecorator extends DataProcessorDecorator {
    private double minPrice;
    private double maxPrice;

    /**
     * Konstruktor ustawia obiekt do dekoracji oraz zakres cenowy.
     */
    public PriceFilterDecorator(DataProcessor wrappee, double minPrice, double maxPrice) {
        super(wrappee);
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    /**
     * Przetwarza listę produktów, filtrując je według zakresu cenowego, a następnie przekazuje do dalszego przetwarzania.
     */
    @Override
    public List<Product> process(List<Product> products) {
        List<Product> filtered = products.stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
        return wrappee.process(filtered);
    }
}
