package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.adapter.monitor.DeviceMonitorDriver;
import edu.kis.powp.jobs2d.drivers.observer.ApplyDriverDecoratorsSubscriber;

import java.sql.Driver;

public class UsageMonitorFeature implements FeatureInterface{

    private static final DeviceMonitorDriver deviceMonitorDriver = new DeviceMonitorDriver();
    private static UsageMonitorFeature instance;

    public static UsageMonitorFeature getInstance() {
        if (instance == null) {
            synchronized (UsageMonitorFeature.class) {
                if (instance == null) {
                    instance = new UsageMonitorFeature();
                }
            }
        }
        return instance;
    }

    public void setupFeature(Application application, DriverManager driverManager) {
        ApplyDriverDecoratorsSubscriber.getInstance().addDriverDecorator(deviceMonitorDriver);
        ApplyDriverDecoratorsSubscriber.getInstance().setDriverManager(driverManager);
        driverManager.addSubscriber(ApplyDriverDecoratorsSubscriber.getInstance());
    }
}
