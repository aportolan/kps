package hr.aportolan.kps.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import hr.aportolan.kps.entity.ProvisioningSystem;

public interface ProvisioningSystemRepository extends MongoRepository<ProvisioningSystem, String> {

}