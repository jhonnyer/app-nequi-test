package com.nequi.app.interfaces;

import com.nequi.app.models.Franquicia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFranquiciaService {
	
	Flux<Franquicia> obtenerTodasFranquicias();
    Mono<Franquicia> obtenerFranquiciaPorId(String id);
    Mono<Franquicia> crearFranquicia(Franquicia franquicia);
    Mono<Franquicia> actualizarFranquicia(String id, Franquicia franquicia);
    Mono<Void> eliminarFranquicia(String id);
}
