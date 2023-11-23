/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Usuario
 */
public class Estado {

    private String nombre;
    private boolean inicial;
    private boolean nodoFinal;
    
    public Estado(String nombre, boolean esInicial, boolean esFinal){
        this.nombre = nombre;
        this.inicial = esInicial;
        this.nodoFinal = esFinal;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the inicial
     */
    public boolean isInicial() {
        return inicial;
    }

    /**
     * @param esInicial the inicial to set
     */
    public void setInicial(boolean esInicial) {
        this.inicial = esInicial;
    }

    /**
     * @return the nodoFinal
     */
    public boolean isNodoFinal() {
        return nodoFinal;
    }

    /**
     * @param nodoFinal the nodoFinal to set
     */
    public void setNodoFinal(boolean nodoFinal) {
        this.nodoFinal = nodoFinal;
    }

}
