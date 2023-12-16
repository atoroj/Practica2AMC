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
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Paint;
import java.util.function.Function;
import javax.swing.JFrame;

/**
 *
 * @author Usuario
 */
public class ControladorAFD implements ActionListener {

    private AFD afd;

    private ArrayList<Estado> estados;
    private Estado inicial;
    private ArrayList<Estado> finales;
    private ArrayList<TransicionAFD> transiciones;

    private final VistaAFDFrame vistaAFDFrame;
    private final VistaPrincipalPanel vistaAFDPrincipalPanel;
    private final VistaCargarFicheroPanel vistaAFDCargarFicheroPanel;
    private final VistaCargarDatosPanel vistaAFDCargarDatosPanel;
    private final VistaAFDMostrarResultadosPanel vistaAFDMostrarResultadosPanel;
    private final VistaComprobarCadenaPanel vistaAFDComprobarCadenaPanel;

    DirectedSparseGraph<String, String> grafo;

    public ControladorAFD() {

        afd = new AFD();

        vistaAFDFrame = new VistaAFDFrame();
        vistaAFDPrincipalPanel = new VistaPrincipalPanel();
        vistaAFDCargarFicheroPanel = new VistaCargarFicheroPanel();
        vistaAFDMostrarResultadosPanel = new VistaAFDMostrarResultadosPanel();
        vistaAFDCargarDatosPanel = new VistaCargarDatosPanel();
        vistaAFDComprobarCadenaPanel = new VistaComprobarCadenaPanel();

        addActionListener();

        this.vistaAFDFrame.setVisible(true);
        this.vistaAFDFrame.getContentPane().setLayout(new CardLayout());
        this.vistaAFDFrame.add(this.vistaAFDPrincipalPanel);
        this.vistaAFDFrame.add(vistaAFDCargarFicheroPanel);
        this.vistaAFDFrame.add(vistaAFDCargarDatosPanel);
        this.vistaAFDFrame.add(vistaAFDMostrarResultadosPanel);
        this.vistaAFDFrame.add(vistaAFDComprobarCadenaPanel);
        this.vistaAFDFrame.setLocationRelativeTo(null);
        this.vistaAFDFrame.setTitle("Vista AFD Principal");

        this.vistaAFDPrincipalPanel.setVisible(true);
        this.vistaAFDCargarDatosPanel.setVisible(false);
        this.vistaAFDCargarFicheroPanel.setVisible(false);
        this.vistaAFDMostrarResultadosPanel.setVisible(false);

        grafo = new DirectedSparseGraph<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Cargar Fichero Txt":
                this.vistaAFDFrame.setTitle("Cargar Fichero");
                cargarPanel(vistaAFDCargarFicheroPanel);
                break;
            case "Cargar Datos":
                inicializarParametros();
                this.vistaAFDFrame.setTitle("Cargar Datos");
                cargarPanel(vistaAFDCargarDatosPanel);
                break;
            case "CargarCFP": {
                inicializarParametros();
                boolean[] existeFichero = new boolean[2];
                try {
                    existeFichero = afd.load("src\\main\\resources\\" + vistaAFDCargarFicheroPanel.txtNombreFichero.getText());
                    estados = afd.getEstados();
                    transiciones = afd.getTransiciones();
                    System.out.println("Estados: " + estados);
                    System.out.println("Transiciones: " + transiciones);
                } catch (Exception ex) {
                    ex.getMessage();
                }

                if (existeFichero[0] && existeFichero[1]) {
                    this.vistaAFDFrame.setTitle("Comprobar Cadena");
                    cargarPanel(vistaAFDComprobarCadenaPanel);
                } else if (existeFichero[0] && !existeFichero[1]) {
                    vistaAFDCargarFicheroPanel.txtNombreFichero.setText("");
                    VistaDialog vistaDialog = new VistaDialog();
                    vistaDialog.mensaje("ERROR: El fichero no es de tipo AFD");
                } else if (!existeFichero[0]) {
                    vistaAFDCargarFicheroPanel.txtNombreFichero.setText("");
                    VistaDialog vistaDialog = new VistaDialog();
                    vistaDialog.mensaje("ERROR: No se ha encontrado el fichero, por favor introduzca de nuevo el fichero");
                }
            }
            break;

            case "Volver":
                vistaAFDCargarDatosPanel.chbxNodoInicial.setVisible(true);
                this.vistaAFDFrame.setTitle("Vista AFD Principal");
                cargarPanel(vistaAFDPrincipalPanel);
                break;
            case "Cargar y Continuar":
                introducirTransiciones(vistaAFDCargarDatosPanel.jTextField1,
                        vistaAFDCargarDatosPanel.jTextField2,
                        vistaAFDCargarDatosPanel.jTextField3,
                        vistaAFDCargarDatosPanel.chbxNodoInicial,
                        vistaAFDCargarDatosPanel.chbxNodoFinal);
                break;
            case "FinalizarCDP": {
                introducirTransiciones(vistaAFDCargarDatosPanel.jTextField1,
                        vistaAFDCargarDatosPanel.jTextField2,
                        vistaAFDCargarDatosPanel.jTextField3,
                        vistaAFDCargarDatosPanel.chbxNodoInicial,
                        vistaAFDCargarDatosPanel.chbxNodoFinal);

                System.out.println("Estados " + estados);
                System.out.println("Inicial " + inicial);
                System.out.println("Finales " + finales);
                System.out.println("transiciones " + transiciones);

                vistaAFDCargarDatosPanel.chbxNodoInicial.setVisible(true);

                try {
                    afd.load(afd.write(estados, inicial, finales, transiciones));
                } catch (Exception ex) {
                    Logger.getLogger(ControladorAFD.class.getName()).log(Level.SEVERE, null, ex);
                }

                this.vistaAFDFrame.setTitle("Comprobar Cadena");
                cargarPanel(vistaAFDComprobarCadenaPanel);
            }
            break;

            case "ComprobarCCP":
                String cadenaEstados = "",
                 cadenaInicial = "",
                 cadenaFinal = "",
                 cadenaTransiciones = "";
                vistaAFDMostrarResultadosPanel.lblTipoValor.setText("AFD");
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
                vistaAFDMostrarResultadosPanel.lblEstadosValor.setText(cadenaEstados);
                vistaAFDMostrarResultadosPanel.lblInicialValor.setText(cadenaInicial);
                vistaAFDMostrarResultadosPanel.lblFinalesValor.setText(cadenaFinal);
                vistaAFDMostrarResultadosPanel.txtATransiciones.setText(cadenaTransiciones);
                vistaAFDMostrarResultadosPanel.txtATransiciones.setEditable(false);

                if (afd.reconocer(vistaAFDComprobarCadenaPanel.txtComprobarCadena.getText())) {
                    vistaAFDMostrarResultadosPanel.lblResultadoValor.setText("ACEPTADO");
                } else {
                    vistaAFDMostrarResultadosPanel.lblResultadoValor.setText("RECHAZADO");
                }
                this.vistaAFDFrame.setTitle("Mostrar Resultados");
                cargarPanel(vistaAFDMostrarResultadosPanel);
                break;

            case "MostrarGrafica":
                this.vistaAFDFrame.setTitle("Mostrar gráfica AFD");

                ArrayList<String> macroEstadosFinales = new ArrayList<>();

                for (Estado estado : afd.getEstados()) {
                    System.out.println("ESTADO " + estado.getNombre());
                    grafo.addVertex(estado.getNombre());
                    if (estado.isNodoFinal()) {
                        macroEstadosFinales.add(estado.getNombre());
                    }
                }

                for (TransicionAFD transicion : afd.getTransiciones()) {
                    System.out.println("TRANSICIONES " + transicion);
                    String estadoInicial = transicion.getEstadoInicial().getNombre();
                    String estadoFinal = transicion.getEstadoFinal().getNombre();
                    grafo.addEdge(estadoInicial + " '" + String.valueOf(transicion.getSimbolo()) + "' " + estadoFinal, transicion.getEstadoInicial().getNombre(), transicion.getEstadoFinal().getNombre(), EdgeType.DIRECTED);
                }

                VisualizationViewer<String, String> vv = new VisualizationViewer<>(new CircleLayout(grafo));

                vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
                vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

                Function<String, Paint> vertexPaint = vertex -> {
                    // Customize the vertex color based on your logic
                    if (vertex.equals("q0")) {
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
                this.vistaAFDFrame.setTitle("Vista AFD Principal");
                cargarPanel(vistaAFDPrincipalPanel);
                break;
        }

    }

    public void inicializarParametros() {
        estados = new ArrayList<>();
        inicial = new Estado();
        finales = new ArrayList<>();
        transiciones = new ArrayList<>();
        afd = new AFD();
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
        this.vistaAFDMostrarResultadosPanel.btnMostarGrafica.addActionListener(this);
    }
}
