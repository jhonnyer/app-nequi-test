package com.nequi.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nequi.app.interfaces.IFranquiciaService;
import com.nequi.app.models.Franquicia;
import com.nequi.app.repository.FranquiciaRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FranquiciaServiceImpl implements IFranquiciaService {
	
	@Autowired
    private FranquiciaRepository franquiciaRepository;

    @Override
    public Flux<Franquicia> obtenerTodasFranquicias() {
        return franquiciaRepository.findAll();
    }

    @Override
    public Mono<Franquicia> obtenerFranquiciaPorId(String id) {
        return franquiciaRepository.findById(id);
    }

    @Override
    public Mono<Franquicia> crearFranquicia(Franquicia franquicia) {
        return franquiciaRepository.save(franquicia);
    }
    
    @Override
    public Mono<Franquicia> actualizarFranquicia(String id, Franquicia franquicia) {
        return franquiciaRepository.findById(id)
            .flatMap(existingFranquicia -> {
                existingFranquicia.setNombre(franquicia.getNombre());
                existingFranquicia.setDireccion(franquicia.getDireccion());
                return franquiciaRepository.save(existingFranquicia);
            })
            .switchIfEmpty(Mono.error(new Exception("Franquicia no encontrada con el id: " + id)));
    }

    @Override
    public Mono<Void> eliminarFranquicia(String id) {
        return franquiciaRepository.deleteById(id);
    }
}
