/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.components;

import controller.MachineStateListener;
import controller.representer.StateRepresenter;
import model.vo.MachineState;

import javax.swing.JButton;

/**
 *
 * @author lucasew
 */
public class DashboardButton extends JButton implements MachineStateListener {

    StateRepresenter extractor;
    public DashboardButton() {
        this.setFont(new java.awt.Font("Dialog", 1, 24));
    }

    public StateRepresenter getExtractor() {
        return extractor;
    }

    public void setExtractor(StateRepresenter extractor) {
        this.extractor = extractor;
    }

    @Override
    public void handleMachineStateChange(MachineState state) {
        if (extractor != null) {
            this.setIcon(extractor.getIcon(state));
            this.setText(extractor.getLabel(state));
        }
    }
}
