package es.unir.patrones;

import es.unir.patrones.app.Main;
import es.unir.patrones.fabrica.FabricaEntrada;
import es.unir.patrones.fabrica.FabricaInformes;
import es.unir.patrones.fabrica.FabricaMixta;
import es.unir.patrones.fabrica.FabricaPedidos;
import es.unir.patrones.fabrica.FabricaSalida;
import es.unir.patrones.modelo.Categoria;
import es.unir.patrones.modelo.Compra;
import es.unir.patrones.modelo.EntradaCompra;
import es.unir.patrones.modelo.EntradaFactura;
import es.unir.patrones.modelo.Factura;
import es.unir.patrones.modelo.Informe;
import es.unir.patrones.modelo.NombreInforme;
import es.unir.patrones.modelo.Tipo;
import es.unir.patrones.procesamiento.ProcesadorInformes;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Pruebas reproducibles sin frameworks externos. Cada caso contiene una
 * aserción verificable; cualquier fallo provoca un código de salida distinto de 0.
 */
public final class PruebasInformes {
    private static int aprobadas;
    private static int fallidas;

    private PruebasInformes() { }

    private static ProcesadorInformes procesador(boolean incluirMixta) {
        Map<Categoria, FabricaInformes> registro = new EnumMap<>(Categoria.class);
        registro.put(Categoria.ENTRADA, new FabricaEntrada());
        registro.put(Categoria.SALIDA, new FabricaSalida());
        if (incluirMixta) {
            registro.put(Categoria.MIXTA, new FabricaMixta());
        }
        return new ProcesadorInformes(registro);
    }

    private static String normalizar(String resultado) {
        return resultado.replace("\r\n", "\n");
    }

    private static String salidaCon(ProcesadorInformes procesador, String... nombres) {
        ByteArrayOutputStream memoria = new ByteArrayOutputStream();
        try (PrintStream salida = new PrintStream(memoria, true, StandardCharsets.UTF_8)) {
            procesador.procesarInformes(nombres, salida);
        }
        return normalizar(memoria.toString(StandardCharsets.UTF_8));
    }

    private static String salida(String... nombres) {
        return salidaCon(procesador(true), nombres);
    }

    private static String capturarMain(String... argumentos) {
        PrintStream original = System.out;
        ByteArrayOutputStream memoria = new ByteArrayOutputStream();
        try (PrintStream salida = new PrintStream(memoria, true, StandardCharsets.UTF_8)) {
            System.setOut(salida);
            Main.main(argumentos);
        } finally {
            System.setOut(original);
        }
        return normalizar(memoria.toString(StandardCharsets.UTF_8));
    }

    private static void verificar(String descripcion, Runnable accion) {
        try {
            accion.run();
            aprobadas++;
            System.out.println("OK    " + descripcion);
        } catch (Throwable error) {
            fallidas++;
            System.out.println("ERROR " + descripcion + " -> " + error);
        }
    }

    private static void igual(Object esperado, Object real) {
        if (!Objects.equals(esperado, real)) {
            throw new AssertionError("Esperado: " + esperado + " / obtenido: " + real);
        }
    }

    private static void esperado(Class<? extends Throwable> tipo, Runnable accion) {
        try {
            accion.run();
        } catch (Throwable error) {
            if (tipo.isInstance(error)) {
                return;
            }
            throw new AssertionError("Excepción inesperada: " + error);
        }
        throw new AssertionError("Se esperaba: " + tipo.getSimpleName());
    }

