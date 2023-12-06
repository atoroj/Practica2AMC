package Controlador;

import Modelo.AFD;
import Modelo.AFND;
import Modelo.Estado;
import Modelo.TransicionAFD;
import Modelo.TransicionAFND;
import Modelo.TransicionLambda;
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

    private AFND afnd;

    private ArrayList<Estado> estados;
    private Estado inicial;
    private ArrayList<Estado> finales;
    private ArrayList<TransicionAFND> transiciones;
    private ArrayList<TransicionLambda> transicionesLambda;

    private VistaAFDFrame vistaAFNDFrame;
    private VistaPrincipalPanel vistaAFNDPrincipalPanel;
    private VistaCargarFicheroPanel vistaAFNDCargarFicheroPanel;
    private VistaCargarDatosPanel vistaAFNDCargarDatosPanel;
    private VistaAFDMostrarResultadosPanel vistaAFNDMostrarResultadosPanel;
    private VistaComprobarCadenaPanel vistaAFNDComprobarCadenaPanel;

    public ControladorAFND() {

        afnd = new AFND();

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
        this.vistaAFNDFrame.setTitle("Vista AFND Principal");

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
                    afnd.load("src\\main\\resources\\" + vistaAFNDCargarFicheroPanel.txtNombreFichero.getText());
                    System.out.println("Estados: " + afnd.getEstados());
                    System.out.println("Transiciones: " + afnd.getTransiciones());
                    System.out.println("Transiciones Lambda: " + afnd.getTransicionesLamba());
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
                System.out.println("transicionesLambda " + transicionesLambda);

                vistaAFNDCargarDatosPanel.chbxNodoInicial.setVisible(true);

                try {
                    afnd.write("pruebaAFND", estados, inicial, finales, transiciones, transicionesLambda);
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
                for (Estado estado : afnd.getEstados()) {
                    cadenaEstados += estado.getNombre() + " ";
                    if (estado.isNodoInicial()) {
                        cadenaInicial = estado.getNombre();
                    }
                    if (estado.isNodoFinal()) {
                        cadenaFinal += estado.getNombre() + " ";
                    }
                }
                for (TransicionAFND transicion : afnd.getTransiciones()) {
                    cadenaTransiciones += transicion + "\n";
                }
                vistaAFNDMostrarResultadosPanel.lblEstadosValor.setText(cadenaEstados);
                vistaAFNDMostrarResultadosPanel.lblInicialValor.setText(cadenaInicial);
                vistaAFNDMostrarResultadosPanel.lblFinalesValor.setText(cadenaFinal);
                vistaAFNDMostrarResultadosPanel.txtATransiciones.setText(cadenaTransiciones);
                vistaAFNDMostrarResultadosPanel.txtATransiciones.setEditable(false);

                if (afnd.reconocer(vistaAFNDComprobarCadenaPanel.txtComprobarCadena.getText())) {
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
        transicionesLambda = new ArrayList<>();
    }

    public void introducirTransiciones(JTextField e1, JTextField simbolo, JTextField e2, JCheckBox chbxInicial, JCheckBox chbxFinal) {

        boolean existeE1 = false, existeE2 = false, transicionExiste = false, transicionLambdaExiste = false;

        if ("l".equals(simbolo.getText())) {
            for (TransicionLambda transicionLambda : transicionesLambda) {
                if (transicionLambda.getEstadoInicial().getNombre().equals(e1.getText())) {
                    transicionLambda.getEstadosFinales().add(new Estado(e2.getText()));
                    transicionLambdaExiste = true;
                }
            }
        } else {
            for (TransicionAFND transicion : transiciones) {
                if (transicion.getEstadoInicial().getNombre().equals(e1.getText()) && transicion.getSimbolo() == simbolo.getText().charAt(0)) {
                    transicion.getEstadosFinales().add(new Estado(e2.getText()));
                    transicionExiste = true;
                }
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

        if (!transicionExiste && !simbolo.getText().equals("l")) {
            ArrayList<Estado> estadosFinales = new ArrayList<>();
            estadosFinales.add(new Estado(e2.getText()));
            transiciones.add(new TransicionAFND(new Estado(e1.getText()), simbolo.getText().charAt(0), estadosFinales));
        }
        
        if (!transicionLambdaExiste && simbolo.getText().equals("l")) {
            ArrayList<Estado> estadosFinales = new ArrayList<>();
            estadosFinales.add(new Estado(e2.getText()));
            transicionesLambda.add(new TransicionLambda(new Estado(e1.getText()), estadosFinales));
            System.out.println("RECOGE LAMBDA");
        }

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
