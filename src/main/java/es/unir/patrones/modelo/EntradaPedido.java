package es.unir.patrones.modelo;

/** Pedido exclusivo de la familia ENTRADA. */
public final class EntradaPedido extends InformeBase implements Pedido {
    public EntradaPedido(String nombre) {
        super(nombre, Categoria.ENTRADA, Tipo.PEDIDO);
    }

    @Override
    protected String mensajeProcesamiento() {
        return "Procesando informe de ENTRADA de tipo PEDIDO";
    }
}
