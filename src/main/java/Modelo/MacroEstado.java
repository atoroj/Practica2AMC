/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.Objects;

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

    public boolean esInicial() {
        for (Estado estado : estados) {
            if (estado.isNodoInicial()) {
                return true;
            }
        }
        return false;
    }

    public boolean esFinal() {
        for (Estado estado : estados) {
            if (estado.isNodoFinal()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object macroEstado) {
        if (this == macroEstado) {
            return true;
        }
        if (macroEstado == null) {
            return false;
        }
        if (getClass() != macroEstado.getClass()) {
            return false;
        }
        final MacroEstado other = (MacroEstado) macroEstado;

        if (this.nombre.equals(other.getNombre())) {
            return true;
        }
        
        ArrayList<String> estadosNombre = new ArrayList<>();
        ArrayList<String> estadosNombreOther = new ArrayList<>();

        for (Estado estado : estados) {
            estadosNombre.add(estado.getNombre());
        }
        
        for (Estado estado : other.getEstados()) {
            estadosNombreOther.add(estado.getNombre());
        }
        
        return estadosNombre.equals(estadosNombreOther);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.estados);
        return hash;
    }

}
