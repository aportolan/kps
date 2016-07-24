package hr.aportolan.kps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import hr.aportolan.kps.dao.RequestRepository;
import hr.aportolan.kps.entity.Requests;
import hr.aportolan.kps.provisioning.enums.KpsConstants;
import hr.aportolan.kps.provisioning.ws.ProvisionSubscriberRequestType;
import hr.aportolan.kps.provisioning.ws.ProvisionSubscriberResponseType;
import hr.aportolan.kps.provisioning.ws.ProvisioningState;
import hr.aportolan.kps.service.ProvisionSubscriberService;

@Service
public class ProvisionSubscriberServiceImpl implements ProvisionSubscriberService {

	@Autowired
	private RequestRepository requestRepository;

	@Override
	public Message<ProvisionSubscriberRequestType> log(Message<ProvisionSubscriberRequestType> message) {
		Requests req = new Requests();
		req.setProvisioningState(ProvisioningState.PENDING);
		req = requestRepository.save(req);
		message.getHeaders().put(KpsConstants.PROVISIONING_REQUEST_ID.toString(), req.getId());
		return message;
	}

	@Override
	public Message<ProvisionSubscriberResponseType> transformToWsOutputImediate(
			Message<ProvisionSubscriberRequestType> message) {
		Requests req = new Requests();
		req.setProvisioningState(ProvisioningState.PENDING);
		req = requestRepository.save(req);
		ProvisionSubscriberResponseType provisionSubscriberResponseType = new ProvisionSubscriberResponseType();
		provisionSubscriberResponseType.setRequestId(req.getId());
		Message<ProvisionSubscriberResponseType> messageResponse = MessageBuilder
				.withPayload(provisionSubscriberResponseType).build();

		return messageResponse;
	}

}
