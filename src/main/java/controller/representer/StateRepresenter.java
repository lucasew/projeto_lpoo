/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.representer;

import model.vo.MachineState;

import javax.swing.ImageIcon;

/**
 *
 * @author lucasew
 */
public interface StateRepresenter {
    ImageIcon getIcon(MachineState state);
    String getLabel(MachineState state);
}
