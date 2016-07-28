package hr.aportolan.kps.service;

import org.springframework.messaging.Message;

import hr.aportolan.kps.provisioning.ws.ProvisionSubscriberRequest;
import hr.aportolan.kps.provisioning.ws.ProvisionSubscriberResponse;

public interface ProvisionSubscriberService {

	Message<ProvisionSubscriberRequest> log(Message<ProvisionSubscriberRequest> message);

	Message<ProvisionSubscriberResponse> transformToWsOutputImmediate(
			Message<ProvisionSubscriberRequest> message);

}
