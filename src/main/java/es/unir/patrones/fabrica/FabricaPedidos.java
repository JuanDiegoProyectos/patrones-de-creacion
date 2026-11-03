package es.unir.patrones.fabrica;

import es.unir.patrones.modelo.Pedido;

/** Extensión específica sin obligar a SALIDA/MIXTA a admitir PEDIDO. */
public interface FabricaPedidos {
    Pedido crearPedido(String nombre);
}
