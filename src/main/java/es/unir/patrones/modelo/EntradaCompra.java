package es.unir.patrones.modelo;

/** Producto ENTRADA/COMPRA: comportamiento específico de su combinación. */
public final class EntradaCompra extends InformeBase implements Compra {
    public EntradaCompra(String nombre) {
        super(nombre, Categoria.ENTRADA, Tipo.COMPRA);
    }

    @Override
    protected String mensajeProcesamiento() {
        return "Procesando informe de ENTRADA de tipo COMPRA";
    }
}
