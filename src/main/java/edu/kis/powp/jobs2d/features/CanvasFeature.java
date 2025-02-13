package edu.kis.powp.jobs2d.features;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.canvas.ICanvas;
import edu.kis.powp.jobs2d.drivers.CanvasRestrictionDriverDecorator;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.ToggleDriverDecoratorOptionListener;
import edu.kis.powp.observer.Publisher;

public class CanvasFeature implements DriverFeatureInterface{
    private static Application application;
    private static List<ICanvas> canvases = null;
    private static ICanvas currentCanvas;
    private static Publisher changePublisher = new Publisher();
    private static final CanvasRestrictionDriverDecorator driverDecorator = new CanvasRestrictionDriverDecorator(null);

    /**
     * Initializes and sets up the canvas feature for the application.
     * It creates and adds the 'Canvas Settings' menu along with submenus for grouped canvases.
     *
     * @param app The application instance to which the canvas feature will be added.
     */
    
    public CanvasFeature(Application app) {
    	application = app;
    }
    
    public static void setupCanvasFeature(Application app) {
        application = app;
        application.addComponentMenu(CanvasFeature.class, "Canvas Settings", 0);

        JMenu mainMenu = getMainMenu();

        Map<String, JMenu> groupMenus = new HashMap<>();

        for (ICanvas canvas : canvases) {
            String group = canvas.getGroup();
            groupMenus.putIfAbsent(group, new JMenu(group + " formats"));
            addMenuItem(groupMenus.get(group), canvas, canvas.getName());
        }

        for (JMenu groupMenu : groupMenus.values()) {
            mainMenu.add(groupMenu);
        }

        addDriverDecorator("Canvas restiction");
    }

    public static void setCanvases(List<ICanvas> canvases) {
        CanvasFeature.canvases = canvases;
    }

    /**
     * Retrieves the main menu for the application.
     * If the "Canvas Settings" menu exists, it returns it; otherwise, it creates a new one.
     *
     * @return The main menu for canvas settings.
     */
    private static JMenu getMainMenu() {
        JMenuBar menuBar = application.getFreePanel().getRootPane().getJMenuBar();

        JMenu mainMenu = findMenu(menuBar);
        if (mainMenu != null) {
            return mainMenu;
        }

        mainMenu = new JMenu("Canvas Settings");
        menuBar.add(mainMenu);
        return mainMenu;
    }

    public static ICanvas getCurrentCanvas() {
        return currentCanvas;
    }

    /**
     * Searches for an existing menu with the name "Canvas Settings" in the provided menu bar.
     *
     * @param menuBar The menu bar to search in.
     * @return The found menu, or null if no menu with the given name exists.
     */
    private static JMenu findMenu(JMenuBar menuBar) {
        return java.util.stream.IntStream.range(0, menuBar.getMenuCount())
                .mapToObj(menuBar::getMenu)
                .filter(menu -> "Canvas Settings".equals(menu.getText()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds a new menu item to the specified menu.
     * The item is associated with a shape and its action triggers the drawing of a canvas.
     *
     * @param menu The menu to which the item will be added.
     * @param canvas The shape associated with the menu item.
     * @param name The name of the menu item.
     */
    private static void addMenuItem(JMenu menu, ICanvas canvas, String name) {
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.addActionListener(e -> {
            currentCanvas = canvas;
            drawCanvas(canvas);
            changePublisher.notifyObservers();
        });
        menu.add(menuItem);
    }

    public static void addDriverDecorator(String name) {
        ToggleDriverDecoratorOptionListener listener = new ToggleDriverDecoratorOptionListener(driverDecorator);
        application.addComponentMenuElementWithCheckBox(CanvasFeature.class, name, listener, false);
    }

    /**
     * Triggers the drawing of the given canvas. It retrieves the driver manager and delegates the drawing operation.
     *
     * @param canvas The canvas to be drawn.
     */
    public static void drawCanvas(ICanvas canvas) {
        DriverManager driverManager = DriverFeature.getDriverManager();
        canvas.getCommand().execute(driverManager.getCurrentDriver());
    }

    public static Publisher getChangePublisher() {
        return changePublisher;
    }

	@Override
	public void setupDriverFeature(Object... params) {
		// TODO Auto-generated method stub
		
	}
}