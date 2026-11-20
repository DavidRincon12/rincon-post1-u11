# Arquitectura de refactoring-u11

## Contexto
Laboratorio académico: un servicio de inventario en memoria (sin web ni BD) y un módulo de reportes de bajo stock.

## Contenedores
```mermaid
flowchart LR
  reportes["reportes<br/>ReporteBajoStockService"] -->|usa InventarioService<br/>implementa NotificadorBajoStock| inventario["inventario<br/>dominio"]
```

## Estilo y módulos
Monolito Spring Boot con paquetes por funcionalidad.
- `inventario` (dominio): no depende de ningún otro paquete del proyecto. Expone el puerto `NotificadorBajoStock`.
- `reportes`: depende de `inventario` e implementa su puerto.
- Regla: paquetes sin ciclos → `ArquitecturaPaquetesTest.losPaquetesNoDebenTenerDependenciasCiclicas`.

## Modelo de datos
`Producto` (id, nombre, `TipoProducto`, precio, stock, stockMinimo). `TipoProducto` es un enum cerrado con comportamiento (recargo y etiqueta).

## Flujos clave
1. Movimiento: `actualizarInventario` → valida → busca → nuevo stock → registra → si queda bajo el mínimo, calcula el reorden y llama a `NotificadorBajoStock.notificar`.

## Atributos de calidad
Mantenibilidad y testabilidad por encima de todo (fin didáctico).

## Riesgos y deuda técnica
- Precio en `double`: impreciso para dinero · migrar a `BigDecimal` si se usa fuera del laboratorio (cambiaría firmas fijadas por el enunciado).
- Estado en listas en memoria no concurrentes en beans singleton · aceptable en el laboratorio; usar persistencia o colecciones concurrentes si hay concurrencia real.
- Ciclo de beans `InventarioService` ↔ `ReporteBajoStockService` resuelto con `@Lazy` · separar el reporte del notificador si crece.
