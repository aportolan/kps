package hr.aportolan.kps.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import hr.aportolan.kps.entity.Requests;

public interface RequestRepository extends MongoRepository<Requests, String> {

}