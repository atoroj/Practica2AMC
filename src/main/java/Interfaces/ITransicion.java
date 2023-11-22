/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.Estado;

/**
 *
 * @author Usuario
 */
public interface ITransicion {
    /**
     * Devuelve el código del estado inicial.
     * @return String con el código del estado inicial.
     */
    Estado getEstadoInicial();

    /**
     * Devuelve el código del estado final.
     * @return String con el código del estado final.
     */
    Estado getEstadoFinal();

    /**
     * Devuelve el carácter (símbolo) que produce la transición.
     * @return Char con el carácter que produce la transición.
     */
    char getSimbolo();
}
