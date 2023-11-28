package Modelo;

import Interfaces.IAutomataFinitoDeterminista;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AFD implements IAutomataFinitoDeterminista {

    private String tipo = "AFD";
    private ArrayList<Estado> estados;
    private ArrayList<TransicionAFD> transiciones;

    public AFD(ArrayList<Estado> estados, ArrayList<TransicionAFD> transiciones) {
        this.estados = estados;
        this.transiciones = transiciones;
    }

    public void agregarTransicion(Estado e1, char simbolo, Estado e2) {

    }

    public Estado transicion(Estado estado, char simbolo) {
        return null;
    }

    @Override
    public boolean reconocer(String cadena) {
        char[] simbolo = cadena.toCharArray();
        Estado estado = null;
        for (int i = 0; i < simbolo.length; i++) {
            estado = transicion(estado, simbolo[i]);
        }
        return esFinal(estado);
    }

    public static AFD pedir() {
        return null;
    }

    @Override
    public void load(String filePath) throws Exception {
        File fichero = new File(filePath);
        if (fichero.exists()) {
            try {
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
                        this.estados.get(i).setInicial(true);
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
                
                while ((line = reader.readLine()) != "FIN") {                 
                    parts = line.split(" ");
                    if (parts.length == 3) {
                        Estado inicio = null, fin = null;
                        for (int i = 0; i < this.estados.size(); i++) {
                            if (this.estados.get(i).getNombre().equals(parts[1])) {
                                inicio = this.estados.get(i);
                            }
                        }
                        for (int i = 0; i < this.estados.size(); i++) {
                            if (this.estados.get(i).getNombre().equals(parts[3])) {
                                fin = this.estados.get(i);
                            }
                        }
                        TransicionAFD trans = new TransicionAFD(inicio, parts[2].charAt(0), fin);
                        this.transiciones.add(trans);
                    }
                    
                }
            } catch (Exception e) {
                System.out.println("ERROR:" + e.getMessage());
            }
        }
    }

    public void crearFichero(String nombre, ArrayList<Estado> estados, Estado inicial, ArrayList<Estado> finales, ArrayList<TransicionAFD> transiciones) {
        File file = new File("../../resources/" + nombre + ".txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.toString()));
            writer.write("TIPO: AFD");
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
                writer.write(finales.get(i).getNombre());
            }
            writer.newLine();
            writer.write("TRANSICIONES:");
            writer.newLine();
            for (int i = 0; i < transiciones.size(); i++) {
                writer.write(transiciones.get(i).getEstadoInicial() + " '" + transiciones.get(i).getSimbolo() + "' " + transiciones.get(i).getEstadoFinal());
                writer.newLine();
            }
            writer.write("FIN");
            writer.flush();
            //LEE EL NUEVO FICHERO
            load(file.toString());
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    @Override
    public boolean esFinal(Estado estado) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
