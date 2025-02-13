package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.DriverManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("Convert2Lambda")
public class MouseClickDrawFeature implements DriverFeatureInterface{

	private static Application application;
	private static DriverManager driverManager;
	private static boolean armed = false;

	public static void setupDriverFeature(
			Application application,
			DriverManager driverManager
	) {
		MouseClickDrawFeature.application = application;
		MouseClickDrawFeature.driverManager = driverManager;
		MouseClickDrawFeature.application.addComponentMenu(MouseClickDrawFeature.class, "Click to Draw");
		MouseClickDrawFeature.application.addComponentMenuElementWithCheckBox(MouseClickDrawFeature.class, "Armed", armedListener, false);

		MouseClickDrawFeature.application.getFreePanel().addMouseListener(mouseClickListener);
	}

	private static final ActionListener armedListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof JCheckBoxMenuItem) {
				JCheckBoxMenuItem checkbox = (JCheckBoxMenuItem) e.getSource();

				armed = checkbox.getState();
			}
		}
	};

	private static final MouseAdapter mouseClickListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (armed) {
				int x = translateCoordinateX(e.getX());
				int y = translateCoordinateY(e.getY());
				Job2dDriver currentDriver = MouseClickDrawFeature.driverManager.getCurrentDriver();
				if (SwingUtilities.isLeftMouseButton(e)) {
					currentDriver.operateTo(x, y);
				} else if (SwingUtilities.isRightMouseButton(e)) {
					currentDriver.setPosition(x, y);
				}
			}
		}
	};

	private static int translateCoordinateX(int x) {
		int centerX = MouseClickDrawFeature.application.getFreePanel().getWidth() / 2;
		return x - centerX;
	}

	private static int translateCoordinateY(int y) {
		int centerY = MouseClickDrawFeature.application.getFreePanel().getHeight() / 2;
		return y - centerY;
	}

}
