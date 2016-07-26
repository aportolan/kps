package hr.aportolan.kps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import hr.aportolan.kps.dao.RequestRepository;
import hr.aportolan.kps.entity.Requests;
import hr.aportolan.kps.provisioning.enums.KpsConstants;
import hr.aportolan.kps.provisioning.ws.ProvisionSubscriberRequest;
import hr.aportolan.kps.provisioning.ws.ProvisionSubscriberResponse;
import hr.aportolan.kps.provisioning.ws.ProvisioningState;
import hr.aportolan.kps.service.ProvisionSubscriberService;

@Service
public class ProvisionSubscriberServiceImpl implements ProvisionSubscriberService {

	@Autowired
	private RequestRepository requestRepository;

	@Override
	public Message<ProvisionSubscriberRequest> log(Message<ProvisionSubscriberRequest> message) {
		Requests req = new Requests();
		req.setProvisioningState(ProvisioningState.PENDING);
		req = requestRepository.save(req);

		return MessageBuilder.fromMessage(message)
				.setHeaderIfAbsent(KpsConstants.PROVISIONING_REQUEST_ID.toString(), req.getId()).build();
	}

	@Override
	public Message<ProvisionSubscriberResponse> transformToWsOutputImediate(
			Message<ProvisionSubscriberRequest> message) {
		Requests req = new Requests();
		req.setProvisioningState(ProvisioningState.PENDING);
		req = requestRepository.save(req);
		ProvisionSubscriberResponse provisionSubscriberResponseType = new ProvisionSubscriberResponse();
		provisionSubscriberResponseType.setRequestId(req.getId());
		Message<ProvisionSubscriberResponse> messageResponse = MessageBuilder
				.withPayload(provisionSubscriberResponseType).build();

		return messageResponse;
	}

}
