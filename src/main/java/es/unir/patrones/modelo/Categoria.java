package es.unir.patrones.modelo;

/** Codificación del categoría en el nombre del archivo. */
public enum Categoria {
    ENTRADA("ENT"),
    SALIDA("SAL"),
    MIXTA("MIX");

    private final String codigo;

    Categoria(String codigo) {
        this.codigo = codigo;
    }

    public String codigo() {
        return codigo;
    }

    public static Categoria desdeCodigo(String codigo) {
        for (Categoria valor : values()) {
            if (valor.codigo.equals(codigo)) {
                return valor;
            }
        }
        throw new IllegalArgumentException("Categoría no admitido: " + codigo);
    }
}
