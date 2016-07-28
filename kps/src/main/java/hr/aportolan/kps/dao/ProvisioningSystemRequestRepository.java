package hr.aportolan.kps.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import hr.aportolan.kps.entity.ProvisioningSystemRequest;

public interface ProvisioningSystemRequestRepository extends MongoRepository<ProvisioningSystemRequest, String> {

	long countByRequestId(String requestId);

}