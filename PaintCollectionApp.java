/**
 * This is the driver file for my MiniaturePaintTracker application. The program works by opening a GUI that prompts the
 * user to enter the brand and color associated with a paint. They can then choose to save the paint to their collection
 * list using a .txt file. The user also has the ability to view their collection list so far, as well as remove certain
 * colors from their collection. To achieve this, simply reenter the brand and color, then click the 'Remove Color'
 * button.
 *
 * @author Brandon Kerns
 * @version 1.4
 * @since 1.0
 */

import javax.swing.*;

public class PaintCollectionApp {
    /**
     * The main method to run the application
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppWindow app = new AppWindow();
            app.setVisible(true);
        });
    }
}
