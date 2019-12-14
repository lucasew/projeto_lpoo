/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.provider.driver.ping;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author a2049031
 */
public class PingDriverFallbacker {
   private static List<Class<? extends GenericPingDriver>> pretendents =
            new ArrayList<Class<? extends GenericPingDriver>>() {{
                add(LinuxPingDriver.class);
            }};
    private static GenericPingDriver backend = null;

    public static GenericPingDriver getDriver() {
        if (PingDriverFallbacker.backend != null) {
            return PingDriverFallbacker.backend;
        }
        for (Class<? extends GenericPingDriver> pretendent : pretendents) {
            try {
                PingDriverFallbacker.backend = pretendent.newInstance();
                return PingDriverFallbacker.backend;
            } catch (IllegalAccessException | InstantiationException | UnsupportedOperationException e) {
                System.out.println(String.format("%s não pode ser utilizado neste contexto por não ser suportado.", pretendent.toString()));
                continue;
            }
        }
        throw new UnsupportedOperationException();
    }    
}
