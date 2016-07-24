package hr.aportolan.kps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import hr.aportolan.kps.dao.RequestRepository;
import hr.aportolan.kps.entity.Requests;
import hr.aportolan.kps.provisioning.ws.ProvisioningStateRequestType;
import hr.aportolan.kps.provisioning.ws.ProvisioningStateResponseType;
import hr.aportolan.kps.service.RequestStateService;

@Service
public class RequestStateServiceImpl implements RequestStateService {

	@Autowired
	private RequestRepository requestRepository;

	@Override
	public Message<ProvisioningStateResponseType> returnState(Message<ProvisioningStateRequestType> message) {
		Requests request = requestRepository.findOne(message.getPayload().getRequestId());
		ProvisioningStateResponseType provisioningStateResponseType = new ProvisioningStateResponseType();

		provisioningStateResponseType.setState(request.getProvisioningState());

		Message<ProvisioningStateResponseType> messageResponse = MessageBuilder
				.withPayload(provisioningStateResponseType).build();
		return messageResponse;
	}

}
