package app;

import java.util.List;

/**
 * Abstrakcyjna klasa dekoratora dla DataProcessor, umożliwiająca rozszerzanie jego funkcjonalności.
 */
public abstract class DataProcessorDecorator implements DataProcessor {
    protected DataProcessor wrappee;

    /**
     * Konstruktor ustawiający dekorowany obiekt.
     */
    public DataProcessorDecorator(DataProcessor wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public List<Product> process(List<Product> products) {
        return wrappee.process(products);
    }
}
