package Modelo;

import Interfaces.ITransicionAFND;
import java.util.ArrayList;

public class TransicionLambda implements ITransicionAFND {

    private Estado estadoInicial;
    private ArrayList<Estado> estadosFinales;
    private char simbolo;

    public TransicionLambda(Estado estadoInicial, ArrayList<Estado> estadosFinales) {
        this.estadoInicial = estadoInicial;
        this.estadosFinales = estadosFinales;
        this.simbolo = 'l';
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
        return estadoInicial + " " + estadosFinales;
    }

}
