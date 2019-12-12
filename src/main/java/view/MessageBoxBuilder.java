package view;

import javax.swing.*;

public class MessageBoxBuilder {
    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInformation(String message) {
        JOptionPane.showMessageDialog(null, message, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }
}
