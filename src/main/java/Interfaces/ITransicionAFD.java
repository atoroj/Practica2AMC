package Interfaces;

import Modelo.Estado;

public interface ITransicionAFD {

    Estado getEstadoInicial();

    Estado getEstadoFinal();

    char getSimbolo();
}
