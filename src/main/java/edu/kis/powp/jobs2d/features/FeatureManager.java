package edu.kis.powp.jobs2d.features;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.DriverManager;

import javax.swing.*;

public class FeatureManager {
	private static Application app;
	private static DriverManager driver;
	private static FeatureManager instance = null;
	private static JMenu menu = null;
	private Map<Class<?>, JMenu> menuList = new HashMap<>();


	public static FeatureManager getInstance() {
		if(instance==null)
		{
			throw new RuntimeException("FeatureManager has not been created");
		}
		return instance;
	}

	public static FeatureManager createInstance(Application app, DriverManager driver) {
		if (instance == null) {
			synchronized (FeatureManager.class) {
				if (instance == null) {
					instance = new FeatureManager(app, driver);
				}
			}
		}
		return instance;
	}
	
	public FeatureManager(Application application, DriverManager driverManager)
	{
		app = application;
		driver = driverManager;
	}
	private final List<FeatureInterface> features = new ArrayList<>();

	private JMenu findMenu(JMenuBar menuBar) {
		return java.util.stream.IntStream.range(0, menuBar.getMenuCount())
				.mapToObj(menuBar::getMenu)
				.filter(menu -> "Features".equals(menu.getText()))
				.findFirst()
				.orElse(null);
	}

	private void setupMenu()
	{
		app.addComponentMenu(FeatureManager.class, "Features");
		menu = this.findMenu(app.getFreePanel().getRootPane().getJMenuBar());
	}

	public void addFeatureMenuItem(Class<?> compClass, String name, ActionListener listener)
	{
		if (!menuList.containsKey(compClass)) {
			JMenu compMenu = new JMenu(name);
			menu.add(compMenu);
			menuList.put(compClass, compMenu);
		}
	}

	public void addFeatureMenuElement(Class<?> compClass, String name, ActionListener listener)
	{
		JMenuItem menuItem = new JMenuItem(name);
		menuList.get(compClass).add(menuItem);
		menuItem.addActionListener(listener);
	}

	public void addFeatureMenuItemWithCheckBox(Class<?> compClass, String name, ActionListener listener, boolean selected)
	{
		JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(name);
		menuList.get(compClass).add(menuItem);
		menuItem.addActionListener(listener);
		menuItem.setSelected(selected);
	}

	public void LoadAllFeatures()
	{
		this.setupMenu();
		this.addFeature(CommandsFeature.getInstance());
		this.addFeature(UsageMonitorFeature.getInstance());
		this.addFeature(DriverFeature.getInstance());
		this.addFeature(MouseClickDrawFeature.getInstance());
		this.addFeature(TransformationFeature.getInstance());
		this.addFeature(CanvasFeature.getInstance());
		this.addFeature(DrawerFeature.getInstance());
		this.addFeature(MacroFeature.getInstance());
		this.setupAll();
	}

    private void addFeature(FeatureInterface feature) {
    	System.out.print("setup 1");
        features.add(feature);
    }

    private void setupAll() {
        for (FeatureInterface feature : features) {
            feature.setupFeature(app, driver);
        }
    }
}
