package net.ejemplo.sesiones.util;

public final class StringFunctions {

    public static String fillLeft(Object origen, char relleno, int longitudFinal) {
        return fillLeft(String.valueOf(origen), relleno, longitudFinal);
    }
}