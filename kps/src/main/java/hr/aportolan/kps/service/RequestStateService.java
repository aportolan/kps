package hr.aportolan.kps.service;

import org.springframework.messaging.Message;

import hr.aportolan.kps.provisioning.ws.ProvisioningStateRequestType;
import hr.aportolan.kps.provisioning.ws.ProvisioningStateResponseType;

public interface RequestStateService {
	public Message<ProvisioningStateResponseType> returnState(Message<ProvisioningStateRequestType> message);
}
