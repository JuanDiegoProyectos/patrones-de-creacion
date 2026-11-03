package es.unir.patrones.modelo;

import java.io.PrintStream;

/** Producto raíz: un informe que puede procesarse de forma polimórfica. */
public interface Informe {
    String nombreArchivo();
    Categoria categoria();
    Tipo tipo();
    void procesar(PrintStream salida);
}
