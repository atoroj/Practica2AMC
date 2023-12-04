package Modelo;

import Interfaces.ITransicionAFND;
import java.util.ArrayList;

public class TransicionAFND implements ITransicionAFND {

    private Estado estadoInicial;
    private ArrayList<Estado> estadosFinales;
    private char simbolo;

    public TransicionAFND(Estado estadoInicial, char simbolo, ArrayList<Estado> estadosFinales) {
        this.estadoInicial = estadoInicial;
        this.estadosFinales = estadosFinales;
        this.simbolo = simbolo;
    }

    @Override
    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    @Override
    public ArrayList<Estado> getEstadosFinales() {
        return estadosFinales;
    }

    @Override
    public char getSimbolo() {
        return simbolo;
    }
    
    @Override
    public String toString() {
        return estadoInicial + " '" + simbolo + "' " + estadosFinales;
    }
}
