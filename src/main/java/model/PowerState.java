package model;

public enum PowerState {
    AC, // Na tomada, sem bateria
    Charging, // Carregando
    Full, // Carregando porém a 100%
    Discharging // Rodando na bateria
}
