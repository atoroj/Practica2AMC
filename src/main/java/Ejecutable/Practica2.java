package Ejecutable;

import Controlador.ControladorPrincipal;
import Vista.VistaPrincipal;

public class Practica2 {

    public static void main(String[] args) {
        VistaPrincipal vistaPrincipal = new VistaPrincipal();
        ControladorPrincipal controladorPrincipal = new ControladorPrincipal(vistaPrincipal);
        
        controladorPrincipal.iniciar();
        System.out.println("hola mundo");
    }
}
