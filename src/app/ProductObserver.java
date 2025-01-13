package app;

import java.util.List;

public interface ProductObserver {
    void update(List<Product> products);
}
