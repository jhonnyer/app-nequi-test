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

import com.nequi.app.interfaces.IFranquiciaService;
import com.nequi.app.models.Franquicia;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/franquicias")
public class FranquiciaController {
	
	@Autowired
	private IFranquiciaService franquiciaService;
	
	@GetMapping
    public Flux<Franquicia> obtenerTodasFranquicias() {
        return franquiciaService.obtenerTodasFranquicias();
    }

	@GetMapping("/{id}")
    public Mono<ResponseEntity<Franquicia>> obtenerFranquiciaPorId(@PathVariable String id) {
        return franquiciaService.obtenerFranquiciaPorId(id)
            .map(franquicia -> ResponseEntity.ok(franquicia))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Franquicia>> crearFranquicia(@RequestBody Franquicia franquicia) {
        return franquiciaService.crearFranquicia(franquicia)
            .map(createdFranquicia -> ResponseEntity.status(HttpStatus.CREATED).body(createdFranquicia));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Franquicia>> actualizarFranquicia(
        @PathVariable String id,
        @RequestBody Franquicia franquicia
    ) {
        return franquiciaService.actualizarFranquicia(id, franquicia)
            .map(updatedFranquicia -> ResponseEntity.ok(updatedFranquicia))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> eliminarFranquicia(@PathVariable String id) {
        return franquiciaService.eliminarFranquicia(id)
            .then(Mono.just(ResponseEntity.noContent().build()))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
