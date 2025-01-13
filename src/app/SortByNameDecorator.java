package app;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortByNameDecorator extends DataProcessorDecorator {

    public SortByNameDecorator(DataProcessor wrappee) {
        super(wrappee);
    }

    @Override
    public List<Product> process(List<Product> products) {
        List<Product> sorted = products.stream()
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
        return wrappee.process(sorted);
    }
}
