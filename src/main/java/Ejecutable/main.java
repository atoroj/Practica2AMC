/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejecutable;

import Controlador.ControladorPrincipal;
import Vista.VistaPrincipal;

/**
 *
 * @author Usuario
 */
public class main {
    public static void main(String[] args){
        VistaPrincipal vistaPrincipal = new VistaPrincipal();
        ControladorPrincipal controladorPrincipal = new ControladorPrincipal(vistaPrincipal);
        
        controladorPrincipal.iniciar();
    }
}
