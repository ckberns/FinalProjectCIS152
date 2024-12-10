import org.junit.Before;
import org.junit.Test;


import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class AppWindowTest {
    private AppWindow appWindow;

    @Before
    public void setUp() {
        appWindow = new AppWindow();
    }

    @Test
    public void testAddPaint() {
        appWindow.brandField.setText("BrandA");
        appWindow.colorField.setText("Red");

        appWindow.addPaint();

        HashMap<String, List<String>> paintColors = appWindow.paintColors;
        assertTrue(paintColors.containsKey("BrandA"));
        assertTrue(paintColors.get("BrandA").contains("Red"));
    }

    @Test
    public void testRemovePaint() {
        appWindow.brandField.setText("BrandA");
        appWindow.colorField.setText("Red");

        appWindow.addPaint();
        appWindow.removePaint();

        HashMap<String, List<String>> paintColors = appWindow.paintColors;
        assertTrue(paintColors.containsKey("BrandA"));
    }

    @Test
    public void testIsValidInput() {
        assertTrue(appWindow.isValidInput("BrandA", "Red"));
        assertFalse(appWindow.isValidInput("", "Red"));
        assertFalse(appWindow.isValidInput("BrandA", ""));
        assertFalse(appWindow.isValidInput("BrandA", "123"));
        assertFalse(appWindow.isValidInput("BrandA", "RedRedRedRedRedRedRedRedRedRedRedRedRedRedRedRedRedRedRedRed"));
    }

    @Test
    public void testSavePaintsToFile() {
        appWindow.brandField.setText("BrandA");
        appWindow.colorField.setText("Red");

        appWindow.addPaint();
        appWindow.savePaintsToFile();
    }
}