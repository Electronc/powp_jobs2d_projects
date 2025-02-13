package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.adapter.transformation.ToggleTransformationOptionListener;
import edu.kis.powp.jobs2d.drivers.adapter.transformation.TransformationDriver;
import edu.kis.powp.jobs2d.drivers.adapter.transformation.TransformationMethod;
import edu.kis.powp.jobs2d.drivers.observer.ApplyDriverDecoratorsSubscriber;
import edu.kis.powp.observer.Publisher;

public class TransformationFeature implements DriverFeatureInterface{
    private static final TransformationDriver transformationDriver = new TransformationDriver();
    private static final Publisher publisher = new Publisher();
    private static Application app;

    /**
     * Setup jobs2d drivers Plugin and add to application.
     *
     * @param application Application context.
     */
    public static void setupDriverFeature(Application application, DriverManager driverManager) {
        app = application;
        app.addComponentMenu(TransformationFeature.class, "Transformations");

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
        app.addComponentMenuElementWithCheckBox(TransformationFeature.class, name, listener, false);
    }

}
