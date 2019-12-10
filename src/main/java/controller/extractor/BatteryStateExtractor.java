/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.extractor;

import controller.ImageProvider;
import controller.reporter.BatteryReporter;
import javax.swing.ImageIcon;

/**
 *
 * @author lucasew
 */
public class BatteryStateExtractor implements StateExtractor {
    BatteryReporter task;

    public BatteryStateExtractor(BatteryReporter task) {
        this.task = task;
    }
    
    @Override
    public ImageIcon getIcon() {
        switch (task.getLastState().getState()) {
            case AC:
                return ImageProvider.getImage("/META-INF/icon/ac.png");
            case Charging:
                return ImageProvider.getImage("/META-INF/icon/battery_charging.png");
            case Full:
                return ImageProvider.getImage("/META-INF/icon/battery_charging.png");
            case Discharging:
                return ImageProvider.getImage("/META-INF/icon/battery.png");
        }
        return ImageProvider.getImage("/META-INF/icon/battery_unknown.png");
    }

    @Override
    public String getLabel() {
        return String.format("%d %% - %s", task.getLastState().getLevel(), task.getLastState().getState().toString());
    }

    @Override
    public void waitForUpdate() throws InterruptedException {
        synchronized(this.task) {
            this.task.wait();
        }
    }
}
