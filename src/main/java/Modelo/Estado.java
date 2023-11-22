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
    private boolean esInicial;
    private boolean esFinal;
    
    public Estado(String nombre, boolean esInicial, boolean esFinal){
        this.nombre = nombre;
        this.esInicial = esInicial;
        this.esFinal = esFinal;
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
     * @return the esInicial
     */
    public boolean isEsInicial() {
        return esInicial;
    }

    /**
     * @param esInicial the esInicial to set
     */
    public void setEsInicial(boolean esInicial) {
        this.esInicial = esInicial;
    }

    /**
     * @return the esFinal
     */
    public boolean isEsFinal() {
        return esFinal;
    }

    /**
     * @param esFinal the esFinal to set
     */
    public void setEsFinal(boolean esFinal) {
        this.esFinal = esFinal;
    }

}
