package com.universidad.refactoringu11.inventario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InventarioNotificacionTest {

    private final List<String> notificaciones = new ArrayList<>();
    private InventarioService service;

    @BeforeEach
    void setUp() {
        service = new InventarioService();
        service.setNotificador((producto, cantidadReorden) ->
                notificaciones.add(producto.getNombre() + ":" + cantidadReorden));
        service.agregarProducto(new Producto(1L, "Clean Code", TipoProducto.LIBRO, 45.0, 20, 5));
    }

    @Test
    void salidaBajoMinimo_notificaUnaVezConLaCantidadDeReorden() {
        service.actualizarInventario(1L, 17, "SALIDA");
        assertEquals(List.of("Clean Code:12"), notificaciones);
    }

    @Test
    void movimientoSinBajoStock_noNotifica() {
        service.actualizarInventario(1L, 15, "SALIDA");
        assertTrue(notificaciones.isEmpty());
    }

    @Test
    void movimientoRechazado_noNotifica() {
        service.actualizarInventario(1L, 50, "SALIDA");
        assertTrue(notificaciones.isEmpty());
    }
}
