package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Klasa ProductManagerGUI implementuje GUI do zarządzania produktami.
 * Pozwala na dodawanie, edytowanie, usuwanie oraz filtrowanie i sortowanie produktów.
 */
public class ProductManagerGUI implements ProductObserver {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private DataSubject dataSubject;
    private DataProcessor processor;

    private double minPrice = Double.MIN_VALUE;
    private double maxPrice = Double.MAX_VALUE;
    private boolean sortByName = false;
    private boolean sortByPrice = false;

    /**
     * Konstruktor inicjalizujący GUI oraz listę produktów początkowych.
     */
    public ProductManagerGUI() {
        dataSubject = new DataSubject();
        dataSubject.addObserver(this);

        processor = products -> products;

        List<Product> initialProducts = List.of(
                new Product("Laptop", 1500),
                new Product("Smartphone", 800),
                new Product("Tablet", 600),
                new Product("Monitor", 300),
                new Product("Klawiatura", 100)
        );
        dataSubject.setMasterProducts(new java.util.ArrayList<>(initialProducts));
    }

    /**
     * Tworzy i wyświetla graficzny interfejs użytkownika.
     */
    public void createAndShowGUI() {
        frame = new JFrame("Product Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        tableModel = new DefaultTableModel(new Object[]{"Nazwa", "Cena"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(scrollPane, gbc);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints bpGbc = new GridBagConstraints();
        bpGbc.insets = new Insets(5, 5, 5, 5);

        JButton filterButton = new JButton("Filtruj po cenie");
        JButton sortNameButton = new JButton("Sortuj po nazwie");
        JButton sortPriceButton = new JButton("Sortuj po cenie");
        JButton resetButton = new JButton("Resetuj filtry i sortowania");
        JButton addButton = new JButton("Dodaj produkt");
        JButton removeButton = new JButton("Usuń produkt");
        JButton editButton = new JButton("Edytuj produkt");

        bpGbc.gridx = 0;
        bpGbc.gridy = 0;
        bpGbc.anchor = GridBagConstraints.LINE_START;
        buttonPanel.add(filterButton, bpGbc);

        bpGbc.gridx = 1;
        buttonPanel.add(sortNameButton, bpGbc);

        bpGbc.gridx = 2;
        buttonPanel.add(sortPriceButton, bpGbc);

        bpGbc.gridx = 3;
        buttonPanel.add(resetButton, bpGbc);

        bpGbc.gridx = 0;
        bpGbc.gridy = 1;
        buttonPanel.add(addButton, bpGbc);

        bpGbc.gridx = 1;
        buttonPanel.add(removeButton, bpGbc);

        bpGbc.gridx = 2;
        buttonPanel.add(editButton, bpGbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(buttonPanel, gbc);

        filterButton.addActionListener(e -> showFilterDialog());
        sortNameButton.addActionListener(e -> sortByName());
        sortPriceButton.addActionListener(e -> sortByPrice());
        resetButton.addActionListener(e -> resetFiltersAndSortings());
        addButton.addActionListener(e -> showAddProductDialog());
        removeButton.addActionListener(e -> showRemoveProductDialog());
        editButton.addActionListener(e -> showEditProductDialog());

        frame.pack();

        frame.setMinimumSize(new Dimension(800, 600));

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        applyFiltersAndSortings();
    }

    /**
     * Aktualizuje zawartość tabeli na podstawie przekazanej listy produktów.
     */
    @Override
    public void update(List<Product> products) {
        SwingUtilities.invokeLater(() -> {
            tableModel.setRowCount(0);
            for (Product p : products) {
                tableModel.addRow(new Object[]{p.getName(), p.getPrice()});
            }
        });
    }

    /**
     * Wyświetla okno dialogowe do ustawiania filtrów cenowych.
     */
    private void showFilterDialog() {
        JTextField minField = new JTextField();
        JTextField maxField = new JTextField();
        Object[] message = {
                "Minimalna cena:", minField,
                "Maksymalna cena:", maxField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Filtruj po cenie", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String minText = minField.getText().trim();
                String maxText = maxField.getText().trim();

                if (minText.isEmpty()) {
                    minPrice = Double.MIN_VALUE;
                } else {
                    minPrice = Double.parseDouble(minText);
                }

                if (maxText.isEmpty()) {
                    maxPrice = Double.MAX_VALUE;
                } else {
                    maxPrice = Double.parseDouble(maxText);
                }

                if (minPrice > maxPrice) {
                    throw new IllegalArgumentException("Minimalna cena nie może być większa niż maksymalna cena.");
                }

                applyFiltersAndSortings();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Nieprawidłowy format ceny. Proszę wprowadzić liczby.", "Błąd", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Sortuje listę produktów według nazw.
     */
    private void sortByName() {
        sortByName = true;
        sortByPrice = false;
        applyFiltersAndSortings();
    }

    /**
     * Sortuje listę produktów według cen.
     */
    private void sortByPrice() {
        sortByPrice = true;
        sortByName = false;
        applyFiltersAndSortings();
    }

    /**
     * Resetuje wszystkie filtry i sortowania do stanu domyślnego.
     */
    private void resetFiltersAndSortings() {
        minPrice = Double.MIN_VALUE;
        maxPrice = Double.MAX_VALUE;
        sortByName = false;
        sortByPrice = false;
        processor = products -> products;
        applyFiltersAndSortings();
    }

    /**
     * Wyświetla okno dialogowe do dodawania nowego produktu.
     */
    private void showAddProductDialog() {
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        Object[] message = {
                "Nazwa produktu:", nameField,
                "Cena produktu:", priceField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Dodaj produkt", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String priceText = priceField.getText().trim();

                if (name.isEmpty()) {
                    throw new IllegalArgumentException("Nazwa produktu nie może być pusta.");
                }

                if (priceText.isEmpty()) {
                    throw new IllegalArgumentException("Cena produktu nie może być pusta.");
                }

                double price = Double.parseDouble(priceText);

                if (price < 0) {
                    throw new IllegalArgumentException("Cena produktu nie może być ujemna.");
                }

                dataSubject.addProduct(new Product(name, price));
                applyFiltersAndSortings();
                JOptionPane.showMessageDialog(frame, "Produkt został dodany pomyślnie.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Nieprawidłowy format ceny. Proszę wprowadzić liczbę.", "Błąd", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Wyświetla okno dialogowe do usuwania wybranego produktu.
     */
    private void showRemoveProductDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Proszę wybrać produkt do usunięcia.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String productName = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(frame, "Czy na pewno chcesz usunąć produkt: " + productName + "?", "Potwierdzenie usunięcia", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Product toRemove = null;
            for (Product p : dataSubject.getMasterProducts()) {
                if (p.getName().equalsIgnoreCase(productName)) {
                    toRemove = p;
                    break;
                }
            }
            if (toRemove != null) {
                dataSubject.removeProduct(toRemove);
                applyFiltersAndSortings();
                JOptionPane.showMessageDialog(frame, "Produkt został usunięty pomyślnie.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Produkt nie został znaleziony.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Wyświetla okno dialogowe do edycji wybranego produktu.
     */
    private void showEditProductDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Proszę wybrać produkt do edytowania.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String currentName = (String) tableModel.getValueAt(selectedRow, 0);
        Double currentPrice = (Double) tableModel.getValueAt(selectedRow, 1);

        JTextField nameField = new JTextField(currentName);
        JTextField priceField = new JTextField(currentPrice.toString());
        Object[] message = {
                "Nazwa produktu:", nameField,
                "Cena produktu:", priceField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Edytuj produkt", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String newName = nameField.getText().trim();
                String priceText = priceField.getText().trim();

                if (newName.isEmpty()) {
                    throw new IllegalArgumentException("Nazwa produktu nie może być pusta.");
                }

                if (priceText.isEmpty()) {
                    throw new IllegalArgumentException("Cena produktu nie może być pusta.");
                }

                double newPrice = Double.parseDouble(priceText);

                if (newPrice < 0) {
                    throw new IllegalArgumentException("Cena produktu nie może być ujemna.");
                }

                Product toEdit = null;
                for (Product p : dataSubject.getMasterProducts()) {
                    if (p.getName().equalsIgnoreCase(currentName)) {
                        toEdit = p;
                        break;
                    }
                }

                if (toEdit != null) {
                    toEdit.setName(newName);
                    toEdit.setPrice(newPrice);
                    applyFiltersAndSortings();
                    JOptionPane.showMessageDialog(frame, "Produkt został edytowany pomyślnie.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Produkt nie został znaleziony.", "Błąd", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Nieprawidłowy format ceny. Proszę wprowadzić liczbę.", "Błąd", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Zastosowuje aktualne filtry i sortowania do listy produktów.
     */
    private void applyFiltersAndSortings() {
        DataProcessor newProcessor = products -> products;

        if (minPrice > Double.MIN_VALUE || maxPrice < Double.MAX_VALUE) {
            newProcessor = new PriceFilterDecorator(newProcessor, minPrice, maxPrice);
        }

        if (sortByName) {
            newProcessor = new SortByNameDecorator(newProcessor);
        } else if (sortByPrice) {
            newProcessor = new SortByPriceDecorator(newProcessor);
        }

        this.processor = newProcessor;

        List<Product> masterList = dataSubject.getMasterProducts();
        List<Product> processed = processor.process(masterList);
        dataSubject.notifyObserversWith(processed);
    }

    /**
     * Metoda główna uruchamiająca aplikację.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductManagerGUI gui = new ProductManagerGUI();
            gui.createAndShowGUI();
        });
    }
}
