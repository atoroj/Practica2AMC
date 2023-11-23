package Interfaces;

import Modelo.Estado;

public interface IProceso {

    boolean esFinal(Estado estado);

    boolean reconocer(String cadena);

    String toString();
}
