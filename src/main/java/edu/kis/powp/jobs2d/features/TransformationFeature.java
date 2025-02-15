package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.adapter.transformation.ToggleTransformationOptionListener;
import edu.kis.powp.jobs2d.drivers.adapter.transformation.TransformationDriver;
import edu.kis.powp.jobs2d.drivers.adapter.transformation.TransformationMethod;
import edu.kis.powp.jobs2d.drivers.observer.ApplyDriverDecoratorsSubscriber;
import edu.kis.powp.observer.Publisher;

public class TransformationFeature implements FeatureInterface{
    private static final TransformationDriver transformationDriver = new TransformationDriver();
    private static final Publisher publisher = new Publisher();
    private static TransformationFeature instance;

    /**
     * Setup jobs2d drivers Plugin and add to application.
     */

    public static TransformationFeature getInstance() {
        if (instance == null) {
            synchronized (TransformationFeature.class) {
                if (instance == null) {
                    instance = new TransformationFeature();
                }
            }
    }
        return instance;
    }

    public void setupFeature(Application application, DriverManager driverManager) {
        FeatureManager.getInstance().addFeatureMenuItem(TransformationFeature.class,"Transformations",null);

        ApplyDriverDecoratorsSubscriber.getInstance().addDriverDecorator(transformationDriver);
        driverManager.addSubscriber(ApplyDriverDecoratorsSubscriber.getInstance());
    }

    /**
     * Add driver to context, create button in driver menu.
     *
     * @param name          Button name.
     */
    public static void addTransformation(String name, TransformationMethod transformationMethod) {
        ToggleTransformationOptionListener listener = new ToggleTransformationOptionListener(transformationDriver, transformationMethod, publisher);
        FeatureManager.getInstance().addFeatureMenuItemWithCheckBox(TransformationFeature.class, name, listener, false);
    }

}
