# Diagramas UML - evolución secuencial

- **01_inicial:** factoría abstracta con familias ENTRADA y SALIDA y dos productos por familia.
- **02_mixta:** extiende el anterior agregando una tercera familia (MIXTA), sin alterar el contrato de fábrica abstracta.
- **03_pedido:** extiende el segundo incorporando PEDIDO solo en ENTRADA mediante interfaz de capacidad específica `FabricaPedidos`.

Los diagramas están disponibles como `.dot` (fuente editable), `.svg` (vectorial) y `.png` (evidencia). Se generaron con Graphviz. Las flechas triangulares discontinuas indican realización de interfaz; las discontinuas con punta normal, dependencia o creación. En la versión 03 el cliente selecciona la creación de PEDIDO tras verificar que la fábrica disponible implementa `FabricaPedidos`.

**Abstracciones omitidas para legibilidad:** `Informe` e `InformeBase` no se muestran en detalle; `Factura`, `Compra` y `Pedido` extienden `Informe`, y los siete productos concretos extienden `InformeBase`.
