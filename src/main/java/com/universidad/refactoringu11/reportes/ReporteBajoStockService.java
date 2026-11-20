package com.universidad.refactoringu11.reportes;

import com.universidad.refactoringu11.inventario.InventarioService;
import com.universidad.refactoringu11.inventario.Producto;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteBajoStockService {

    private final InventarioService inventarioService;

    // @Lazy: InventarioService recibe este bean por setter; el proxy diferido rompe el ciclo de creacion de beans
    public ReporteBajoStockService(@Lazy InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    public String generarReporte() {
        List<Producto> bajoStock = inventarioService.productosBajoStockMinimo();
        if (bajoStock.isEmpty()) return "Reporte de bajo stock: sin productos bajo el minimo";
        return bajoStock.stream()
                .map(p -> "- " + p.getNombre() + ": stock " + p.getStock() + ", minimo " + p.getStockMinimo())
                .collect(Collectors.joining("\n", "Reporte de bajo stock:\n", ""));
    }
}
