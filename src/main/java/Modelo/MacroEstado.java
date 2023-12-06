/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class MacroEstado {

    private String nombre;
    private ArrayList<Estado> estados;

    public MacroEstado() {
    }

    public MacroEstado(String nombre, ArrayList<Estado> estados) {
        this.nombre = nombre;
        this.estados = estados;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<Estado> estados) {
        this.estados = estados;
    }

}
