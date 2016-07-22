package hr.aportolan.kps.service;

import hr.aportolan.kps.provisioning.notification.NotifyProvisioningStateRequestType;
import hr.aportolan.kps.provisioning.notification.NotifyProvisioningStateResponseType;

public interface ProvisionWSClientService {

	NotifyProvisioningStateResponseType notifyProvisioningState(
			NotifyProvisioningStateRequestType notifyProvisioningStateResponse);

}
