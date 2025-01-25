package edu.kis.powp.jobs2d.drivers;

import java.util.ArrayList;
import java.util.List;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.visitor.IVisitor;

public class DriverComposite implements Job2dDriver {
    private List<Job2dDriver> drivers = new ArrayList<>();

    public void addDriver(Job2dDriver driver) {
        drivers.add(driver);
    }

    @Override
    public void operateTo(int x, int y) {
        for (Job2dDriver driver : drivers) {
            driver.operateTo(x, y);
        }
    }

    @Override
    public void setPosition(int x, int y) {
        for (Job2dDriver driver : drivers) {
            driver.setPosition(x, y);
        }
    }

    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    public void visitDrivers(IVisitor visitor) {
        for (Job2dDriver driver : drivers) {
            visitor.visit(driver);
        }
    }
}