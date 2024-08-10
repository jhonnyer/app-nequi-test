package com.nequi.app.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nequi.app.interfaces.ISucursalService;
import com.nequi.app.models.Sucursal;
import com.nequi.app.repository.FranquiciaRepository;
import com.nequi.app.repository.SucursalRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SucursalServiceImpl implements ISucursalService{
	@Autowired
    private SucursalRepository sucursalRepository;
	
	@Autowired
	private FranquiciaRepository franquiciaRepository;
	
	@Override
	public Flux<Sucursal> obtenerSucursales(){
		return sucursalRepository.findAll();
	} 

	@Override
	public Flux<Sucursal> obtenerSucursalesPorIdFranquicia(String franquiciaId) {
	    return franquiciaRepository.findById(franquiciaId)
	        .flatMapMany(franquicia -> 
	        {
	        	ObjectId objectId = new ObjectId(franquicia.getId());
	        	return sucursalRepository.findByFranquiciaId(objectId);
	        });
	}

    @Override
    public Mono<Sucursal> obtenerSucursalPorId(String id) {
        return sucursalRepository.findById(id);
    }

    @Override
    public Mono<Sucursal> crearSucursal(String franquiciaId, Sucursal sucursal) {
        return franquiciaRepository.findById(franquiciaId)
            .flatMap(franquicia -> {
                sucursal.setFranquiciaId(franquiciaId);
                return sucursalRepository.save(sucursal);
            })
            .switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")));
    }
    
    @Override
    public Mono<Sucursal> actualizarSucursal(String id, Sucursal sucursal) {
        return sucursalRepository.findById(id)
            .flatMap(sucursalExistente -> {
                sucursalExistente.setNombre(sucursal.getNombre());
                sucursalExistente.setDireccion(sucursal.getDireccion());
                return sucursalRepository.save(sucursalExistente);
            })
            .switchIfEmpty(Mono.error(new RuntimeException("Sucursal no encontrada")));
    }

    @Override
    public Mono<Void> eliminarSucursal(String id) {
        return sucursalRepository.deleteById(id);
    }
}
