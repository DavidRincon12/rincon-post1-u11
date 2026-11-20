package com.universidad.refactoringu11.inventario;

/**
 * Puerto del dominio de inventario: quien quiera enterarse de un bajo stock lo implementa.
 * Vive en inventario para que la dependencia apunte siempre hacia el dominio.
 */
public interface NotificadorBajoStock {
    void notificar(Producto producto, int cantidadReorden);
}
