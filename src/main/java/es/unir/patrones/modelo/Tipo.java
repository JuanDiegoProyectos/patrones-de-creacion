package es.unir.patrones.modelo;

/** Codificación del tipo en el nombre del archivo. */
public enum Tipo {
    FACTURA("FAC"),
    COMPRA("COM"),
    PEDIDO("PED");

    private final String codigo;

    Tipo(String codigo) {
        this.codigo = codigo;
    }

    public String codigo() {
        return codigo;
    }

    public static Tipo desdeCodigo(String codigo) {
        for (Tipo valor : values()) {
            if (valor.codigo.equals(codigo)) {
                return valor;
            }
        }
        throw new IllegalArgumentException("Tipo no admitido: " + codigo);
    }
}
