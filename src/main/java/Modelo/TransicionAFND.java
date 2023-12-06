package Modelo;

import java.util.ArrayList;

public class TransicionAFND{

    private Estado estadoInicial;
    private ArrayList<Estado> estadosFinales;
    private char simbolo;

    public TransicionAFND(Estado estadoInicial, char simbolo, ArrayList<Estado> estadosFinales) {
        this.estadoInicial = estadoInicial;
        this.estadosFinales = estadosFinales;
        this.simbolo = simbolo;
    }

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public ArrayList<Estado> getEstadosFinales() {
        return estadosFinales;
    }

    public char getSimbolo() {
        return simbolo;
    }
    
    @Override
    public String toString() {
        return estadoInicial + " '" + simbolo + "' " + estadosFinales;
    }
}
