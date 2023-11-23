/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

/**
 *
 * @author Usuario
 */
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
}
