package es.unir.patrones.procesamiento;

import es.unir.patrones.fabrica.FabricaInformes;
import es.unir.patrones.fabrica.FabricaPedidos;
import es.unir.patrones.modelo.Categoria;
import es.unir.patrones.modelo.Informe;
import es.unir.patrones.modelo.NombreInforme;

import java.io.PrintStream;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Cliente del patrón Abstract Factory.
 *
 * <p>Interpreta nombres de archivos, localiza la familia adecuada y delega
 * la creación de productos. Nunca instancia productos concretos.</p>
 */
public final class ProcesadorInformes {
    private final Map<Categoria, FabricaInformes> fabricas;

    /** Copia el registro para que posteriores cambios externos no afecten al cliente. */
    public ProcesadorInformes(Map<Categoria, FabricaInformes> fabricas) {
        Objects.requireNonNull(fabricas, "El registro de fábricas es obligatorio");
        this.fabricas = new EnumMap<>(Categoria.class);
        this.fabricas.putAll(fabricas);
    }

    /**
     * Procesa en orden los nombres recibidos. Ante un error, detiene la llamada.
     * Los informes anteriores al error pueden haberse impreso (no es transaccional).
     */
    public void procesarInformes(String[] archivos, PrintStream salida) {
        Objects.requireNonNull(archivos, "La lista de informes es obligatoria");
        Objects.requireNonNull(salida, "El canal de salida es obligatorio");

        for (String archivo : archivos) {
            NombreInforme nombre = NombreInforme.analizar(archivo);
            FabricaInformes fabrica = fabricas.get(nombre.categoria());
            if (fabrica == null) {
                throw new IllegalArgumentException("Sin fábrica para: " + nombre.categoria());
            }
            Informe informe = crear(fabrica, nombre);
            informe.procesar(salida);
        }
    }

    private Informe crear(FabricaInformes fabrica, NombreInforme nombre) {
        switch (nombre.tipo()) {
            case FACTURA:
                return fabrica.crearFactura(nombre.nombre());
            case COMPRA:
                return fabrica.crearCompra(nombre.nombre());
            case PEDIDO:
                // PEDIDO es una capacidad exclusiva de la familia ENTRADA.
                if (nombre.categoria() == Categoria.ENTRADA
                        && fabrica instanceof FabricaPedidos) {
                    return ((FabricaPedidos) fabrica).crearPedido(nombre.nombre());
                }
                throw new IllegalArgumentException(
                        "Combinación no admitida: " + nombre.categoria() + "/PEDIDO");
            default:
                throw new IllegalStateException("Tipo no contemplado: " + nombre.tipo());
        }
    }
}
