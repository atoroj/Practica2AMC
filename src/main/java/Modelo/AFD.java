package Modelo;

import Interfaces.IAutomataFinitoDeterminista;
import java.io.IOException;
import java.util.ArrayList;

public class AFD implements IAutomataFinitoDeterminista {

    private int[] estadosFinales; 
    private ArrayList<TransicionAFD> transiciones; 

    public AFD() {

    }

    public void agregarTransicion(Estado e1, char simbolo, Estado e2) {

    }

    public Estado transicion(Estado estado, char simbolo) {
        return null;
    }

    @Override
    public boolean reconocer(String cadena) {
        char[] simbolo = cadena.toCharArray();
        Estado estado = null; 
        for (int i = 0; i < simbolo.length; i++) {
            estado = transicion(estado, simbolo[i]);
        }
        return esFinal(estado);
    }

    public static AFD pedir() {
        return null;
    }

    @Override
    public void load(String filePath) throws Exception {
        throw new IOException("Dummy load");
    }

    @Override
    public boolean esFinal(Estado estado) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
