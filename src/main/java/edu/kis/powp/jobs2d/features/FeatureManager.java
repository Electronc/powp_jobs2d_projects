package edu.kis.powp.jobs2d.features;
import java.util.ArrayList;
import java.util.List;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.DriverManager;

import javax.swing.*;

public class FeatureManager {
	private static Application app;
	private static DriverManager driver;
	private static FeatureManager instance = null;

	public static FeatureManager getInstance(Application app, DriverManager driver) {
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

	public void LoadAllFeatures()
	{
		app.addComponentMenu(FeatureManager.class, "Features");
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
