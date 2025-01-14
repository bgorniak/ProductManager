package app;


/**
 * Klasa Main uruchamia aplikację z graficznym interfejsem użytkownika (GUI).
 */
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            ProductManagerGUI gui = new ProductManagerGUI();
            gui.createAndShowGUI();
        });
    }
}
