package es.unir.patrones.fabrica;

import es.unir.patrones.modelo.Compra;
import es.unir.patrones.modelo.EntradaCompra;
import es.unir.patrones.modelo.EntradaFactura;
import es.unir.patrones.modelo.EntradaPedido;
import es.unir.patrones.modelo.Factura;
import es.unir.patrones.modelo.Pedido;

/** Produce la familia de informes de ENTRADA, incluida su extensión PEDIDO. */
public final class FabricaEntrada implements FabricaInformes, FabricaPedidos {
    @Override
    public Factura crearFactura(String nombre) {
        return new EntradaFactura(nombre);
    }

    @Override
    public Compra crearCompra(String nombre) {
        return new EntradaCompra(nombre);
    }

    @Override
    public Pedido crearPedido(String nombre) {
        return new EntradaPedido(nombre);
    }
}
