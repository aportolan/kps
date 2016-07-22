package hr.aportolan.kps.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import hr.aportolan.kps.provisioning.notification.NotifyProvisioningStateRequestType;
import hr.aportolan.kps.provisioning.notification.NotifyProvisioningStateResponseType;
import hr.aportolan.kps.service.ProvisionWSClientService;

public class ProvisionWSClientServiceImpl extends WebServiceGatewaySupport implements ProvisionWSClientService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProvisionWSClientServiceImpl.class);

	@Override
	public NotifyProvisioningStateResponseType notifyProvisioningState(
			NotifyProvisioningStateRequestType notifyProvisioningStateRequestType) {
		LOGGER.info("Notifying client for request id: " + notifyProvisioningStateRequestType.getRequestId());
		NotifyProvisioningStateResponseType notifyProvisioningStateResponseType = (NotifyProvisioningStateResponseType) getWebServiceTemplate()
				.marshalSendAndReceive(notifyProvisioningStateRequestType);
		return notifyProvisioningStateResponseType;
	}
}
