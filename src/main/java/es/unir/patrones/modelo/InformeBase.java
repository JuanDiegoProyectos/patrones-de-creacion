package es.unir.patrones.modelo;

import java.io.PrintStream;
import java.util.Objects;

/** Reutiliza metadatos y salida de consola; delega el mensaje en cada producto. */
public abstract class InformeBase implements Informe {
    private final String nombre;
    private final Categoria categoria;
    private final Tipo tipo;

    protected InformeBase(String nombre, Categoria categoria, Tipo tipo) {
        this.nombre = Objects.requireNonNull(nombre, "nombre");
        this.categoria = Objects.requireNonNull(categoria, "categoria");
        this.tipo = Objects.requireNonNull(tipo, "tipo");
    }

    @Override
    public final String nombreArchivo() { return nombre; }

    @Override
    public final Categoria categoria() { return categoria; }

    @Override
    public final Tipo tipo() { return tipo; }

    @Override
    public final void procesar(PrintStream salida) {
        Objects.requireNonNull(salida, "salida").println(mensajeProcesamiento());
    }

    protected abstract String mensajeProcesamiento();
}
