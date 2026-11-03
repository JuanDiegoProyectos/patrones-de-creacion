package es.unir.patrones.fabrica;

import es.unir.patrones.modelo.Compra;
import es.unir.patrones.modelo.Factura;
import es.unir.patrones.modelo.MixtaCompra;
import es.unir.patrones.modelo.MixtaFactura;

/** Produce la familia de informes de MIXTA. */
public final class FabricaMixta implements FabricaInformes {
    @Override
    public Factura crearFactura(String nombre) {
        return new MixtaFactura(nombre);
    }

    @Override
    public Compra crearCompra(String nombre) {
        return new MixtaCompra(nombre);
    }
}
