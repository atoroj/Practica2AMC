package Modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

public class AFD {

    private ArrayList<Estado> estados;
    private ArrayList<TransicionAFD> transiciones;

    public AFD() {
        estados = new ArrayList<>();
        transiciones = new ArrayList<>();
    }

    public AFD(ArrayList<Estado> estados, ArrayList<TransicionAFD> transiciones) {
        this.estados = estados;
        this.transiciones = transiciones;
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<Estado> estados) {
        this.estados = estados;
    }

    public ArrayList<TransicionAFD> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(ArrayList<TransicionAFD> transiciones) {
        this.transiciones = transiciones;
    }

    public Estado transicion(Estado estado, char simbolo) {

        Estado estadoResult = null;

        for (TransicionAFD transicion : transiciones) {
            if (transicion.getEstadoInicial().getNombre().equals(estado.getNombre())
                    && transicion.getSimbolo() == simbolo) {
                estadoResult = transicion.getEstadoFinal();
                break;
            }
        }

        if (estadoResult == null) {
            estadoResult = new Estado("M");
        }
        return estadoResult;
    }

    public boolean reconocer(String cadena) {
        char[] simbolo = cadena.toCharArray();
        Estado estado = null;
        for (int i = 0; i < estados.size(); i++) {
            if (estados.get(i).isNodoInicial()) {
                estado = estados.get(i);
                break;
            }
        }
        for (int i = 0; i < simbolo.length; i++) {
            estado = transicion(estado, simbolo[i]);
            if (estado.getNombre().equals("M")) {
                break;
            }
        }
        return esFinal(estado);
    }

    public boolean[] load(String filePath) throws Exception {
        File fichero = new File(filePath);
        //El primer elemento indica si el fichero existe o no
        //El segundo elemento indica si el fichero cargado es de tipo AFND
        boolean[] booleanResults = new boolean[2];
        if (fichero.exists()) {
            booleanResults[0] = true;
            try {
                this.estados.clear();
                this.transiciones.clear();
                BufferedReader reader = new BufferedReader(new FileReader(fichero));
                String line;
                line = reader.readLine(); //TIPO:
                if (line.equals("TIPO: AFD")) {
                    booleanResults[1] = true;
                }
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
                    if (line.contains("FIN")) {
                        break;
                    }
                    parts = line.split(" ");
                    if (parts.length == 3) {
                        Estado inicio = null, fin = null;
                        for (int i = 0; i < this.estados.size(); i++) {
                            if (this.estados.get(i).getNombre().equals(parts[0])) {
                                inicio = this.estados.get(i);
                            }
                        }
                        for (int i = 0; i < this.estados.size(); i++) {
                            if (this.estados.get(i).getNombre().equals(parts[2])) {
                                fin = this.estados.get(i);
                            }
                        }
                        TransicionAFD trans = new TransicionAFD(inicio, parts[1].charAt(1), fin);
                        this.transiciones.add(trans);
                    }
                }

            } catch (Exception e) {
                System.out.println("ERROR:" + e.getMessage());
            }
        }
        return booleanResults;
    }

    public String write(ArrayList<Estado> estados, Estado inicial, ArrayList<Estado> finales, ArrayList<TransicionAFD> transiciones) {
        
        int numero = 0;
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        numero = rand.nextInt(100);
        
        File file = new File("src\\main\\resources\\AFD" + numero + ".txt");
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
                writer.write(finales.get(i).getNombre() + " ");
            }
            writer.newLine();
            writer.write("TRANSICIONES:");
            writer.newLine();
            for (int i = 0; i < transiciones.size(); i++) {
                writer.write(transiciones.get(i).getEstadoInicial().getNombre() + " '" + transiciones.get(i).getSimbolo() + "' " + transiciones.get(i).getEstadoFinal().getNombre());
                writer.newLine();
            }
            writer.write("FIN");
            writer.flush();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return file.toString();
    }

    public boolean esFinal(Estado estado) {
        return estado.isNodoFinal();
    }

}
