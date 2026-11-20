package com.universidad.refactoringu11.reportes;

import com.universidad.refactoringu11.inventario.InventarioService;
import com.universidad.refactoringu11.inventario.Producto;
import com.universidad.refactoringu11.inventario.TipoProducto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReporteBajoStockServiceTest {

    private InventarioService inventario;
    private ReporteBajoStockService reporte;

    @BeforeEach
    void setUp() {
        inventario = new InventarioService();
        reporte = new ReporteBajoStockService(inventario);
    }

    @Test
    void generarReporte_sinProductosBajoMinimo_indicaQueNoHay() {
        inventario.agregarProducto(new Producto(1L, "Clean Code", TipoProducto.LIBRO, 45.0, 20, 5));
        assertEquals("Reporte de bajo stock: sin productos bajo el minimo", reporte.generarReporte());
    }

    @Test
    void generarReporte_listaSoloLosProductosBajoMinimo() {
        inventario.agregarProducto(new Producto(1L, "Clean Code", TipoProducto.LIBRO, 45.0, 3, 5));
        inventario.agregarProducto(new Producto(2L, "Teclado", TipoProducto.ELECTRONICO, 100.0, 10, 2));
        inventario.agregarProducto(new Producto(3L, "Cafe", TipoProducto.ALIMENTO, 15.0, 0, 4));
        assertEquals("""
                Reporte de bajo stock:
                - Clean Code: stock 3, minimo 5
                - Cafe: stock 0, minimo 4""", reporte.generarReporte());
    }
}
