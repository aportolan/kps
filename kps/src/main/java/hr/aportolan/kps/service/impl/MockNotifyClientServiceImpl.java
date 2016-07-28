package hr.aportolan.kps.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import hr.aportolan.kps.provisioning.ws.client.NotifyProvisioningStateRequest;
import hr.aportolan.kps.provisioning.ws.client.NotifyProvisioningStateResponse;

@Endpoint
public class MockNotifyClientServiceImpl {
	private static final Logger LOGGER = LoggerFactory.getLogger(MockNotifyClientServiceImpl.class);
	private static final String NAMESPACE_URI = "http://kps-aportolan.rhcloud.com/schema/ProvisioningNotificationSchema";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "NotifyProvisioningStateRequest")
	@ResponsePayload
	public NotifyProvisioningStateResponse notifyClient(@RequestPayload NotifyProvisioningStateRequest request) {
		NotifyProvisioningStateResponse response = new NotifyProvisioningStateResponse();

		System.out.println("--------- Dojavio klijentu!");
		LOGGER.debug("------------------------------------ Dojavio kijentu!");
		return response;
	}

}
