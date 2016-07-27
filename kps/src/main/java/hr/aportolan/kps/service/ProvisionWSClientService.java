package hr.aportolan.kps.service;

import hr.aportolan.kps.provisioning.ws.client.NotifyProvisioningStateRequest;
import hr.aportolan.kps.provisioning.ws.client.NotifyProvisioningStateResponse;

public interface ProvisionWSClientService {

	NotifyProvisioningStateResponse notifyProvisioningState(
			NotifyProvisioningStateRequest notifyProvisioningStateResponse);

}
