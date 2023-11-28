package Modelo;

import Interfaces.ITransicion;

public class TransicionAFND implements ITransicion {

    private Estado estadoInicial;
    private Estado estadoFinal;
    private char simbolo;

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

}
