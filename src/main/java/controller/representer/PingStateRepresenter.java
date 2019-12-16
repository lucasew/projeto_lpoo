/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.representer;

import controller.ImageProvider;
import model.vo.MachineState;
import model.vo.PingState;

import javax.swing.ImageIcon;

/**
 *
 * @author lucasew
 */
public class PingStateRepresenter implements StateRepresenter {
    int badNetCriteria;

    public PingStateRepresenter(int badNetCriteria) {
        this.badNetCriteria = badNetCriteria;
    }

    @Override
    public ImageIcon getIcon(MachineState state) {
        PingState pingState = state.getPingState();
        if (!pingState.isValido()) {
            return ImageProvider.getImage("/META-INF/icon/desconectado.png");
        }
        if (pingState.getLatency() > badNetCriteria) {
            return ImageProvider.getImage("/META-INF/icon/sinal_maisoumenos.png");
        } else {
            return ImageProvider.getImage("/META-INF/icon/sinal_bom.png");
        }
    }

    @Override
    public String getLabel(MachineState state) {
        PingState pingState = state.getPingState();
        return pingState == null
            ? "[DESCONHEDIDO]"
            : pingState.isValido() ? String.format("Conectado: %d ms",  pingState.getLatency()) : "Sem conexão";
    }
}
