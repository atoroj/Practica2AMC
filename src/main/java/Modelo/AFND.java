/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Interfaces.IAutomataFinitoNoDeterminista;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class AFND implements IAutomataFinitoNoDeterminista {

    private Estado[] estadosFinales; //indica cuales son los estados Finales
    private ArrayList<TransicionAFND> transiciones; //indica la lista de transiciones del AFND
    private ArrayList<Transicionλ> transicionesλ; //indica la lista de transiciones λ del AFND

    public AFND() {

    }

    public void agregarTransicion(Estado e1, char simbolo, Estado[] e2) {

    }

    public void agregarTransicionλ(Estado e1, Estado[] e2) {

    }

    private Estado[] transicion(Estado estado, char simbolo) {
        return null;
    }

    public Estado[] transicion(Estado[] macroestado, char simbolo) {
        return null;
    }

    public Estado[] transicionλ(Estado estado) {
        return null;
    }

    public boolean esFinal(Estado[] macroestado) {
        return false;
    }

    private Estado[] λ_clausura(Estado[] macroestado) {
        return null;
    }

    @Override
    public boolean reconocer(String cadena) {
        char[] simbolo = cadena.toCharArray();
        Estado[] estado = null; //El estado inicial es el 0
        Estado[] macroestado = λ_clausura(estado);
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
        // Aquí debería comprobar el fichero (existencia, formato, transiciones, etc.)
        throw new IOException("Dummy load");
    }

    @Override
    public boolean esFinal(Estado estado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
