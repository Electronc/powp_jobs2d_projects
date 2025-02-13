package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.events.SelectClearPanelOptionListener;
import edu.kis.legacy.drawer.panel.DrawPanelController;

public class DrawerFeature implements DriverFeatureInterface {

    private static DrawPanelController drawerController;
    private static DrawerFeature instance;

    public static DrawerFeature getInstance() {
        if (instance == null) {
            synchronized (DrawerFeature.class) {
                if (instance == null) {
                    instance = new DrawerFeature();
                }
            }
        }
        return instance;
    }
   
    
    public void setupDriverFeature(Application application, DriverManager driverManager) {
        SelectClearPanelOptionListener selectClearPanelOptionListener = new SelectClearPanelOptionListener();

        drawerController = new DrawPanelController();
        application.addComponentMenu(DrawPanelController.class, "Draw Panel", 0);
        application.addComponentMenuElement(DrawPanelController.class, "Clear Panel", selectClearPanelOptionListener);

        drawerController.initialize(application.getFreePanel());
    }

    /**
     * Get controller of application drawing panel.
     * 
     * @return drawPanelController.
     */
    public static DrawPanelController getDrawerController() {
        return drawerController;
    }

}
