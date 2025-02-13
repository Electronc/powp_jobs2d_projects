package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.DriverManager;

public interface DriverFeatureInterface {

    static DriverFeatureInterface instance = null;

    void setupDriverFeature(Application application, DriverManager driverManager);

    static DriverFeatureInterface getInstance() {
        return instance;
    }
  }
