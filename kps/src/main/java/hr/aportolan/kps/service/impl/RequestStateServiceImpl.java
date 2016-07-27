package hr.aportolan.kps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import hr.aportolan.kps.dao.RequestRepository;
import hr.aportolan.kps.entity.Requests;
import hr.aportolan.kps.provisioning.ws.CancelProvisioningRequest;
import hr.aportolan.kps.provisioning.ws.CancelProvisioningResponse;
import hr.aportolan.kps.provisioning.ws.ProvisioningState;
import hr.aportolan.kps.provisioning.ws.ProvisioningStateRequest;
import hr.aportolan.kps.provisioning.ws.ProvisioningStateResponse;
import hr.aportolan.kps.service.RequestStateService;

@Service
public class RequestStateServiceImpl implements RequestStateService {

	@Autowired
	private RequestRepository requestRepository;
	@Value("#{controlBusChannel}")
	private MessageChannel controlBusChannel;

	@Override
	public Message<ProvisioningStateResponse> returnState(Message<ProvisioningStateRequest> message) {
		Requests request = requestRepository.findOne(message.getPayload().getRequestId());
		ProvisioningStateResponse provisioningStateResponseType = new ProvisioningStateResponse();

		if (request != null)
			provisioningStateResponseType.setState(request.getProvisioningState());
		else
			provisioningStateResponseType.setState(ProvisioningState.ERROR);

		Message<ProvisioningStateResponse> messageResponse = MessageBuilder.withPayload(provisioningStateResponseType)
				.build();
		return messageResponse;
	}

	@Override
	public Message<CancelProvisioningResponse> cancel(Message<CancelProvisioningRequest> message) {

		Requests request = requestRepository.findOne(message.getPayload().getRequestId());
		CancelProvisioningResponse cancelProvisioningResponse = new CancelProvisioningResponse();
		if (request != null && request.getProvisioningState() != null
				&& request.getProvisioningState().equals(ProvisioningState.PENDING)) {

			controlBusChannel.send(MessageBuilder
					.withPayload(
							"'@provisioningOutboundGateway.stop();@asyncExecutorProcessData.stop();provisioningAggregator.stop();'")
					.copyHeaders(message.getHeaders()).build());

			request.setProvisioningState(ProvisioningState.ERROR);
			requestRepository.save(request);

		}

		return MessageBuilder.withPayload(cancelProvisioningResponse).build();

	}

}
