package Interfaces;

import Modelo.Estado;
import Modelo.TransicionAFND;
import Modelo.TransicionLambda;
import java.util.ArrayList;

public interface IAutomataFinitoNoDeterminista extends IProceso {

    /**
     * Carga el AFND a partir de los datos del fichero de texto indicado.El
     * fichero de texto contendrá la descripción (Estados, estado inicial,
     * estados finales, transiciones y transiciones lambda).
     *
     * @param filePath String con la ruta al fichero de texto con la descripción
     * del AFND.
     * @throws java.lang.Exception
     */
    void load(String filePath) throws Exception;
    
    String write(String nombre, ArrayList<Estado> estados, Estado inicial, ArrayList<Estado> finales, ArrayList<TransicionAFND> transiciones, ArrayList<TransicionLambda> transicionesL);
}
