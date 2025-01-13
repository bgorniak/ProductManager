package app;

import java.util.List;

public abstract class DataProcessorDecorator implements DataProcessor {
    protected DataProcessor wrappee;

    public DataProcessorDecorator(DataProcessor wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public List<Product> process(List<Product> products) {
        return wrappee.process(products);
    }
}
