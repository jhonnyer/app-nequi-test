package com.nequi.app.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nequi.app.models.Sucursal;

import reactor.core.publisher.Flux;

@Repository
public interface SucursalRepository extends ReactiveMongoRepository<Sucursal, String> {
	Flux<Sucursal> findByFranquiciaId(ObjectId franquiciaId);
}
