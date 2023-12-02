package Ejecutable;

import Controlador.ControladorPrincipal;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.UIManager;

public class Practica2 {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
            ControladorPrincipal controladorPrincipal = new ControladorPrincipal();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
