package es.unir.patrones.app;

import es.unir.patrones.fabrica.*;
import es.unir.patrones.modelo.Categoria;
import es.unir.patrones.procesamiento.ProcesadorInformes;
import java.util.EnumMap;
import java.util.Map;

/** Ejecuta el sistema final o muestra sus tres etapas evolutivas. */
public final class Main {
    private Main() { }

    public static void main(String[] args) {
        Map<Categoria, FabricaInformes> fabricas = new EnumMap<>(Categoria.class);
        fabricas.put(Categoria.ENTRADA, new FabricaEntrada());
        fabricas.put(Categoria.SALIDA, new FabricaSalida());
        fabricas.put(Categoria.MIXTA, new FabricaMixta());
        ProcesadorInformes procesador = new ProcesadorInformes(fabricas);

        String[] inicial = {
            "ENT_FAC_001.txt", "ENT_COM_001.txt", "SAL_FAC_001.txt", "SAL_COM_001.txt"
        };
        String[] mixta = { "MIX_FAC_001.txt", "MIX_COM_001.txt" };
        String[] pedido = { "ENT_PED_001.txt" };

        if (args.length == 1 && "--etapas".equals(args[0])) {
            System.out.println("=== ETAPA 1: MODELO INICIAL ===");
            procesador.procesarInformes(inicial, System.out);
            System.out.println("=== ETAPA 2: NUEVA CATEGORÍA MIXTA ===");
            procesador.procesarInformes(mixta, System.out);
            System.out.println("=== ETAPA 3: NUEVO TIPO PEDIDO ===");
            procesador.procesarInformes(pedido, System.out);
        } else if (args.length == 0) {
            String[] todos = {
                "ENT_FAC_001.txt", "ENT_COM_001.txt", "SAL_FAC_001.txt", "SAL_COM_001.txt",
                "MIX_FAC_001.txt", "MIX_COM_001.txt", "ENT_PED_001.txt"
            };
            procesador.procesarInformes(todos, System.out);
        } else {
            System.err.println("Uso: Main [--etapas]");
            System.exit(2);
        }
    }
}
