package Modelo;


public class TransicionAFD{

    private Estado estadoInicial;
    private Estado estadoFinal;
    private char simbolo;

    public TransicionAFD(Estado estadoInicial, char simbolo, Estado estadoFinal) {
        this.estadoInicial = estadoInicial;
        this.estadoFinal = estadoFinal;
        this.simbolo = simbolo;
    }

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public Estado getEstadoFinal() {
        return estadoFinal;
    }

    public char getSimbolo() {
        return simbolo;
    }

    @Override
    public String toString() {
        return estadoInicial + " '" + simbolo + "' " + estadoFinal;
    }
    
}
