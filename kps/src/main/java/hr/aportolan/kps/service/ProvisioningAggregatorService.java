package hr.aportolan.kps.service;

import java.util.List;

import org.springframework.messaging.Message;

import hr.aportolan.kps.provisioning.ws.NotifyProvisioningStateRequest;

public interface ProvisioningAggregatorService {

	Message<NotifyProvisioningStateRequest> aggregate(List<Message<?>> messages);

	String correlateBy(Message<?> message);

	boolean release(List<Message<?>> messages);

}
