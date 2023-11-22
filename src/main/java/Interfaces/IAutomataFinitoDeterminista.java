/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

/**
 *
 * @author Usuario
 */
public interface IAutomataFinitoDeterminista extends IProceso{
    /**
     * Carga el AFD a partir de los datos del fichero de texto indicado.
     * El fichero de texto contendrá la descripción (Estados, estado
     * inicial, estados finales y transiciones).
     * @param filePath String con la ruta al fichero de texto con la
     *                 descripción del AFD.
     */
    void load(String filePath) throws Exception;
}
