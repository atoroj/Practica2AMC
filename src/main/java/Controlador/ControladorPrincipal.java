/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.VistaAFDFrame;
import Vista.VistaAFNDFrame;
import Vista.VistaPrincipalFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Usuario
 */
public class ControladorPrincipal implements ActionListener {

    private ControladorAFD controladorAFD;
    private VistaPrincipalFrame vistaPrincipal;
    private VistaAFDFrame vistaAFD;
    private VistaAFNDFrame vistaAFND;

    public ControladorPrincipal(VistaPrincipalFrame view) {
        this.vistaPrincipal = view;

        this.vistaPrincipal.btnAFD.addActionListener(this);
        this.vistaPrincipal.btnAFND.addActionListener(this);
    }

    public void iniciar(){
        this.vistaPrincipal.setVisible(true);
        this.vistaPrincipal.setLocationRelativeTo(null);
        this.vistaPrincipal.setTitle("Vista Principal");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "AFD":
                
                break;
            case "AFND":

                break;
            default:
                throw new AssertionError();
        }

    }

}
