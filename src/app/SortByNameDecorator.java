package app;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Dekorator do sortowania produktów według nazwy.
 */
public class SortByNameDecorator extends DataProcessorDecorator {

    /**
     * Konstruktor przyjmujący obiekt DataProcessor do dekoracji.
     */
    public SortByNameDecorator(DataProcessor wrappee) {
        super(wrappee);
    }

    /**
     * Sortuje listę produktów według nazwy, a następnie przekazuje do dalszego przetwarzania.
     */
    @Override
    public List<Product> process(List<Product> products) {
        List<Product> sorted = products.stream()
                .sorted(Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        return wrappee.process(sorted);
    }
}
