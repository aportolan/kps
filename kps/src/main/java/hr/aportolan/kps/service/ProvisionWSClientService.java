package hr.aportolan.kps.service;

import hr.aportolan.kps.provisioning.ws.NotifyProvisioningStateRequest;
import hr.aportolan.kps.provisioning.ws.NotifyProvisioningStateResponse;

public interface ProvisionWSClientService {

	NotifyProvisioningStateResponse notifyProvisioningState(
			NotifyProvisioningStateRequest notifyProvisioningStateResponse);

}
