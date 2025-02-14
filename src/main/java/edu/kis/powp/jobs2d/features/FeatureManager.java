package edu.kis.powp.jobs2d.features;
import java.util.ArrayList;
import java.util.List;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.DriverManager;

public class FeatureManager {
	private static Application app;
	private static DriverManager driver;
	
	public FeatureManager(Application application, DriverManager driverManager)
	{
		app = application;
		driver = driverManager;
	}
	private final List<FeatureInterface> features = new ArrayList<>();

    public void addFeature(FeatureInterface feature) {
    	System.out.print("setup 1");
        features.add(feature);
    }

    public void setupAll() {
        for (FeatureInterface feature : features) {
            feature.setupFeature(app, driver);
            
        }
    }
}
