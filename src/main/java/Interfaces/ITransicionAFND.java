/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.Estado;
import java.util.ArrayList;

/**
 *
 * @author Antonio
 */
public interface ITransicionAFND {
    Estado getEstadoInicial();

    ArrayList<Estado> getEstadosFinales();

    char getSimbolo();
}
