/**
 * This file contains the class/methods that create the GUI used within my application. This class is implemented in the
 * PaintCollectionApp.java file.
 */

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * AppWindow class represents the main window of the Paint Collection application
 * This allows users to add, remove, display, and save paint colors, associated with brands, into an easy-to-read list
 */
public class AppWindow extends JFrame {
    HashMap<String, List<String>> paintColors;
    JTextField brandField;
    JTextField colorField;

    /**
     * Constructs an AppWindow object and initializes the UI components
     */
    public AppWindow() {
        paintColors = new HashMap<>();
        createUI();
    }

    /**
     * Creates and sets up the UI components for the application
     */
    private void createUI() {
        setTitle("Paint Collection");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        brandField = new JTextField(15);
        colorField = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(new JLabel("Paint Brand:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(brandField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Paint Color:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(colorField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton addButton = new JButton("Add Color");
        addButton.setPreferredSize(new Dimension(100, 30));
        addButton.addActionListener(e -> addPaint());

        JButton removeButton = new JButton("Remove Color");
        removeButton.setPreferredSize(new Dimension(120, 30));
        removeButton.addActionListener(e -> removePaint());

        JButton displayButton = new JButton("Display Paints");
        displayButton.setPreferredSize(new Dimension(120, 30));
        displayButton.addActionListener(e -> displayPaints());

        JButton saveButton = new JButton("Save Paints");
        saveButton.setPreferredSize(new Dimension(120, 30));
        saveButton.addActionListener(e -> savePaintsToFile());

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(saveButton);

        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /**
     * Adds a paint color to the collection if the input is valid and the color does not already exist for the brand
     */
    void addPaint() {
        String brand = brandField.getText().trim();
        String color = colorField.getText().trim();

        if (!isValidInput(brand, color)) {
            return;
        }

        paintColors.putIfAbsent(brand, new ArrayList<>());
        List<String> colors = paintColors.get(brand);
        if (!colors.contains(color)) {
            colors.add(color);
            brandField.setText("");
            colorField.setText("");
            JOptionPane.showMessageDialog(this,
                    "Paint added successfully!");
        } else {
            JOptionPane.showMessageDialog(this,
                    "This color already exists for the selected brand.");
        }
    }

    /**
     * Displays the list of paint colors in a new window
     */
    void displayPaints() {
        JFrame displayFrame = new JFrame("Paint List");
        displayFrame.setSize(400, 300);
        displayFrame.setLocationRelativeTo(null);

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);

        StringBuilder displayTxt = new StringBuilder();
        for (String brand : paintColors.keySet()) {
            displayTxt.append("Brand: ").append(brand).append("\n Colors: ").append(paintColors.get(brand)).append("\n");
        }
        displayArea.setText(displayTxt.toString());

        displayFrame.add(new JScrollPane(displayArea));
        displayFrame.setVisible(true);
    }

    /**
     * Saves the list of paint colors to a text file
     */
    void savePaintsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("PaintList.txt"))) {
            for (String brand : paintColors.keySet()) {
                writer.write("Brand: " + brand + "\n");
                writer.write("Colors: " + paintColors.get(brand) + "\n");
            }
            JOptionPane.showMessageDialog(this,
                    "Paint list saved to PaintList.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error saving paint list: " + e.getMessage());
        }
    }

    /**
     * Removes a paint color from the collection if the input is valid and the color exists for the brand
     */
    void removePaint() {
        String brand = brandField.getText().trim();
        String color = colorField.getText().trim();

        if (!isValidInput(brand, color)) {
            return;
        }

        List<String> colors = paintColors.get(brand);
        if (colors != null && colors.contains(color)) {
            colors.remove(color);
            if (colors.isEmpty()) {
                paintColors.remove(brand);
            }
            brandField.setText("");
            colorField.setText("");
            JOptionPane.showMessageDialog(this,
                    "Paint removed successfully!");
        } else {
            JOptionPane.showMessageDialog(this,
                    "This color does not exist for the selected brand.");
        }
    }

    /**
     * Validates the input for brand and color fields
     *
     * @param brand     the brand name to validate
     * @param color     the color name to validate
     * @return          true if the input is valid, false otherwise
     */
    boolean isValidInput(String brand, String color) {
        if (brand.isEmpty() || color.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both brand and color.");
            return false;
        }

        if (brand.length() > 20 || color.length() > 20) {
            JOptionPane.showMessageDialog(this,
                    "Brand and color should not exceed 20 characters.");
            return false;
        }

        if (!brand.matches("[a-zA-Z ]+") || !color.matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(this,
                    "Brand and color should only contain letters and spaces.");
            return false;
        }

        return true;
    }
}
