package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.manager.DriverCommandManager;
import edu.kis.powp.jobs2d.command.manager.LoggerCommandChangeObserver;
import edu.kis.powp.jobs2d.drivers.DriverManager;

public class CommandsFeature implements DriverFeatureInterface {

    private static DriverCommandManager commandManager;
    private static CommandsFeature instance;

    public static CommandsFeature getInstance() {
        if (instance == null) {
            synchronized (CommandsFeature.class) {
                if (instance == null) {
                    instance = new CommandsFeature();
                }
            }
        }
        return instance;
    }
    public void setupDriverFeature(Application application, DriverManager driverManager) {
        commandManager = new DriverCommandManager();

        LoggerCommandChangeObserver loggerObserver = new LoggerCommandChangeObserver();
        commandManager.getChangePublisher().addSubscriber(loggerObserver);
    }

    /**
     * Get manager of application driver command.
     * 
     * @return plotterCommandManager.
     */
    public static DriverCommandManager getDriverCommandManager() {
        return commandManager;
    }

}
