/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Usuario
 */
public class TransicionMacroestado {
    private MacroEstado estadoInicial;
    private MacroEstado estadoFinal;
    private char simbolo;

    public TransicionMacroestado() {
    }

    public TransicionMacroestado(MacroEstado estadoInicial, MacroEstado estadoFinal, char simbolo) {
        this.estadoInicial = estadoInicial;
        this.estadoFinal = estadoFinal;
        this.simbolo = simbolo;
    }

    public MacroEstado getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(MacroEstado estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public MacroEstado getEstadoFinal() {
        return estadoFinal;
    }

    public void setEstadoFinal(MacroEstado estadoFinal) {
        this.estadoFinal = estadoFinal;
    }

    public char getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(char simbolo) {
        this.simbolo = simbolo;
    }

    @Override
    public String toString() {
        return estadoInicial + " " + simbolo + " " + estadoFinal;
    }
    
    
}
