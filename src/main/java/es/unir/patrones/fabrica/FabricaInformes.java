package es.unir.patrones.fabrica;

import es.unir.patrones.modelo.Compra;
import es.unir.patrones.modelo.Factura;

/** Abstract Factory: crea la familia de productos FACTURA y COMPRA. */
public interface FabricaInformes {
    Factura crearFactura(String nombre);
    Compra crearCompra(String nombre);
}
