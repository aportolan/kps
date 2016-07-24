package hr.aportolan.kps.service;

import hr.aportolan.kps.provisioning.ws.NotifyProvisioningStateRequestType;
import hr.aportolan.kps.provisioning.ws.NotifyProvisioningStateResponseType;

public interface ProvisionWSClientService {

	NotifyProvisioningStateResponseType notifyProvisioningState(
			NotifyProvisioningStateRequestType notifyProvisioningStateResponse);

}
