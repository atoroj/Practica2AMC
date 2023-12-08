package Modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

public class AFND {

    private ArrayList<Estado> estados;
    private ArrayList<TransicionAFND> transiciones;
    private ArrayList<TransicionLambda> transicionesLamba;
    private ArrayList<TransicionMacroestado> transicionesMacroestados;
    private ArrayList<Character> simbolosDistintos;
    private int contadorMacroestados = 0;

    public AFND() {
        this.estados = new ArrayList<>();
        this.transiciones = new ArrayList<>();
        this.transicionesLamba = new ArrayList<>();
        this.simbolosDistintos = new ArrayList<>();
        this.transicionesMacroestados = new ArrayList<>();
    }

    public AFND(ArrayList<Estado> estados, ArrayList<TransicionAFND> transiciones, ArrayList<TransicionLambda> transicionesLamba) {
        this.estados = estados;
        this.transiciones = transiciones;
        this.transicionesLamba = transicionesLamba;
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<Estado> estados) {
        this.estados = estados;
    }

    public ArrayList<TransicionAFND> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(ArrayList<TransicionAFND> transiciones) {
        this.transiciones = transiciones;
    }

    public ArrayList<TransicionLambda> getTransicionesLamba() {
        return transicionesLamba;
    }

    public void setTransicionesLamba(ArrayList<TransicionLambda> transicionesLamba) {
        this.transicionesLamba = transicionesLamba;
    }

    public MacroEstado transicion(MacroEstado macroestado, char simbolo) {
        ArrayList<Estado> estadosResult = new ArrayList<>();
        ArrayList<Estado> estadosaux = new ArrayList<>();
        MacroEstado macroestadoResut = new MacroEstado();
        //Primera parte
        for (Estado estado : macroestado.getEstados()) {
            for (TransicionAFND transicionAFND : transiciones) {
                if (transicionAFND.getEstadoInicial().getNombre().equals(estado.getNombre()) && transicionAFND.getSimbolo() == simbolo) {
                    estadosResult.addAll(transicionAFND.getEstadosFinales());
                }
            }
        }

        //Segunda parte
        for (Estado estado : estadosResult) {
            if (transicionLamda(estado) != null) {
                estadosaux.addAll(transicionLamda(estado));
            }
        }

        if (!estadosaux.isEmpty()) {
            estadosResult.addAll(estadosaux);
        }
        macroestadoResut.setNombre("Q" + contadorMacroestados);
        contadorMacroestados++;
        macroestadoResut.setEstados(estadosResult);

        return macroestadoResut;
    }

    public ArrayList<Estado> transicionLamda(Estado estado) {
        for (TransicionLambda transicionLambda : transicionesLamba) {
            if (transicionLambda.getEstadoInicial().getNombre().equals(estado.getNombre())) {
                return transicionLambda.getEstadosFinales();
            }
        }
        return null;
    }

    public boolean esFinal(MacroEstado macroestado) {
        if (macroestado.getNombre().equals("M")) {
            return false;
        }
        for (Estado estado : macroestado.getEstados()) {
            if (estado.isNodoFinal()) {
                return true;
            }
        }
        return false;
    }

    //Recoge el macroestado comprendido por los estados
    private ArrayList<Estado> lamda_clausura(ArrayList<Estado> estados) {
        ArrayList<Estado> macroEstados = new ArrayList<>();
        return macroEstados;
    }

    //Recoge el macroestado inicial
    private ArrayList<Estado> lamda_clausura_inicial(ArrayList<Estado> estados) {
        ArrayList<Estado> macroEstados = new ArrayList<>();
        for (Estado estado : estados) {
            if (estado.isNodoInicial()) {
                macroEstados.add(estado);
                macroEstados.addAll(transicionLamda(estado));
            }
        }
        return macroEstados;
    }

