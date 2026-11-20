# 0001. Invertir la dependencia entre inventario y reportes

- Fecha: 2026-11-20
- Estado: Aceptada

## Contexto
`InventarioService` importaba `ReporteBajoStockService` y este importaba a `InventarioService`: el ciclo de paquetes `inventario ↔ reportes` lo detectó `ArquitecturaPaquetesTest`.

## Decisión
`inventario` define el puerto `NotificadorBajoStock.notificar(Producto, int)`; `reportes` lo implementa. Notificación por evento individual. Detalle y justificación: Decisiones de diseño 3 y 4 en el README.

## Alternativas consideradas
- Interfaz en `reportes`: no rompe el ciclo · descartada.
- Notificación por lote (`List<Producto>`): exige acumular estado y pierde la cantidad de reorden · descartada por ahora.

## Consecuencias
Dominio libre de dependencias hacia afuera; queda un ciclo de beans en tiempo de ejecución resuelto con `@Lazy`.

## Cuándo revisar
- Si aparecen cargas masivas o un canal con límite de llamadas: evaluar notificación por lote.
