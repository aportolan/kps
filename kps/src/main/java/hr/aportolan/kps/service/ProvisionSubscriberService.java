package hr.aportolan.kps.service;

import org.springframework.messaging.Message;

import hr.aportolan.kps.provisioning.ws.ProvisionSubscriberRequestType;
import hr.aportolan.kps.provisioning.ws.ProvisionSubscriberResponseType;

public interface ProvisionSubscriberService {

	Message<ProvisionSubscriberRequestType> log(Message<ProvisionSubscriberRequestType> message);

	Message<ProvisionSubscriberResponseType> transformToWsOutputImediate(
			Message<ProvisionSubscriberRequestType> message);

}
