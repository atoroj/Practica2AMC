/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.VistaPrincipalFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Usuario
 */
public class ControladorPrincipal implements ActionListener {

    private ControladorAFD controladorAFD;
    private ControladorAFND controladorAFND;

    private VistaPrincipalFrame vistaPrincipal;

    public ControladorPrincipal() {
        this.vistaPrincipal = new VistaPrincipalFrame();
        
        addActionListener();
        
        this.vistaPrincipal.setVisible(true);
        this.vistaPrincipal.setLocationRelativeTo(null);
        this.vistaPrincipal.setTitle("Vista Principal");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "AFD":
                this.controladorAFD = new ControladorAFD();
                break;
            case "AFND":
                this.controladorAFND = new ControladorAFND();
                break;
            default:
                throw new AssertionError();
        }

    }

    public void addActionListener() {
        this.vistaPrincipal.btnAFD.addActionListener(this);
        this.vistaPrincipal.btnAFND.addActionListener(this);
    }
}
