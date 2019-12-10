/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.components;

import controller.extractor.StateExtractor;
import javax.swing.JButton;

/**
 *
 * @author lucasew
 */
public class DashboardButton extends JButton implements Runnable {

    StateExtractor extractor;
    public DashboardButton() {
        this.setFont(new java.awt.Font("Dialog", 1, 24));
    }

    public StateExtractor getExtractor() {
        return extractor;
    }

    public void setExtractor(StateExtractor extractor) {
        this.extractor = extractor;
        tick();
        
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                break;
            }
            tick();
        }
    }
    
    void tick() {
        synchronized(this) {
            this.setIcon(extractor.getIcon());
            this.setText(extractor.getLabel());
        }
    }
}
