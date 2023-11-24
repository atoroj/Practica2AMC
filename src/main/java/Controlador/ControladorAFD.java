/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.VistaAFDCargarDatosPanel;
import Vista.VistaAFDFrame;
import Vista.VistaAFDPrincipalPanel;
import Vista.VistaAFDCargarFicheroPanel;
import Vista.VistaAFDMostrarResultadosPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author Usuario
 */
public class ControladorAFD implements ActionListener {

    private VistaAFDFrame vistaAFDFrame;
    private VistaAFDPrincipalPanel vistaAFDPrincipalPanel;
    private VistaAFDCargarFicheroPanel vistaAFDCargarFicheroPanel;
    private VistaAFDCargarDatosPanel vistaAFDCargarDatosPanel;
    private VistaAFDMostrarResultadosPanel vistaAFDMostrarResultadosPanel;

    public ControladorAFD() {
        vistaAFDFrame = new VistaAFDFrame();
        vistaAFDPrincipalPanel = new VistaAFDPrincipalPanel();
        vistaAFDCargarFicheroPanel = new VistaAFDCargarFicheroPanel();
        vistaAFDMostrarResultadosPanel = new VistaAFDMostrarResultadosPanel();
        vistaAFDCargarDatosPanel = new VistaAFDCargarDatosPanel();

        this.vistaAFDPrincipalPanel.setSize(vistaAFDFrame.getSize());
        this.vistaAFDPrincipalPanel.setLocation(vistaAFDFrame.getLocation());

        addActionListener();

        this.vistaAFDFrame.add(this.vistaAFDPrincipalPanel, BorderLayout.CENTER);
        this.vistaAFDFrame.add(vistaAFDCargarFicheroPanel, BorderLayout.CENTER);
        this.vistaAFDFrame.add(vistaAFDCargarDatosPanel, BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Cargar Fichero Txt":
                cargarPanel(vistaAFDCargarFicheroPanel);
                break;
            case "Cargar Datos":

                break;
        }
    }

    public void iniciar() {
        this.vistaAFDFrame.setVisible(true);
        this.vistaAFDFrame.setLocationRelativeTo(null);
        this.vistaAFDFrame.setTitle("Vista Principal");
    }

    public void cargarPanel(JPanel panel) {
        vistaAFDFrame.removeAll();

        panel.setSize(vistaAFDFrame.getSize());
        panel.setLocation(vistaAFDFrame.getLocation());
        panel.setVisible(true);
    }

    public void addActionListener() {
        this.vistaAFDPrincipalPanel.btnCargarDatos.addActionListener(this);
        this.vistaAFDPrincipalPanel.btnCargarFichero.addActionListener(this);
    }
}
