package es.unir.patrones.modelo;

/** Producto ENTRADA/FACTURA: comportamiento específico de su combinación. */
public final class EntradaFactura extends InformeBase implements Factura {
    public EntradaFactura(String nombre) {
        super(nombre, Categoria.ENTRADA, Tipo.FACTURA);
    }

    @Override
    protected String mensajeProcesamiento() {
        return "Procesando informe de ENTRADA de tipo FACTURA";
    }
}
