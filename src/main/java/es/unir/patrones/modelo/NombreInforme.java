package es.unir.patrones.modelo;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Representa la categoría, el tipo y el nombre de un informe.
 * Solo analiza el nombre; no intenta abrir ni leer el archivo.
 */
public final class NombreInforme {
    private static final Pattern FORMATO = Pattern.compile(
            "^([A-Z]{3})_([A-Z]{3})_([A-Za-z0-9][A-Za-z0-9_-]*)\\.txt$");

    private final String nombre;
    private final Categoria categoria;
    private final Tipo tipo;

    private NombreInforme(String nombre, Categoria categoria, Tipo tipo) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.tipo = tipo;
    }

    /**
     * Admite CAT_TIPO_identificador.txt con códigos definidos por el sistema.
     * La restricción léxica es una validación adicional al enunciado académico.
     */
    public static NombreInforme analizar(String nombre) {
        Objects.requireNonNull(nombre, "El nombre no puede ser null");
        Matcher coincidencia = FORMATO.matcher(nombre);
        if (!coincidencia.matches()) {
            throw new IllegalArgumentException("Formato de informe inválido: " + nombre);
        }
        return new NombreInforme(
                nombre,
                Categoria.desdeCodigo(coincidencia.group(1)),
                Tipo.desdeCodigo(coincidencia.group(2)));
    }

    public String nombre() { return nombre; }
    public Categoria categoria() { return categoria; }
    public Tipo tipo() { return tipo; }
}
