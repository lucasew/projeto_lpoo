/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.extractor;

import controller.ImageProvider;
import controller.daemon.task.PingTask;
import javax.swing.ImageIcon;

/**
 *
 * @author lucasew
 */
public class PingStateExtractor implements StateExtractor {
    PingTask task;
    int badNetCriteria;
    
    public PingStateExtractor(PingTask task, int badNetCriteria) {
        this.task = task;
        this.badNetCriteria = badNetCriteria;
    }
    
    @Override
    public ImageIcon getIcon() {
        Integer latency = task.getLastState().getLatency();
        if (latency == null) {
            return ImageProvider.getImage("/META-INF/icon/desconectado.png");
        }
        if (latency > badNetCriteria) {
            return ImageProvider.getImage("/META-INF/icon/sinal_maisoumenos.png");
        } else {
            return ImageProvider.getImage("/META-INF/icon/sinal_bom.png");
        }
    }

    @Override
    public String getLabel() {
        Integer latency = task.getLastState().getLatency();
        return latency == null ? "Sem conexão" : String.format("Conectado: %d ms",  latency);
    }

    @Override
    public void waitForUpdate() throws InterruptedException {
        task.wait();
    }
}
