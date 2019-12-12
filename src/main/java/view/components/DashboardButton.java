/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.components;

import controller.capture.CaptureListener;
import controller.extractor.StateExtractor;
import model.MachineState;

import javax.swing.JButton;

/**
 *
 * @author lucasew
 */
public class DashboardButton extends JButton implements CaptureListener {

    StateExtractor extractor;
    public DashboardButton() {
        this.setFont(new java.awt.Font("Dialog", 1, 24));
    }

    public StateExtractor getExtractor() {
        return extractor;
    }

    public void setExtractor(StateExtractor extractor) {
        this.extractor = extractor;
    }

    @Override
    public void handleMachineStateCapture(MachineState state) {
        if (extractor != null) {
            this.setIcon(extractor.getIcon(state));
            this.setText(extractor.getLabel(state));
        }
    }
}
