package app;

import java.util.List;

/**
 * Interfejs DataProcessor definiuje metodę do przetwarzania listy obiektów Product.
 */
public interface DataProcessor {
    List<Product> process(List<Product> products);
}
