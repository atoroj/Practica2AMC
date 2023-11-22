/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Interfaces.IAutomataFinitoDeterminista;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class AFD implements IAutomataFinitoDeterminista {

    private int[] estadosFinales; //indica cuales son los estados Finales
    private ArrayList<TransicionAFD> transiciones; //indica la lista de transiciones del AFD

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
        Estado estado = null; //El estado inicial es el 0
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
        // Aquí debería comprobar el fichero (existencia, formato, transiciones, etc.)
        throw new IOException("Dummy load");
    }

    @Override
    public boolean esFinal(Estado estado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
