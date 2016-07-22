package hr.aportolan.kps.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import hr.aportolan.kps.entity.Routes;

public interface RoutesRepository extends MongoRepository<Routes, String> {

}