package com.nequi.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nequi.app.interfaces.IProductoService;
import com.nequi.app.models.Producto;
import com.nequi.app.models.ProductoConSucursal;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	private IProductoService productoService;
	
	@GetMapping("/sucursal/{sucursalId}")
    public Flux<Producto> obtenerProductosPorSucursalId(@PathVariable String sucursalId) {
        return productoService.obtenerProductosPorSucursalId(sucursalId);
    }

	@GetMapping("/{id}")
    public Mono<ResponseEntity<Producto>> obtenerProductoPorId(@PathVariable String id) {
        return productoService.obtenerProductoPorId(id)
            .map(producto -> ResponseEntity.ok(producto))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }
	
	 @PostMapping("/{sucursalId}")
	 public Mono<ResponseEntity<Producto>> crearProducto(
	            @PathVariable String sucursalId,
	            @RequestBody Producto producto) {
	     	return productoService.crearProducto(sucursalId, producto)
	                .map(createdProducto -> ResponseEntity
	                        .status(HttpStatus.CREATED)
	                        .body(createdProducto))
	                .onErrorResume(e -> Mono.just(ResponseEntity
	                        .status(HttpStatus.BAD_REQUEST)
	                        .build()));
	}

	 
	 @PutMapping("/{sucursalId}/{productoId}")
	    public Mono<ResponseEntity<Producto>> actualizarProducto(
	            @PathVariable String sucursalId,
	            @PathVariable String productoId,
	            @RequestBody Producto producto) {
	        return productoService.actualizarProducto(sucursalId, productoId, producto)
	                .map(updatedProducto -> ResponseEntity
	                        .status(HttpStatus.OK)
	                        .body(updatedProducto))
	                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build())
	                .onErrorResume(e -> Mono.just(ResponseEntity
	                        .status(HttpStatus.BAD_REQUEST)
	                        .build()));
	    }

    @DeleteMapping("/{sucursalId}/{productoId}")
    public Mono<ResponseEntity<Object>> eliminarProducto(
            @PathVariable String sucursalId,
            @PathVariable String productoId) {
        return productoService.eliminarProducto(sucursalId, productoId)
                .then(Mono.just(ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .build()))
                .onErrorResume(e -> Mono.just(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build()));
    }
    
    @GetMapping("/mas-stock/{franquiciaId}")
    public Flux<ProductoConSucursal> obtenerProductoConMasStockPorSucursal(
            @PathVariable String franquiciaId) {
        return productoService.obtenerProductoConMasStockPorSucursal(franquiciaId);
    }
}
