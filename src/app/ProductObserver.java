package app;

import java.util.List;

/**
 * Interfejs dla obserwatorów, którzy będą powiadamiani o zmianach w liście produktów.
 */
public interface ProductObserver {
    /**
     * Metoda wywoływana w celu aktualizacji obserwatora po zmianie w liście produktów.
     */
    void update(List<Product> products);
}