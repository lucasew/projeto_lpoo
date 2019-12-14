package model.i18n;

import model.vo.PowerState;

public class PowerStateI18NBuilder {
    public static String getName(PowerState state) {
        switch(state) {
            case AC:
                return "Bateria não detectada";
            case Charging:
                return "Carregando";
            case Full:
                return "Carregado";
            case Discharging:
                return "Descarregando";
            default:
                throw new IllegalStateException("Unexpected value: " + state);
        }
    }
}
