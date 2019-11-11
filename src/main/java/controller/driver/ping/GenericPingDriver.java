/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.driver.ping;

import java.io.IOException;

/**
 *
 * @author a2049031
 */
public interface GenericPingDriver {
    void Initialize() throws UnsupportedOperationException;
    
    Integer pingTo(String server) throws IOException, InterruptedException;
}
