package com.nequi.app.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nequi.app.models.Franquicia;

@Repository
public interface FranquiciaRepository extends ReactiveMongoRepository<Franquicia, String> {
}