    public boolean reconocer(String cadena) {
        char[] simbolo = cadena.toCharArray();
        boolean existeMacroestado = false;
        ArrayList<MacroEstado> macroestados = new ArrayList<>();
        MacroEstado macroEstadoResult = null;
        MacroEstado macroEstadoInicial = new MacroEstado("Q0", lamda_clausura_inicial(estados));

        //Iterando cada estado dentro de los macroestados resultantes
        //for (MacroEstado macroestadoActual : macroestados) {
        //Iterando por cada caracter distinto dentro de cada macroestado
        for (Character simboloActual : simbolo) {
            //Obtenemos el macroestado al que apunta macroestadoActual con el simboloActual
            macroEstadoResult = transicion(macroEstadoInicial, simboloActual);
            //transicionesMacroestados.add(new TransicionMacroestado(macroestadoActual, macroEstadoResult, simboloActual));
//                //En este for comprobamos si el macroestadoResult está introducido dentro del ArrayList de macroestados
//                for (MacroEstado macroestado1 : macroestados) {
//                    //Si la longitud de los estados de ambos macroestados es distinta sabemos que no está introducido
//                    if (macroestado1.getEstados().size() == macroEstadoResult.getEstados().size()) {
//                        for (int i = 0; i < macroestado1.getEstados().size(); i++) {
//                            if (macroEstadoResult.getEstados().get(i).getNombre().equals(macroestado1.getEstados().get(i).getNombre())) {
//                                existeMacroestado = true;
//                            }
//                        }
//                    } else {
//                        macroestados.add(macroestadoActual);
//                    }
//                }
//                //Si no está introducido se introduce
//                if (!existeMacroestado) {
//                    macroestados.add(macroestadoActual);
//                }
        }
        //}

        if (macroEstadoResult == null) {
            macroEstadoResult.setNombre("M");
        }

        return esFinal(macroEstadoResult);
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
                this.transicionesLamba.clear();
                BufferedReader reader = new BufferedReader(new FileReader(fichero));
                String line;
                line = reader.readLine(); //TIPO:
                if (line.equals("TIPO: AFND")) {
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
                    if (line.contains("TRANSICIONES LAMBDA:")) {
                        break;
                    }
                    parts = line.split(" ");
                    if (parts.length >= 3) {
                        Estado inicio = null;
                        ArrayList<Estado> fin = new ArrayList<>();
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

                        //Para guardar los distintos simbolos introducidos para tratarlos en el AFND
                        if (!simbolosDistintos.contains(parts[1].charAt(1))) {
                            simbolosDistintos.add(parts[1].charAt(1));
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
                        ArrayList<Estado> finales = new ArrayList<>();
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
        
        return booleanResults;
    }

    public String write(ArrayList<Estado> estados, Estado inicial, ArrayList<Estado> finales, ArrayList<TransicionAFND> transiciones, ArrayList<TransicionLambda> transicionesL) {
        int numero = 0;
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        numero = rand.nextInt(100);
        
        File file = new File("src\\main\\resources\\AFND" + numero + ".txt");
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
                for (int j = 0; j < transiciones.get(i).getEstadosFinales().size(); j++) {
                    estadosFinales += transiciones.get(i).getEstadosFinales().get(j).getNombre() + " ";
                }
                writer.write(transiciones.get(i).getEstadoInicial() + " '" + transiciones.get(i).getSimbolo() + "' " + estadosFinales);
                writer.newLine();
            }
            writer.write("TRANSICIONES LAMBDA:");
            writer.newLine();
            if (!transicionesL.isEmpty()) {
                for (int i = 0; i < transicionesL.size(); i++) {
                    String estadosFinalesL = "";
                    for (int j = 0; j < transicionesL.get(i).getEstadosFinales().size(); j++) {
                        estadosFinalesL += transicionesL.get(i).getEstadosFinales().get(j).getNombre() + " ";
                    }
                    writer.write(transicionesL.get(i).getEstadoInicial() + " " + estadosFinalesL);
                    writer.newLine();
                }
            }
            writer.write("FIN");
            writer.flush();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getLocalizedMessage());
        }
        return file.toString();
    }

}
