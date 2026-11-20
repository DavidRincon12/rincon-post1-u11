package com.universidad.refactoringu11.reportes;

import com.universidad.refactoringu11.inventario.InventarioService;
import com.universidad.refactoringu11.inventario.NotificadorBajoStock;
import com.universidad.refactoringu11.inventario.Producto;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteBajoStockService implements NotificadorBajoStock {

    private final InventarioService inventarioService;
    private final List<String> alertas = new ArrayList<>();

    // @Lazy: InventarioService recibe este bean por setter; el proxy diferido rompe el ciclo de creacion de beans
    public ReporteBajoStockService(@Lazy InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @Override
    public void notificar(Producto producto, int cantidadReorden) {
        alertas.add("- " + producto.getNombre() + ": reordenar " + cantidadReorden + " unidades");
    }

    public String generarReporte() {
        List<Producto> bajoStock = inventarioService.productosBajoStockMinimo();
        if (bajoStock.isEmpty()) return "Reporte de bajo stock: sin productos bajo el minimo";
        String productos = bajoStock.stream()
                .map(p -> "- " + p.getNombre() + ": stock " + p.getStock() + ", minimo " + p.getStockMinimo())
                .collect(Collectors.joining("\n", "Reporte de bajo stock:\n", ""));
        return alertas.isEmpty() ? productos : productos + "\nAlertas de reorden:\n" + String.join("\n", alertas);
    }
}
