package Ejecutable;

import Controlador.ControladorPrincipal;
import Vista.VistaPrincipalFrame;

public class Practica2 {

    public static void main(String[] args) {
        VistaPrincipalFrame vistaPrincipal = new VistaPrincipalFrame();
        ControladorPrincipal controladorPrincipal = new ControladorPrincipal(vistaPrincipal);
        
        controladorPrincipal.iniciar();
        System.out.println("hola mundo");
    }
}
