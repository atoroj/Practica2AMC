package Controlador;

import Modelo.AFD;
import Modelo.Estado;
import Modelo.TransicionAFD;
import Vista.VistaCargarDatosPanel;
import Vista.VistaAFDFrame;
import Vista.VistaPrincipalPanel;
import Vista.VistaCargarFicheroPanel;
import Vista.VistaComprobarCadenaPanel;
import Vista.VistaAFDMostrarResultadosPanel;
import Vista.VistaDialog;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Usuario
 */
public class ControladorAFND implements ActionListener {

    private AFD afd;

    private ArrayList<Estado> estados;
    private Estado inicial;
    private ArrayList<Estado> finales;
    private ArrayList<TransicionAFD> transiciones;

    private VistaAFDFrame vistaAFNDFrame;
    private VistaPrincipalPanel vistaAFNDPrincipalPanel;
    private VistaCargarFicheroPanel vistaAFNDCargarFicheroPanel;
    private VistaCargarDatosPanel vistaAFNDCargarDatosPanel;
    private VistaAFDMostrarResultadosPanel vistaAFNDMostrarResultadosPanel;
    private VistaComprobarCadenaPanel vistaAFNDComprobarCadenaPanel;

    public ControladorAFND() {

        afd = new AFD();

        vistaAFNDFrame = new VistaAFDFrame();
        vistaAFNDPrincipalPanel = new VistaPrincipalPanel();
        vistaAFNDCargarFicheroPanel = new VistaCargarFicheroPanel();
        vistaAFNDMostrarResultadosPanel = new VistaAFDMostrarResultadosPanel();
        vistaAFNDCargarDatosPanel = new VistaCargarDatosPanel();
        vistaAFNDComprobarCadenaPanel = new VistaComprobarCadenaPanel();

        addActionListener();

        this.vistaAFNDFrame.setVisible(true);
        this.vistaAFNDFrame.getContentPane().setLayout(new CardLayout());
        this.vistaAFNDFrame.add(this.vistaAFNDPrincipalPanel);
        this.vistaAFNDFrame.add(vistaAFNDCargarFicheroPanel);
        this.vistaAFNDFrame.add(vistaAFNDCargarDatosPanel);
        this.vistaAFNDFrame.add(vistaAFNDMostrarResultadosPanel);
        this.vistaAFNDFrame.add(vistaAFNDComprobarCadenaPanel);
        this.vistaAFNDFrame.setLocationRelativeTo(null);
        this.vistaAFNDFrame.setTitle("Vista AFD Principal");

        this.vistaAFNDPrincipalPanel.setVisible(true);
        this.vistaAFNDCargarDatosPanel.setVisible(false);
        this.vistaAFNDCargarFicheroPanel.setVisible(false);
        this.vistaAFNDMostrarResultadosPanel.setVisible(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Cargar Fichero Txt":
                this.vistaAFNDFrame.setTitle("Cargar Fichero");
                cargarPanel(vistaAFNDCargarFicheroPanel);
                break;
            case "Cargar Datos":
                inicializarParametros();
                this.vistaAFNDFrame.setTitle("Cargar Datos");
                cargarPanel(vistaAFNDCargarDatosPanel);
                break;
            case "CargarCFP": {
                try {
                    afd.load("src\\main\\resources\\" + vistaAFNDCargarFicheroPanel.txtNombreFichero.getText());
                    System.out.println("Estados: " + afd.getEstados());
                    System.out.println("Transiciones: " + afd.getTransiciones());
                } catch (Exception ex) {
                    ex.getMessage();
                }

                this.vistaAFNDFrame.setTitle("Comprobar Cadena");
                cargarPanel(vistaAFNDComprobarCadenaPanel);
            }
            break;

            case "Volver":
                vistaAFNDCargarDatosPanel.chbxNodoInicial.setVisible(true);
                this.vistaAFNDFrame.setTitle("Vista AFD Principal");
                cargarPanel(vistaAFNDPrincipalPanel);
                break;
            case "Cargar y Continuar":
                introducirTransiciones(vistaAFNDCargarDatosPanel.jTextField1,
                        vistaAFNDCargarDatosPanel.jTextField2,
                        vistaAFNDCargarDatosPanel.jTextField3,
                        vistaAFNDCargarDatosPanel.chbxNodoInicial,
                        vistaAFNDCargarDatosPanel.chbxNodoFinal);
                break;
            case "FinalizarCDP": {
                introducirTransiciones(vistaAFNDCargarDatosPanel.jTextField1,
                        vistaAFNDCargarDatosPanel.jTextField2,
                        vistaAFNDCargarDatosPanel.jTextField3,
                        vistaAFNDCargarDatosPanel.chbxNodoInicial,
                        vistaAFNDCargarDatosPanel.chbxNodoFinal);

                System.out.println("Estados " + estados);
                System.out.println("Inicial " + inicial);
                System.out.println("Finales " + finales);
                System.out.println("transiciones " + transiciones);

                vistaAFNDCargarDatosPanel.chbxNodoInicial.setVisible(true);

                try {
                    afd.load(afd.write("prueba", estados, inicial, finales, transiciones));
                } catch (Exception ex) {
                    Logger.getLogger(ControladorAFD.class.getName()).log(Level.SEVERE, null, ex);
                }

                this.vistaAFNDFrame.setTitle("Comprobar Cadena");
                cargarPanel(vistaAFNDComprobarCadenaPanel);
            }
            break;

            case "ComprobarCCP":
                String cadenaEstados = "",
                 cadenaInicial = "",
                 cadenaFinal = "",
                 cadenaTransiciones = "";
                vistaAFNDMostrarResultadosPanel.lblTipoValor.setText("AFD");
                for (Estado estado : afd.getEstados()) {
                    cadenaEstados += estado.getNombre() + " ";
                    if (estado.isNodoInicial()) {
                        cadenaInicial = estado.getNombre();
                    }
                    if (estado.isNodoFinal()) {
                        cadenaFinal += estado.getNombre() + " ";
                    }
                }
                for (TransicionAFD transicion : afd.getTransiciones()) {
                    cadenaTransiciones += transicion + "\n";
                }
                vistaAFNDMostrarResultadosPanel.lblEstadosValor.setText(cadenaEstados);
                vistaAFNDMostrarResultadosPanel.lblInicialValor.setText(cadenaInicial);
                vistaAFNDMostrarResultadosPanel.lblFinalesValor.setText(cadenaFinal);
                vistaAFNDMostrarResultadosPanel.txtATransiciones.setText(cadenaTransiciones);
                vistaAFNDMostrarResultadosPanel.txtATransiciones.setEditable(false);

                if (afd.reconocer(vistaAFNDComprobarCadenaPanel.txtComprobarCadena.getText())) {
                    vistaAFNDMostrarResultadosPanel.lblResultadoValor.setText("EXISTE");
                } else {
                    vistaAFNDMostrarResultadosPanel.lblResultadoValor.setText("NO EXISTE");
                }
                this.vistaAFNDFrame.setTitle("Mostrar Resultados");
                cargarPanel(vistaAFNDMostrarResultadosPanel);
                break;
            case "AceptarMRP":
                this.vistaAFNDFrame.setTitle("Vista AFD Principal");
                cargarPanel(vistaAFNDPrincipalPanel);
                break;
        }

    }

