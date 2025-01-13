package app;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortByPriceDecorator extends DataProcessorDecorator {

    public SortByPriceDecorator(DataProcessor wrappee) {
        super(wrappee);
    }

    @Override
    public List<Product> process(List<Product> products) {
        List<Product> sorted = products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());
        return wrappee.process(sorted);
    }
}
