/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.extractor;

import controller.ImageProvider;
import controller.reporter.PingReporter;
import model.PingState;

import javax.swing.ImageIcon;

/**
 *
 * @author lucasew
 */
public class PingStateExtractor implements StateExtractor {
    PingReporter task;
    int badNetCriteria;
    
    public PingStateExtractor(PingReporter task, int badNetCriteria) {
        this.task = task;
        this.badNetCriteria = badNetCriteria;
    }
    
    @Override
    public ImageIcon getIcon() {
        PingState latency = task.getLastState();
        if (!latency.isValido()) {
            return ImageProvider.getImage("/META-INF/icon/desconectado.png");
        }
        if (latency.getLatency() > badNetCriteria) {
            return ImageProvider.getImage("/META-INF/icon/sinal_maisoumenos.png");
        } else {
            return ImageProvider.getImage("/META-INF/icon/sinal_bom.png");
        }
    }

    @Override
    public String getLabel() {
        PingState latency = task.getLastState();
        return latency.isValido() ? String.format("Conectado: %d ms",  latency.getLatency()) : "Sem conexão";
    }

    @Override
    public void waitForUpdate() throws InterruptedException {
        task.wait();
    }
}
