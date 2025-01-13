package app;

import java.util.List;
import java.util.stream.Collectors;

public class PriceFilterDecorator extends DataProcessorDecorator {
    private double minPrice;
    private double maxPrice;

    public PriceFilterDecorator(DataProcessor wrappee, double minPrice, double maxPrice) {
        super(wrappee);
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public List<Product> process(List<Product> products) {
        List<Product> filtered = products.stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
        return wrappee.process(filtered);
    }
}
