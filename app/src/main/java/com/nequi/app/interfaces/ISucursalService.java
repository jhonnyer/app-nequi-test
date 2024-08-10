package com.nequi.app.interfaces;

import com.nequi.app.models.Sucursal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ISucursalService {
	
	Flux<Sucursal> obtenerSucursales(); 
	Flux<Sucursal> obtenerSucursalesPorIdFranquicia(String franquiciaId);
    Mono<Sucursal> obtenerSucursalPorId(String id);
    Mono<Sucursal> crearSucursal(String franquiciaId, Sucursal sucursal);
    Mono<Sucursal> actualizarSucursal(String id, Sucursal sucursal);
    Mono<Void> eliminarSucursal(String id);
}
