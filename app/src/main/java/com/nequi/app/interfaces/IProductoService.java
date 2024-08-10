package com.nequi.app.interfaces;

import com.nequi.app.models.Producto;
import com.nequi.app.models.ProductoConSucursal;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductoService {
	
	Flux<Producto> obtenerProductosPorSucursalId(String sucursalId);
	Mono<Producto> obtenerProductoPorId(String id);
    Mono<Producto> crearProducto(String sucursalId, Producto producto);
    Mono<Producto> actualizarProducto(String sucursalId,String id, Producto producto);
    Mono<Void> eliminarProducto(String sucursalId, String productoId);
    Flux<ProductoConSucursal> obtenerProductoConMasStockPorSucursal(String franquiciaId);
}