    public static void main(String[] args) {
        String[][] casos = {
                {"ENT_FAC_001.txt", "ENTRADA", "FACTURA"},
                {"ENT_COM_001.txt", "ENTRADA", "COMPRA"},
                {"SAL_FAC_001.txt", "SALIDA", "FACTURA"},
                {"SAL_COM_001.txt", "SALIDA", "COMPRA"},
                {"MIX_FAC_001.txt", "MIXTA", "FACTURA"},
                {"MIX_COM_001.txt", "MIXTA", "COMPRA"},
                {"ENT_PED_001.txt", "ENTRADA", "PEDIDO"}
        };
        for (String[] caso : casos) {
            verificar("Procesamiento válido: " + caso[0], () -> igual(
                    "Procesando informe de " + caso[1] + " de tipo " + caso[2] + "\n",
                    salida(caso[0])));
        }

        verificar("Orden y repetición de elementos", () -> igual(
                "Procesando informe de SALIDA de tipo COMPRA\n" +
                "Procesando informe de ENTRADA de tipo FACTURA\n" +
                "Procesando informe de SALIDA de tipo COMPRA\n",
                salida("SAL_COM_002.txt", "ENT_FAC_002.txt", "SAL_COM_002.txt")));
        verificar("Identificador con guiones y subrayados", () -> igual(
                Tipo.COMPRA, NombreInforme.analizar("ENT_COM_compra_02-A.txt").tipo()));
        verificar("Parser: conserva el nombre completo", () -> igual(
                "ENT_FAC_factura-A.txt", NombreInforme.analizar("ENT_FAC_factura-A.txt").nombre()));
        verificar("Parser: categoría de MIXTA", () -> igual(
                Categoria.MIXTA, NombreInforme.analizar("MIX_FAC_1.txt").categoria()));
        verificar("Parser: tipo PEDIDO", () -> igual(
                Tipo.PEDIDO, NombreInforme.analizar("ENT_PED_1.txt").tipo()));

        verificar("Rechazo de categoría desconocida", () -> esperado(
                IllegalArgumentException.class, () -> salida("XYZ_FAC_001.txt")));
        verificar("Rechazo de tipo desconocido", () -> esperado(
                IllegalArgumentException.class, () -> salida("ENT_XXX_001.txt")));
        verificar("Rechazo de extensión distinta de .txt", () -> esperado(
                IllegalArgumentException.class, () -> salida("ENT_FAC_001.pdf")));
        verificar("Rechazo de extensión en mayúsculas", () -> esperado(
                IllegalArgumentException.class, () -> salida("ENT_FAC_001.TXT")));
        verificar("Rechazo de identificador vacío", () -> esperado(
                IllegalArgumentException.class, () -> salida("ENT_FAC_.txt")));
        verificar("Rechazo de ruta relativa", () -> esperado(
                IllegalArgumentException.class, () -> salida("../ENT_FAC_001.txt")));
        verificar("Rechazo de ruta absoluta", () -> esperado(
                IllegalArgumentException.class, () -> salida("/tmp/ENT_FAC_001.txt")));
        verificar("Rechazo de formato en minúsculas", () -> esperado(
                IllegalArgumentException.class, () -> salida("ent_fac_001.txt")));
        verificar("Rechazo de espacios", () -> esperado(
                IllegalArgumentException.class, () -> salida("ENT_FAC_00 1.txt")));
        verificar("Rechazo PEDIDO en SALIDA", () -> esperado(
                IllegalArgumentException.class, () -> salida("SAL_PED_001.txt")));
        verificar("Rechazo PEDIDO en MIXTA", () -> esperado(
                IllegalArgumentException.class, () -> salida("MIX_PED_001.txt")));
        verificar("Sin registro de fábrica MIXTA", () -> esperado(
                IllegalArgumentException.class,
                () -> salidaCon(procesador(false), "MIX_FAC_001.txt")));
        verificar("Procesar lista vacía no genera salida", () -> igual("", salida()));
        verificar("Lista nula", () -> esperado(
                NullPointerException.class,
                () -> procesador(true).procesarInformes(null, System.out)));
        verificar("Nombre nulo", () -> esperado(
                NullPointerException.class, () -> salida((String) null)));
        verificar("Registro de fábricas nulo", () -> esperado(
                NullPointerException.class, () -> new ProcesadorInformes(null)));
        verificar("Canal de salida nulo", () -> esperado(
                NullPointerException.class,
                () -> procesador(true).procesarInformes(new String[0], null)));

        verificar("Las fábricas devuelven productos concretos correctos", () -> {
            FabricaInformes fabrica = new FabricaEntrada();
            if (!(fabrica.crearFactura("ENT_FAC_1.txt") instanceof EntradaFactura)) {
                throw new AssertionError("Factura incorrecta");
            }
            if (!(fabrica.crearCompra("ENT_COM_1.txt") instanceof EntradaCompra)) {
                throw new AssertionError("Compra incorrecta");
            }
        });
        verificar("Las fábricas conservan metadatos correctos", () -> {
            Factura factura = new FabricaEntrada().crearFactura("ENT_FAC_7.txt");
            igual(Categoria.ENTRADA, factura.categoria());
            igual(Tipo.FACTURA, factura.tipo());
            igual("ENT_FAC_7.txt", factura.nombreArchivo());
        });
        verificar("Solo ENTRADA implementa capacidad PEDIDO", () -> {
            if (!(new FabricaEntrada() instanceof FabricaPedidos)) {
                throw new AssertionError("ENTRADA no ofrece pedidos");
            }
            FabricaInformes salida = new FabricaSalida();
            FabricaInformes mixta = new FabricaMixta();
            if (salida instanceof FabricaPedidos || mixta instanceof FabricaPedidos) {
                throw new AssertionError("Se admitió PEDIDO en otra familia");
            }
        });
        verificar("El registro del procesador se copia defensivamente", () -> {
            Map<Categoria, FabricaInformes> registro = new EnumMap<>(Categoria.class);
            registro.put(Categoria.ENTRADA, new FabricaEntrada());
            ProcesadorInformes cliente = new ProcesadorInformes(registro);
            registro.clear();
            igual("Procesando informe de ENTRADA de tipo COMPRA\n",
                    salidaCon(cliente, "ENT_COM_11.txt"));
        });
        verificar("La ejecución se detiene ante el primer error", () -> {
            ByteArrayOutputStream memoria = new ByteArrayOutputStream();
            try (PrintStream flujo = new PrintStream(memoria, true, StandardCharsets.UTF_8)) {
                esperado(IllegalArgumentException.class, () -> procesador(true).procesarInformes(
                        new String[] {"ENT_FAC_01.txt", "SAL_PED_02.txt", "SAL_COM_03.txt"},
                        flujo));
            }
            igual("Procesando informe de ENTRADA de tipo FACTURA\n",
                    normalizar(memoria.toString(StandardCharsets.UTF_8)));
        });
        verificar("main: procesa exactamente siete informes", () -> {
            String resultado = capturarMain();
            String[] lineas = resultado.strip().split("\\R");
            igual(7, lineas.length);
            igual("Procesando informe de ENTRADA de tipo FACTURA", lineas[0]);
            igual("Procesando informe de ENTRADA de tipo PEDIDO", lineas[6]);
        });
        verificar("main: opción --etapas incluye los tres estados", () -> {
            String resultado = capturarMain("--etapas");
            if (!resultado.contains("ETAPA 1: MODELO INICIAL")
                    || !resultado.contains("ETAPA 2: NUEVA CATEGORÍA MIXTA")
                    || !resultado.contains("ETAPA 3: NUEVO TIPO PEDIDO")) {
                throw new AssertionError("No se encontraron las tres etapas");
            }
        });
        verificar("Se puede inyectar una fábrica alternativa", () -> {
            // Prueba de sustitución real, sin afirmar cobertura estática del código.
            final int[] llamadas = {0};
            FabricaInformes doble = new FabricaInformes() {
                @Override public Factura crearFactura(String nombre) {
                    llamadas[0]++;
                    return new FabricaEntrada().crearFactura(nombre);
                }
                @Override public Compra crearCompra(String nombre) {
                    llamadas[0]++;
                    return new FabricaEntrada().crearCompra(nombre);
                }
            };
            Map<Categoria, FabricaInformes> registro = new EnumMap<>(Categoria.class);
            registro.put(Categoria.ENTRADA, doble);
            salidaCon(new ProcesadorInformes(registro), "ENT_FAC_1.txt", "ENT_COM_1.txt");
            igual(2, llamadas[0]);
        });

        System.out.println("RESULTADO: " + aprobadas + " aprobadas; " + fallidas + " fallidas");
        if (fallidas > 0) {
            System.exit(1);
        }
    }
}
