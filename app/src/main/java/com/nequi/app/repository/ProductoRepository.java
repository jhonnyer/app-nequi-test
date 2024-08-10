package com.nequi.app.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nequi.app.models.Producto;

import reactor.core.publisher.Flux;

@Repository
public interface ProductoRepository extends ReactiveMongoRepository<Producto, String>{
	Flux<Producto> findBySucursalId(ObjectId sucursalId);
	
	@Query("{ 'sucursalId': ?0 }")
    Flux<Producto> findBySucursalId(String sucursalId);
    
    @Query("{ 'franquiciaId': ?0 }")
    Flux<Producto> findByFranquiciaId(String franquiciaId);
}
