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
    private boolean nodoInicial;
    private boolean nodoFinal;
    
    public Estado(String nombre, boolean esInicial, boolean esFinal){
        this.nombre = nombre;
        this.nodoInicial = esInicial;
        this.nodoFinal = esFinal;
    }
    public Estado(String nombre){
        this.nombre = nombre;
    }
    public Estado(){
        
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
     * @return the nodoInicial
     */
    public boolean isNodoInicial() {
        return nodoInicial;
    }

    /**
     * @param esInicial the nodoInicial to set
     */
    public void setNodoInicial(boolean esInicial) {
        this.nodoInicial = esInicial;
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

    @Override
    public String toString() {
        return nombre;
    }

}
