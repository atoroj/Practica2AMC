package Controlador;

import Modelo.AFND;
import Modelo.Estado;
import Modelo.MacroEstado;
import Modelo.TransicionAFND;
import Modelo.TransicionLambda;
import Modelo.TransicionMacroestado;
import Vista.VistaCargarDatosPanel;
import Vista.VistaAFDFrame;
import Vista.VistaPrincipalPanel;
import Vista.VistaCargarFicheroPanel;
import Vista.VistaComprobarCadenaPanel;
import Vista.VistaAFNDMostrarResultadosPanel;
import Vista.VistaDialog;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
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

    private final VistaAFDFrame vistaAFNDFrame;
    private final VistaPrincipalPanel vistaAFNDPrincipalPanel;
    private final VistaCargarFicheroPanel vistaAFNDCargarFicheroPanel;
    private final VistaCargarDatosPanel vistaAFNDCargarDatosPanel;
    private final VistaAFNDMostrarResultadosPanel vistaAFNDMostrarResultadosPanel;
    private final VistaComprobarCadenaPanel vistaAFNDComprobarCadenaPanel;

    DirectedSparseGraph<String, String> grafo;

    public ControladorAFND() {

        afnd = new AFND();

        grafo = new DirectedSparseGraph<>();

        vistaAFNDFrame = new VistaAFDFrame();
        vistaAFNDPrincipalPanel = new VistaPrincipalPanel();
        vistaAFNDCargarFicheroPanel = new VistaCargarFicheroPanel();
        vistaAFNDMostrarResultadosPanel = new VistaAFNDMostrarResultadosPanel();
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
                inicializarParametros();
                boolean[] existeFichero = new boolean[2];
                try {
                    existeFichero = afnd.load("src\\main\\resources\\" + vistaAFNDCargarFicheroPanel.txtNombreFichero.getText());
                    System.out.println("Estados: " + afnd.getEstados());
                    System.out.println("Transiciones: " + afnd.getTransiciones());
                    System.out.println("Transiciones Lambda: " + afnd.getTransicionesLambda());
                } catch (Exception ex) {
                    ex.getMessage();
                }
                if (existeFichero[0] && existeFichero[1]) {
                    this.vistaAFNDFrame.setTitle("Comprobar Cadena");
                    cargarPanel(vistaAFNDComprobarCadenaPanel);
                } else if (existeFichero[0] && !existeFichero[1]) {
                    vistaAFNDCargarFicheroPanel.txtNombreFichero.setText("");
                    VistaDialog vistaDialog = new VistaDialog();
                    vistaDialog.mensaje("ERROR: El fichero no es de tipo AFND");
                } else if (!existeFichero[0]) {
                    vistaAFNDCargarFicheroPanel.txtNombreFichero.setText("");
                    VistaDialog vistaDialog = new VistaDialog();
                    vistaDialog.mensaje("ERROR: No se ha encontrado el fichero, por favor introduzca de nuevo el fichero");
                }
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
                    afnd.write(estados, inicial, finales, transiciones, transicionesLambda);
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
                 cadenaTransiciones = "",
                 cadenaTransicionesLambda = "";
                vistaAFNDMostrarResultadosPanel.lblTipoValor.setText("AFND");
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
                for (TransicionLambda transicionLambda : afnd.getTransicionesLambda()) {
                    cadenaTransicionesLambda += transicionLambda + "\n";
                }
                vistaAFNDMostrarResultadosPanel.lblEstadosValor.setText(cadenaEstados);
                vistaAFNDMostrarResultadosPanel.lblInicialValor.setText(cadenaInicial);
                vistaAFNDMostrarResultadosPanel.lblFinalesValor.setText(cadenaFinal);
                vistaAFNDMostrarResultadosPanel.txtATransiciones.setText(cadenaTransiciones);
                vistaAFNDMostrarResultadosPanel.txtATransiciones.setEditable(false);
                vistaAFNDMostrarResultadosPanel.txtATransicionesLambda.setText(cadenaTransicionesLambda);
                vistaAFNDMostrarResultadosPanel.txtATransicionesLambda.setEditable(false);

                if (afnd.reconocer(vistaAFNDComprobarCadenaPanel.txtComprobarCadena.getText())) {
                    vistaAFNDMostrarResultadosPanel.lblResultadoValor.setText("ACEPTADO");
                } else {
                    vistaAFNDMostrarResultadosPanel.lblResultadoValor.setText("RECHAZADO");
                }
                this.vistaAFNDFrame.setTitle("Mostrar Resultados");
                cargarPanel(vistaAFNDMostrarResultadosPanel);
                break;
            case "MostrarGrafica":
                this.vistaAFNDFrame.setTitle("Mostrar gráfica AFD");

                ArrayList<String> macroEstadosFinales = new ArrayList<>();

                for (MacroEstado estado : afnd.getMacroestados()) {
                    System.out.println("ESTADO " + estado.getNombre());
                    grafo.addVertex(estado.getNombre());
                    if (estado.esFinal()) {
                        macroEstadosFinales.add(estado.getNombre());
                    }
                }

                for (TransicionMacroestado transicion : afnd.getTransicionesMacroestados()) {
                    System.out.println("TRANSICIONES " + transicion);
                    char[] estadoInicial = transicion.getEstadoInicial().getNombre().toCharArray();
                    char[] estadoFinal = transicion.getEstadoFinal().getNombre().toCharArray();
                    grafo.addEdge(estadoInicial[1] + " '" + String.valueOf(transicion.getSimbolo()) + "' " + estadoFinal[1], transicion.getEstadoInicial().getNombre(), transicion.getEstadoFinal().getNombre(), EdgeType.DIRECTED);
                }

                VisualizationViewer<String, String> vv = new VisualizationViewer<>(new CircleLayout(grafo));

                vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
                vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

                Function<String, Paint> vertexPaint = vertex -> {
                    // Customize the vertex color based on your logic
                    if (vertex.equals("Q0")) {
                        return Color.GREEN;
                    } else if (macroEstadosFinales.contains(vertex)) {
                        return Color.RED;
                    } else {
                        return Color.YELLOW;
                    }
                };

                vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint::apply);

                JFrame frame = new JFrame("Mostrar Grafica AFND");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(vv, BorderLayout.CENTER);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

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
        afnd = new AFND();
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

        if (panel.getClass() == vistaAFNDMostrarResultadosPanel.getClass()) {
            vistaAFNDFrame.setSize(459, 390);
        } else {
            vistaAFNDFrame.setSize(400, 300);
        }

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
        this.vistaAFNDMostrarResultadosPanel.btnMostarGrafica.addActionListener(this);
    }
}
