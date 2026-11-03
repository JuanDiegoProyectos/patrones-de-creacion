package es.unir.patrones.modelo;

/** Producto MIXTA/COMPRA: comportamiento específico de su combinación. */
public final class MixtaCompra extends InformeBase implements Compra {
    public MixtaCompra(String nombre) {
        super(nombre, Categoria.MIXTA, Tipo.COMPRA);
    }

    @Override
    protected String mensajeProcesamiento() {
        return "Procesando informe de MIXTA de tipo COMPRA";
    }
}
