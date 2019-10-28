package controller.driver.power;

import java.io.IOException;
import java.nio.file.Paths;

import static java.lang.Integer.*;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.readAllLines;

public class LinuxPowerDriver extends GenericPowerDriver {

    LinuxPowerDriver() throws UnsupportedOperationException {
        if (!exists(Paths.get("/sys/class/power_supply")))
            throw new UnsupportedOperationException("Interfaces não disponiveis para consulta de nivel de energia");
    }

    public PowerState getState() {
        try {
            String rawState = readAllLines(Paths.get("/sys/class/power_supply/BAT1/status")).get(0);
            switch (rawState) {
                case "Full":
                    return PowerState.Full;
                case "Discharging":
                    return PowerState.Discharging;
                case "Charging":
                    return PowerState.Charging;
                default:
                    throw new IllegalStateException("Unexpected value: " + rawState);
            }
        } catch (IOException e) {
            return PowerState.AC; // Se não existir o arquivo não tem bateria, logo AC
        }

    }

    public int getChargeLevel() {
        if (this.getState() != PowerState.AC) {
            try {
                String rawState = readAllLines(Paths.get("/sys/class/power_supply/BAT1/capacity")).get(0);
                return decode(rawState);
            } catch (IOException e) {
                /*
                    Se der este erro o programador realmente fez uma baita cagada.
                    Este arquivo existe em todos os linux, até no android
                */
                throw new IllegalStateException();
            }
        }
        return 100;
    }
}
