/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.driver.power;

import model.vo.PowerState;

/**
 *
 * @author a2049031
 */
public class MockedPowerDriver extends GenericPowerDriver {
    @Override
    public void Initialize() throws UnsupportedOperationException {}
    boolean ticker = false; // S� pra gerar altera��o de estado, gg
    public MockedPowerDriver() {
        this.Initialize();
    }
    
    @Override
    public PowerState getState() {
        return PowerState.Charging;
    }
    
    @Override
    public int getChargeLevel() {
        ticker = !ticker;
        return 81 + (ticker ? 1 : 0);
    }
    
}
