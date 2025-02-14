package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.SelectDriverMenuOptionListener;
import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.drivers.observer.UpdateDriverInfoSubscriber;
import edu.kis.powp.jobs2d.drivers.observer.UpdateDriverVisitorSubscriber;

public class DriverFeature implements FeatureInterface{

    private static final DriverManager driverManager = new DriverManager();
    private static Application app;
    private static final UsageMonitorFeature usageMonitorFeature = new UsageMonitorFeature();
    private static DriverFeature instance;

    public static DriverFeature getInstance() {
        if (instance == null) {
            synchronized (DriverFeature.class) {
                if (instance == null) {
                    instance = new DriverFeature();
                }
            }
        }
        return instance;
    }
    public static DriverManager getDriverManager() {
        return driverManager;
    }

    /**
     * Setup jobs2d drivers Plugin and add to application.
     *
     * @param application Application context.
     */
    public void setupFeature(Application application, DriverManager driverManager) {
        app = application;
        app.addComponentMenu(DriverFeature.class, "Drivers");

        UpdateDriverInfoSubscriber updateDriverInfoSubscriber = new UpdateDriverInfoSubscriber(app);
        UpdateDriverVisitorSubscriber updateDriverVisitorSubscriber = new UpdateDriverVisitorSubscriber();
        driverManager.addSubscriber(updateDriverInfoSubscriber);
        driverManager.addSubscriber(updateDriverVisitorSubscriber);
    }

    /**
     * Add driver to context, create button in driver menu.
     *
     * @param name   Button name.
     * @param driver Job2dDriver object.
     */
    public static void addDriver(String name, VisitableJob2dDriver driver) {
        SelectDriverMenuOptionListener listener = new SelectDriverMenuOptionListener(driver, driverManager);
        app.addComponentMenuElement(DriverFeature.class, name, listener);
    }
}
