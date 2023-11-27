/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.VistaAFDCargarDatosPanel;
import Vista.VistaAFDFrame;
import Vista.VistaAFDPrincipalPanel;
import Vista.VistaAFDCargarFicheroPanel;
import Vista.VistaAFDComprobarCadenaPanel;
import Vista.VistaAFDMostrarResultadosPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
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
    private VistaAFDComprobarCadenaPanel vistaAFDComprobarCadenaPanel;

    public ControladorAFD() {
        vistaAFDFrame = new VistaAFDFrame();
        vistaAFDPrincipalPanel = new VistaAFDPrincipalPanel();
        vistaAFDCargarFicheroPanel = new VistaAFDCargarFicheroPanel();
        vistaAFDMostrarResultadosPanel = new VistaAFDMostrarResultadosPanel();
        vistaAFDCargarDatosPanel = new VistaAFDCargarDatosPanel();
        vistaAFDComprobarCadenaPanel = new VistaAFDComprobarCadenaPanel();

        addActionListener();

        this.vistaAFDFrame.setVisible(true);
        this.vistaAFDFrame.getContentPane().setLayout(new CardLayout());
        this.vistaAFDFrame.add(this.vistaAFDPrincipalPanel);
        this.vistaAFDFrame.add(vistaAFDCargarFicheroPanel);
        this.vistaAFDFrame.add(vistaAFDCargarDatosPanel);
        this.vistaAFDFrame.add(vistaAFDMostrarResultadosPanel);
        this.vistaAFDFrame.add(vistaAFDComprobarCadenaPanel);
        this.vistaAFDFrame.setLocationRelativeTo(null);

        this.vistaAFDPrincipalPanel.setVisible(true);
        this.vistaAFDCargarDatosPanel.setVisible(false);
        this.vistaAFDCargarFicheroPanel.setVisible(false);
        this.vistaAFDMostrarResultadosPanel.setVisible(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Cargar Fichero Txt":
                cargarPanel(vistaAFDCargarFicheroPanel);
                break;
            case "Cargar Datos":
                cargarPanel(vistaAFDCargarDatosPanel);
                break;
            case "CargarCFP":
                cargarPanel(vistaAFDComprobarCadenaPanel);
                break;
            case "Volver":
                cargarPanel(vistaAFDPrincipalPanel);
                break;
            case "Cargar y Continuar":
                break;
            case "FinalizarCDP":
                cargarPanel(vistaAFDComprobarCadenaPanel);
                break;
            case "ComprobarCCP":
                cargarPanel(vistaAFDMostrarResultadosPanel);
                break;
            case "AceptarMRP":
                cargarPanel(vistaAFDPrincipalPanel);
                break;
        }
    }

    public void cargarPanel(JPanel panel) {
        this.vistaAFDPrincipalPanel.setVisible(false);
        this.vistaAFDCargarDatosPanel.setVisible(false);
        this.vistaAFDCargarFicheroPanel.setVisible(false);
        this.vistaAFDMostrarResultadosPanel.setVisible(false);
        this.vistaAFDComprobarCadenaPanel.setVisible(false);

        panel.setVisible(true);
    }

    public void addActionListener() {
        this.vistaAFDPrincipalPanel.btnCargarDatos.addActionListener(this);
        this.vistaAFDPrincipalPanel.btnCargarFichero.addActionListener(this);
        this.vistaAFDCargarFicheroPanel.btnCargarFichero.addActionListener(this);
        this.vistaAFDCargarFicheroPanel.btnVolver.addActionListener(this);
        this.vistaAFDCargarDatosPanel.btnCargaryContinuar.addActionListener(this);
        this.vistaAFDCargarDatosPanel.btnVolver.addActionListener(this);
        this.vistaAFDCargarDatosPanel.btnFinalizar.addActionListener(this);
        this.vistaAFDComprobarCadenaPanel.btnComprobar.addActionListener(this);
        this.vistaAFDMostrarResultadosPanel.btnAceptar.addActionListener(this);
    }
}
