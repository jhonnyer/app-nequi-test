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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nequi.app.interfaces.ISucursalService;
import com.nequi.app.models.Sucursal;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sucursales")
public class SucursalController {
	
	@Autowired
	private ISucursalService sucursalService;
    
	@GetMapping()
    public Flux<Sucursal> obtenerSucursales() {
        return sucursalService.obtenerSucursales();
    }
	
	@GetMapping("/{id}")
    public Mono<ResponseEntity<Sucursal>> obtenerSucursalPorId(@PathVariable String id) {
        return sucursalService.obtenerSucursalPorId(id)
            .map(sucursal -> ResponseEntity.ok(sucursal))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/franquicia/{franquiciaID}")
    public Flux<Sucursal> obtenerSucursalesPorIdFranquicia(@PathVariable String franquiciaID) {
        return sucursalService.obtenerSucursalesPorIdFranquicia(franquiciaID);
    }

    @PostMapping
    public Mono<ResponseEntity<Sucursal>> crearSucursal(
        @RequestParam String franquiciaId,
        @RequestBody Sucursal sucursal
    ) {
        return sucursalService.crearSucursal(franquiciaId, sucursal)
            .map(createdSucursal -> ResponseEntity.status(HttpStatus.CREATED).body(createdSucursal));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Sucursal>> actualizarSucursal(
        @PathVariable String id,
        @RequestBody Sucursal sucursal
    ) {
        return sucursalService.actualizarSucursal(id, sucursal)
            .map(updatedSucursal -> ResponseEntity.ok(updatedSucursal))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> eliminarSucursal(@PathVariable String id) {
        return sucursalService.eliminarSucursal(id)
            .then(Mono.just(ResponseEntity.noContent().build()))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
