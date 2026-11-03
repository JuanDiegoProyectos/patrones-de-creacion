package es.unir.patrones.modelo;

/** Producto SALIDA/COMPRA: comportamiento específico de su combinación. */
public final class SalidaCompra extends InformeBase implements Compra {
    public SalidaCompra(String nombre) {
        super(nombre, Categoria.SALIDA, Tipo.COMPRA);
    }

    @Override
    protected String mensajeProcesamiento() {
        return "Procesando informe de SALIDA de tipo COMPRA";
    }
}
