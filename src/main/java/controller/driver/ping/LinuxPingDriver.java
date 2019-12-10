/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.driver.ping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author a2049031
 */
class LinuxPingDriver implements GenericPingDriver {
    private static final Pattern pattern = Pattern.compile("time=[0-9]+");
    private static final Pattern number = Pattern.compile("[0-9]+");

    @Override
    public void Initialize() throws UnsupportedOperationException {
        boolean isWindows = System.getProperty("os.name").startsWith("Linux");
        if (!isWindows) {
            throw new UnsupportedOperationException("Plataforma nãoo suportada.");
        }
    }

    LinuxPingDriver() {
        Initialize();
    }

    @Override
    public Integer pingTo(String server) throws IOException, InterruptedException {
        Runtime rt = Runtime.getRuntime();
        Process proc;
        String cmd = String.format("ping -c 1 %s", server);
        proc = rt.exec(cmd);
        int ecode = proc.waitFor();
        BufferedReader r = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        if (ecode != 0) {
            return null;
        };
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            Matcher mexpr = LinuxPingDriver.pattern.matcher(line).usePattern(LinuxPingDriver.pattern);
            while (mexpr.find()) {
                Matcher mnum = LinuxPingDriver.number.matcher(mexpr.group());
                if (mnum.find()) {
                     return Integer.parseInt(mnum.group());
                }
            }
        }
        return null;
    }
}
