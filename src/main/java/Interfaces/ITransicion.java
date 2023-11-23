package Interfaces;

import Modelo.Estado;

public interface ITransicion {

    Estado getEstadoInicial();

    Estado getEstadoFinal();

    char getSimbolo();
}
