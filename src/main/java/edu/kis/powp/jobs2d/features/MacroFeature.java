package edu.kis.powp.jobs2d.features;
import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.builder.CompoundCommandBuilder;
import edu.kis.powp.jobs2d.command.manager.DriverCommandManager;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.RecordMacroDriverDecorator;
import edu.kis.powp.jobs2d.drivers.observer.ApplyDriverDecoratorsSubscriber;
import edu.kis.powp.jobs2d.events.SelectMacroOptionListener;
import edu.kis.powp.jobs2d.events.SelectMacroOptionListener.MacroAction;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.swing.*;
import java.util.Objects;

public class MacroFeature implements FeatureInterface {
    private static Application application;
    private static MacroFeature instance;
    private static DriverManager driverManager;
    private static CompoundCommandBuilder recordedCommand;
    private static final RecordMacroDriverDecorator recordMacroDriverDecorator = new RecordMacroDriverDecorator();


    public static MacroFeature getInstance() {
        if (instance == null) {
            synchronized (MacroFeature.class) {
                if (instance == null) {
                    instance = new MacroFeature();
                }
            }
        }
        return instance;
    }

    private static boolean isRecording = false;
    public void setupFeature(Application application, DriverManager driverManager){
        recordedCommand = new CompoundCommandBuilder();
        recordedCommand.setName("Record command");

        SelectMacroOptionListener clearOption = new SelectMacroOptionListener(MacroAction.CLEAR);
        SelectMacroOptionListener startOption = new SelectMacroOptionListener(MacroAction.TOGGLE);
        SelectMacroOptionListener loadOption = new SelectMacroOptionListener(MacroAction.LOAD);

        application.addComponentMenu(MacroFeature.class, "Macro");
        application.addComponentMenuElement(MacroFeature.class, "Clear", clearOption);
        application.addComponentMenuElementWithCheckBox(MacroFeature.class, "Start/Stop", startOption, false);
        application.addComponentMenuElement(MacroFeature.class, "Load", loadOption);

        ApplyDriverDecoratorsSubscriber.getInstance().addDriverDecorator(recordMacroDriverDecorator);
        driverManager.addSubscriber(ApplyDriverDecoratorsSubscriber.getInstance());
    }
    public static void setCommand(DriverCommand command){
        if(isRecording){
            recordedCommand.addCommand(command);
        }
    }

    public static void toggle(){
        isRecording = !isRecording;
    }
    
    public static void load(){
        CompoundCommand command = getRecordedCommand();
        DriverCommandManager manager = CommandsFeature.getDriverCommandManager();
        manager.setCurrentCommand(command);
    }

    public static void clear(){
        recordedCommand.clear();
    }

    public static boolean isRecording() { return isRecording; }

    public static CompoundCommand getRecordedCommand() {
        return recordedCommand.build();
    }
}