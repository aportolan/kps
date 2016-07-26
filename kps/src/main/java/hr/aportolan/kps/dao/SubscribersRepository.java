package hr.aportolan.kps.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import hr.aportolan.kps.entity.Subscribers;

public interface SubscribersRepository extends MongoRepository<Subscribers, String> {

	Subscribers findByName(String subscriber);

}