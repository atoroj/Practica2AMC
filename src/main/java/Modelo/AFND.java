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
    private ArrayList<TransicionLambda> transicionesLambda;
    private ArrayList<TransicionMacroestado> transicionesMacroestados;
    private ArrayList<Character> simbolosDistintos;
    private ArrayList<MacroEstado> macroestados;
    private int contadorMacroestados;

    public AFND() {
        this.estados = new ArrayList<>();
        this.transiciones = new ArrayList<>();
        this.transicionesLambda = new ArrayList<>();
        this.simbolosDistintos = new ArrayList<>();
        this.transicionesMacroestados = new ArrayList<>();
        this.macroestados = new ArrayList<>();
        this.contadorMacroestados = 0;
    }

    public AFND(ArrayList<Estado> estados, ArrayList<TransicionAFND> transiciones, ArrayList<TransicionLambda> transicionesLamba) {
        this.estados = estados;
        this.transiciones = transiciones;
        this.transicionesLambda = transicionesLamba;
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

    public ArrayList<TransicionLambda> getTransicionesLambda() {
        return transicionesLambda;
    }

    public void setTransicionesLambda(ArrayList<TransicionLambda> transicionesLambda) {
        this.transicionesLambda = transicionesLambda;
    }

    /**
     * @return the macroestados
     */
    public ArrayList<MacroEstado> getMacroestados() {
        return macroestados;
    }

    /**
     * @param macroestados the macroestados to set
     */
    public void setMacroestados(ArrayList<MacroEstado> macroestados) {
        this.macroestados = macroestados;
    }

    /**
     * @return the transicionesMacroestados
     */
    public ArrayList<TransicionMacroestado> getTransicionesMacroestados() {
        return transicionesMacroestados;
    }

    /**
     * @param transicionesMacroestados the transicionesMacroestados to set
     */
    public void setTransicionesMacroestados(ArrayList<TransicionMacroestado> transicionesMacroestados) {
        this.transicionesMacroestados = transicionesMacroestados;
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
            if (transicionLambda(estado) != null) {
                estadosaux.addAll(transicionLambda(estado));
            }
        }

        if (!estadosaux.isEmpty()) {
            estadosResult.addAll(estadosaux);
        }
        
        macroestadoResut.setEstados(estadosResult);
        if (!existeMacroestado(macroestadoResut)) {
            macroestadoResut.setNombre("Q" + contadorMacroestados);
            contadorMacroestados++;
        }else{
            for (MacroEstado estado : macroestados) {
                if (estado.equals(macroestadoResut)) {
                    macroestadoResut.setNombre(estado.getNombre());
                }
            }
        }

        return macroestadoResut;
    }

    public ArrayList<Estado> transicionLambda(Estado estado) {
        ArrayList<Estado> estadosAlcanzables = new ArrayList<>();
        transicionLambdaRecursivo(estado, estadosAlcanzables, new ArrayList<>());
        return estadosAlcanzables;
    }

    //Comprueba todos los estados a los que llega por lambda desde el estado pasado por parametro de forma recurrente
    private void transicionLambdaRecursivo(Estado estado, ArrayList<Estado> estadosAlcanzables, ArrayList<Estado> visitados) {
        // Evitar ciclos infinitos
        if (visitados.contains(estado)) {
            return;
        }

        visitados.add(estado);

        for (TransicionLambda transicionLambda : transicionesLambda) {
            if (transicionLambda.getEstadoInicial().getNombre().equals(estado.getNombre())) {
                for (Estado estadoFinal : transicionLambda.getEstadosFinales()) {
                    // Agregar el estado final a la lista
                    estadosAlcanzables.add(estadoFinal);
                    // Llamada recursiva para explorar más transiciones lambda
                    transicionLambdaRecursivo(estadoFinal, estadosAlcanzables, visitados);
                }
            }
        }
    }

//    public ArrayList<Estado> transicionLamda(Estado estado) {
//        for (TransicionLambda transicionLambda : transicionesLambda) {
//            if (transicionLambda.getEstadoInicial().getNombre().equals(estado.getNombre())) {
//                return transicionLambda.getEstadosFinales();
//            }
//        }
//
//        return null;
//    }
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

    //Recoge el macroestado inicial
    private ArrayList<Estado> lamda_clausura_inicial(ArrayList<Estado> estados) {
        ArrayList<Estado> macroEstados = new ArrayList<>();
        for (Estado estado : estados) {
            if (estado.isNodoInicial()) {
                macroEstados.add(estado);
                macroEstados.addAll(transicionLambda(estado));
            }
        }
        return macroEstados;
    }

    private boolean existeMacroestado(MacroEstado macro) {
        for (MacroEstado macroestado : macroestados) {
            if (macroestado.equals(macro)) {
                return true;
            }
        }
        return false;
    }

    public boolean reconocer(String cadena) {
        char[] simbolo = cadena.toCharArray();
        MacroEstado macroEstadoResult = new MacroEstado();
        MacroEstado macroEstadoInicial = new MacroEstado("Q0", lamda_clausura_inicial(estados));

        //Iterando por cada caracter distinto dentro de cada macroestado
        for (Character simboloActual : simbolo) {
            macroEstadoResult = transicion(macroEstadoInicial, simboloActual);
            transicionesMacroestados.add(new TransicionMacroestado(macroEstadoInicial, macroEstadoResult, simboloActual));
            if (!existeMacroestado(macroEstadoResult)) {
                macroestados.add(macroEstadoResult);
            }
        }

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
                this.transicionesLambda.clear();
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
                        this.transicionesLambda.add(transL);
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
