package hr.aportolan.kps.service;

import org.springframework.messaging.Message;

import hr.aportolan.kps.provisioning.ws.CancelProvisioningRequest;
import hr.aportolan.kps.provisioning.ws.CancelProvisioningResponse;
import hr.aportolan.kps.provisioning.ws.ProvisioningStateRequest;
import hr.aportolan.kps.provisioning.ws.ProvisioningStateResponse;

public interface RequestStateService {
	Message<ProvisioningStateResponse> returnState(Message<ProvisioningStateRequest> message);

	Message<CancelProvisioningResponse> cancel(Message<CancelProvisioningRequest> message);
}
