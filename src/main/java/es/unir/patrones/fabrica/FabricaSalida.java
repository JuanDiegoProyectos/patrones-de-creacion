package es.unir.patrones.fabrica;

import es.unir.patrones.modelo.Compra;
import es.unir.patrones.modelo.Factura;
import es.unir.patrones.modelo.SalidaCompra;
import es.unir.patrones.modelo.SalidaFactura;

/** Produce la familia de informes de SALIDA. */
public final class FabricaSalida implements FabricaInformes {
    @Override
    public Factura crearFactura(String nombre) {
        return new SalidaFactura(nombre);
    }

    @Override
    public Compra crearCompra(String nombre) {
        return new SalidaCompra(nombre);
    }
}
