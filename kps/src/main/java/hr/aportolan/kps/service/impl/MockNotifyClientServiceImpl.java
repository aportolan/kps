package hr.aportolan.kps.service.impl;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import hr.aportolan.kps.provisioning.ws.NotifyProvisioningStateRequestType;
import hr.aportolan.kps.provisioning.ws.NotifyProvisioningStateResponseType;

@Endpoint
public class MockNotifyClientServiceImpl {
	private static final String NAMESPACE_URI = "http://aportolan.hr/kps/provisioning/ws";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "notifyProvisioningState")
	@ResponsePayload
	public NotifyProvisioningStateResponseType notifyClient(
			@RequestPayload NotifyProvisioningStateRequestType request) {
		NotifyProvisioningStateResponseType response = new NotifyProvisioningStateResponseType();

		return response;
	}
}
