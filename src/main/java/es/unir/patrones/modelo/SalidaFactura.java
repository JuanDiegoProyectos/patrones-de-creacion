package es.unir.patrones.modelo;

/** Producto SALIDA/FACTURA: comportamiento específico de su combinación. */
public final class SalidaFactura extends InformeBase implements Factura {
    public SalidaFactura(String nombre) {
        super(nombre, Categoria.SALIDA, Tipo.FACTURA);
    }

    @Override
    protected String mensajeProcesamiento() {
        return "Procesando informe de SALIDA de tipo FACTURA";
    }
}
