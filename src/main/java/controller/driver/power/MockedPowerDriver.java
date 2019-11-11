/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.driver.power;

import model.PowerState;

/**
 *
 * @author a2049031
 */
public class MockedPowerDriver implements GenericPowerDriver {
    public void Initialize() throws UnsupportedOperationException {}
    boolean ticker = false; // Só pra gerar alteração de estado, gg
    public MockedPowerDriver() {
        this.Initialize();
    }
    
    public PowerState getState() {
        return PowerState.Charging;
    }
    
    public int getChargeLevel() {
        ticker = !ticker;
        return 81 + (ticker ? 1 : 0);
    }
    
}
