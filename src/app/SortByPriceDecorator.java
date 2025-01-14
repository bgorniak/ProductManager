package app;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Dekorator do sortowania produktów według ceny.
 */
public class SortByPriceDecorator extends DataProcessorDecorator {

    /**
     * Konstruktor przyjmujący obiekt DataProcessor do dekoracji.
     */
    public SortByPriceDecorator(DataProcessor wrappee) {
        super(wrappee);
    }

    /**
     * Sortuje listę produktów według ceny, a następnie przekazuje do dalszego przetwarzania.
     */
    @Override
    public List<Product> process(List<Product> products) {
        List<Product> sorted = products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());
        return wrappee.process(sorted);
    }
}