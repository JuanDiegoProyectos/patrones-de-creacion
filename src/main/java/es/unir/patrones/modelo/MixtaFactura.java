package es.unir.patrones.modelo;

/** Producto MIXTA/FACTURA: comportamiento específico de su combinación. */
public final class MixtaFactura extends InformeBase implements Factura {
    public MixtaFactura(String nombre) {
        super(nombre, Categoria.MIXTA, Tipo.FACTURA);
    }

    @Override
    protected String mensajeProcesamiento() {
        return "Procesando informe de MIXTA de tipo FACTURA";
    }
}
