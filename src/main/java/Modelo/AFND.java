package Modelo;

import Interfaces.IAutomataFinitoNoDeterminista;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class AFND implements IAutomataFinitoNoDeterminista {

    private ArrayList<Estado> estados;
    private ArrayList<TransicionAFND> transiciones;
    private ArrayList<TransicionLambda> transicionesLamba;

    public AFND(ArrayList<Estado> estados, ArrayList<TransicionAFND> transiciones, ArrayList<TransicionLambda> transicionesLamba) {
        this.estados = estados;
        this.transiciones = transiciones;
        this.transicionesLamba = transicionesLamba;
    }

    public void agregarTransicion(Estado e1, char simbolo, Estado[] e2) {

    }

    public void agregarTransicionLamda(Estado e1, Estado[] e2) {

    }

    private ArrayList<Estado> transicion(Estado estado, char simbolo) {
        return null;
    }

    public ArrayList<Estado> transicion(ArrayList<Estado> macroestado, char simbolo) {
        return null;
    }

    public ArrayList<Estado> transicionLamda(Estado estado) {
        return null;
    }

    public boolean esFinal(ArrayList<Estado> macroestado) {
        return false;
    }

    private ArrayList<Estado> lamda_clausura(Estado[] macroestado) {
        return null;
    }

    @Override
    public boolean reconocer(String cadena) {
        char[] simbolo = cadena.toCharArray();
        Estado[] estado = null;
        ArrayList<Estado> macroestado = lamda_clausura(estado);
        for (int i = 0; i < simbolo.length; i++) {
            macroestado = transicion(macroestado, simbolo[i]);
        }
        return esFinal(macroestado);
    }

    public static AFND pedir() {
        return null;
    }

    @Override
    public void load(String filePath) throws Exception {
        File fichero = new File(filePath);
        if (fichero.exists()) {
            try {
                this.estados.clear();
                this.transiciones.clear();
                BufferedReader reader = new BufferedReader(new FileReader(fichero));
                String line;
                reader.readLine(); //TIPO:
                line = reader.readLine(); //ESTADOS: 
                String[] parts = line.split(" ");
                for (int i = 1; i < parts.length; i++) {
                    this.estados.add(new Estado(parts[i]));
                }
                line = reader.readLine(); //INICIAL:
                parts = line.split(" ");
                for (int i = 0; i < this.estados.size(); i++) {
                    if (this.estados.get(i).getNombre().equals(parts[1])) {
                        this.estados.get(i).setNodoInicial(true);
                        break;
                    }
                }
                line = reader.readLine(); //FINALES:
                parts = line.split(" ");
                for (int i = 1; i < parts.length; i++) {
                    for (int j = 0; j < this.estados.size(); j++) {
                        if (this.estados.get(j).getNombre().equals(parts[i])) {
                            this.estados.get(j).setNodoFinal(true);
                            break;
                        }
                    }
                }

                reader.readLine(); //TRANSICIONES:

                while ((line = reader.readLine()) != null) {
                    if (line.contains("TRANSICIONES LAMBDA:")) {
                        break;
                    }
                    parts = line.split(" ");
                    if (parts.length >= 3) {
                        Estado inicio = null;
                        ArrayList<Estado> fin = null;
                        for (int i = 0; i < this.estados.size(); i++) {
                            if (this.estados.get(i).getNombre().equals(parts[0])) {
                                inicio = this.estados.get(i);
                            }
                        }
                        for (int i = 0; i < this.estados.size(); i++) {
                            for (int j = 2; j < parts.length; j++) {
                                if (this.estados.get(i).getNombre().equals(parts[j])) {
                                    fin.add(this.estados.get(i));
                                    break;
                                }
                            }
                        }
                        TransicionAFND trans = new TransicionAFND(inicio, parts[1].charAt(1), fin);
                        this.transiciones.add(trans);
                    }
                }
                while ((line = reader.readLine()) != null) {
                    if (line.contains("FIN")) {
                        break;
                    }
                    parts = line.split(" ");
                    if (parts.length >= 2) {
                        Estado inicio = null;
                        ArrayList<Estado> finales = null;
                        for (int i = 0; i < this.estados.size(); i++) {
                            if (this.estados.get(i).getNombre().equals(parts[0])) {
                                inicio = this.estados.get(i);
                            }
                        }
                        for (int i = 0; i < this.estados.size(); i++) {
                            for (int j = 1; j < parts.length; j++) {
                                if (this.estados.get(i).getNombre().equals(parts[j])) {
                                    finales.add(this.estados.get(i));
                                    break;
                                }
                            }
                        }
                        TransicionLambda transL = new TransicionLambda(inicio, finales);
                        this.transicionesLamba.add(transL);
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR:" + e.getMessage());
            }
        }
    }

    @Override
    public String write(String nombre, ArrayList<Estado> estados, Estado inicial, ArrayList<Estado> finales, ArrayList<TransicionAFND> transiciones, ArrayList<TransicionLambda> transicionesL) {
        File file = new File("src\\main\\resources\\" + nombre + ".txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.toString()));
            writer.write("TIPO: AFND");
            writer.newLine();
            writer.write("ESTADOS: ");
            for (int i = 0; i < estados.size(); i++) {
                writer.write(estados.get(i).getNombre() + " ");
            }
            writer.newLine();
            writer.write("INICIAL: " + inicial.getNombre());
            writer.newLine();
            writer.write("FINALES: ");
            for (int i = 0; i < finales.size(); i++) {
                writer.write(finales.get(i).getNombre() + " ");
            }
            writer.newLine();
            writer.write("TRANSICIONES:");
            writer.newLine();
            for (int i = 0; i < transiciones.size(); i++) {
               String estadosFinales = "";
                for (int j = 0; j < transiciones.size(); j++) {
                    estadosFinales += transicionesL.get(i).getEstadosFinales().get(j).getNombre()+" ";
                }
                writer.write(transiciones.get(i).getEstadoInicial()+" "+estadosFinales);
                writer.newLine();
            }
            writer.write("TRANSICIONES LAMBDA:");
            writer.newLine();
            for (int i = 0; i < transicionesL.size(); i++) {
                String estadosFinalesL  = "";
                for (int j = 0; j < transicionesL.get(i).getEstadosFinales().size(); j++) {
                    estadosFinalesL += transicionesL.get(i).getEstadosFinales().get(j).getNombre()+" ";
                }
                writer.write(transicionesL.get(i).getEstadoInicial()+ " "+estadosFinalesL);
                writer.newLine();
            }
            writer.write("FIN");
            writer.flush();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return file.toString();
    }

    @Override
    public boolean esFinal(Estado estado) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
