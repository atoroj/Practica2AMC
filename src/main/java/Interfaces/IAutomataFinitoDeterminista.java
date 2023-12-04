package Interfaces;

import Modelo.Estado;
import Modelo.TransicionAFD;
import java.util.ArrayList;

public interface IAutomataFinitoDeterminista extends IProceso {

    /**
     * Carga el AFD a partir de los datos del fichero de texto indicado.El
     * fichero de texto contendrá la descripción (Estados, estado inicial,
     * estados finales y transiciones).
     *
     * @param filePath String con la ruta al fichero de texto con la descripción
     * del AFD.
     * @throws java.lang.Exception
     */
    void load(String filePath) throws Exception;
    
    String write(String nombre, ArrayList<Estado> estados, Estado inicial, ArrayList<Estado> finales, ArrayList<TransicionAFD> transiciones);
}
