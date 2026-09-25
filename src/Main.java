import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Use system look and feel for native platform look and clean fonts
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // fallback to default swing if system look-and-feel fails
        }

        // Launch UI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
