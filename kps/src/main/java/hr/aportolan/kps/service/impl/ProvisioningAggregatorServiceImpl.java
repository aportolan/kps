package hr.aportolan.kps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.integration.annotation.ReleaseStrategy;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import hr.aportolan.kps.dao.ProvisioningSystemRepository;
import hr.aportolan.kps.provisioning.enums.KpsConstants;
import hr.aportolan.kps.provisioning.ws.client.NotifyProvisioningStateRequest;
import hr.aportolan.kps.service.ProvisioningAggregatorService;

public class ProvisioningAggregatorServiceImpl implements ProvisioningAggregatorService {

	@Autowired
	private ProvisioningSystemRepository provisioningSystemRepository;

	@ReleaseStrategy
	@Override
	public boolean release(List<Message<?>> messages) {

		return messages.size() == provisioningSystemRepository.count();
	}

	@CorrelationStrategy
	@Override
	public String correlateBy(Message<?> message) {

		return message.getHeaders().get(KpsConstants.CORRELATION_IDENTIFIER.getValue()).toString();

	}

	@Override
	public Message<NotifyProvisioningStateRequest> aggregate(List<Message<?>> messages) {
		NotifyProvisioningStateRequest notifyProvisioningStateRequestType = new NotifyProvisioningStateRequest();
		notifyProvisioningStateRequestType.setRequestId(
				messages.get(0).getHeaders().get(KpsConstants.PROVISIONING_REQUEST_ID.getValue()).toString());

		notifyProvisioningStateRequestType.setState(hr.aportolan.kps.provisioning.ws.ProvisioningState.COMPLETE);

		return MessageBuilder.withPayload(notifyProvisioningStateRequestType).build();

	}

}
