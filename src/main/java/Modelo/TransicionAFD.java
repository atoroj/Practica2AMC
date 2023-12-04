package Modelo;

import Interfaces.ITransicionAFD;

public class TransicionAFD implements ITransicionAFD{

    private Estado estadoInicial;
    private Estado estadoFinal;
    private char simbolo;

    public TransicionAFD(Estado estadoInicial, char simbolo, Estado estadoFinal) {
        this.estadoInicial = estadoInicial;
        this.estadoFinal = estadoFinal;
        this.simbolo = simbolo;
    }

    @Override
    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    @Override
    public Estado getEstadoFinal() {
        return estadoFinal;
    }

    @Override
    public char getSimbolo() {
        return simbolo;
    }

    @Override
    public String toString() {
        return estadoInicial + " '" + simbolo + "' " + estadoFinal;
    }
    
}
