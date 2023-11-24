package Modelo;

import Interfaces.IAutomataFinitoNoDeterminista;
import java.io.IOException;
import java.util.ArrayList;

public class AFND implements IAutomataFinitoNoDeterminista {

    private ArrayList<Estado> estadosFinales; 
    private ArrayList<TransicionAFND> transiciones; 
    private ArrayList<TransicionLamba> transicionesLamba; 

    public AFND(ArrayList<Estado> estadosFinales, ArrayList<TransicionAFND> transiciones, ArrayList<TransicionLamba> transicionesLamba) {
        this.estadosFinales = estadosFinales;
        this.transiciones = transiciones;
        this.transicionesLamba = transicionesLamba;
    }

    public void agregarTransicion(Estado e1, char simbolo, Estado[] e2) {

    }

    public void agregarTransicionLamda(Estado e1, Estado[] e2) {

    }

    private ArrayList<Estado> transicion(Estado estado, char simbolo) {
        return null;
    }

    public ArrayList<Estado> transicion(ArrayList<Estado> macroestado, char simbolo) {
        return null;
    }

    public ArrayList<Estado> transicionLamda(Estado estado) {
        return null;
    }

    public boolean esFinal(ArrayList<Estado> macroestado) {
        return false;
    }

    private ArrayList<Estado> lamda_clausura(Estado[] macroestado) {
        return null;
    }

    @Override
    public boolean reconocer(String cadena) {
        char[] simbolo = cadena.toCharArray();
        Estado[] estado = null; 
        ArrayList<Estado> macroestado = lamda_clausura(estado);
        for (int i = 0; i < simbolo.length; i++) {
            macroestado = transicion(macroestado, simbolo[i]);
        }
        return esFinal(macroestado);
    }

    public static AFND pedir() {
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
