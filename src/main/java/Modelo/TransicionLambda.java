package Modelo;

import java.util.ArrayList;

public class TransicionLambda{

    private Estado estadoInicial;
    private ArrayList<Estado> estadosFinales;
    private char simbolo;

    public TransicionLambda(Estado estadoInicial, ArrayList<Estado> estadosFinales) {
        this.estadoInicial = estadoInicial;
        this.estadosFinales = estadosFinales;
        this.simbolo = 'l';
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
        return estadoInicial + " " + estadosFinales;
    }

}