    public void inicializarParametros() {
        estados = new ArrayList<>();
        inicial = new Estado();
        finales = new ArrayList<>();
        transiciones = new ArrayList<>();
    }

    //TO DO: COMPROBAR QUE NO SE PUEDA FINALIZAR EL PROCESO SIN EXISTIR UN NODO INICIAL Y UN NODO FINAL
    public void introducirTransiciones(JTextField e1, JTextField simbolo, JTextField e2, JCheckBox chbxInicial, JCheckBox chbxFinal) {

        boolean existeE1 = false, existeE2 = false;

        for (TransicionAFD transicion : transiciones) {
            if (transicion.getEstadoInicial().getNombre().equals(e1.getText()) && transicion.getSimbolo() == simbolo.getText().charAt(0)) {
                e1.setText("");
                e2.setText("");
                simbolo.setText("");
                VistaDialog vistaDialog = new VistaDialog();
                vistaDialog.mensaje("Ya existe una transicion con ese estado inicial y simbolo, por favor introduce otra transición");
                return;
            }
        }

        if (!estados.isEmpty()) {
            for (Estado estado : estados) {
                if (estado.getNombre().equals(e1.getText())) {
                    existeE1 = true;
                }
                if (estado.getNombre().equals(e2.getText())) {
                    existeE2 = true;
                }
            }
        }

        if (!existeE1) {
            estados.add(new Estado(e1.getText()));
        }
        if (!existeE2 && !e1.getText().equals(e2.getText())) {
            estados.add(new Estado(e2.getText()));
        }

        if (chbxInicial.isSelected()) {
            inicial = new Estado(e1.getText());
            chbxInicial.setVisible(false);
            chbxInicial.setSelected(false);
        }
        if (chbxFinal.isSelected()) {
            finales.add(new Estado(e2.getText()));
            chbxFinal.setSelected(false);
        }
        transiciones.add(new TransicionAFD(new Estado(e1.getText()), simbolo.getText().charAt(0), new Estado(e2.getText())));

        e1.setText("");
        e2.setText("");
        simbolo.setText("");
    }

    public void cargarPanel(JPanel panel) {
        this.vistaAFNDPrincipalPanel.setVisible(false);
        this.vistaAFNDCargarDatosPanel.setVisible(false);
        this.vistaAFNDCargarFicheroPanel.setVisible(false);
        this.vistaAFNDMostrarResultadosPanel.setVisible(false);
        this.vistaAFNDComprobarCadenaPanel.setVisible(false);

        panel.setVisible(true);
    }

    public void addActionListener() {
        this.vistaAFNDPrincipalPanel.btnCargarDatos.addActionListener(this);
        this.vistaAFNDPrincipalPanel.btnCargarFichero.addActionListener(this);
        this.vistaAFNDCargarFicheroPanel.btnCargarFichero.addActionListener(this);
        this.vistaAFNDCargarFicheroPanel.btnVolver.addActionListener(this);
        this.vistaAFNDCargarDatosPanel.btnCargaryContinuar.addActionListener(this);
        this.vistaAFNDCargarDatosPanel.btnVolver.addActionListener(this);
        this.vistaAFNDCargarDatosPanel.btnFinalizar.addActionListener(this);
        this.vistaAFNDComprobarCadenaPanel.btnComprobar.addActionListener(this);
        this.vistaAFNDMostrarResultadosPanel.btnAceptar.addActionListener(this);
    }
}
