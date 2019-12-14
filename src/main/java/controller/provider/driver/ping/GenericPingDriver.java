/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.provider.driver.ping;

import controller.provider.driver.GenericDriver;

import java.io.IOException;

/**
 *
 * @author a2049031
 */
public abstract class GenericPingDriver extends GenericDriver {
    public abstract Integer pingTo(String server) throws IOException, InterruptedException;
}
