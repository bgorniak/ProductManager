package app;

import java.util.List;

public interface DataProcessor {
    List<Product> process(List<Product> products);
}
