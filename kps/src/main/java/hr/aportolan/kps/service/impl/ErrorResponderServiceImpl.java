package hr.aportolan.kps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;

import hr.aportolan.kps.dao.RequestRepository;
import hr.aportolan.kps.entity.Requests;
import hr.aportolan.kps.provisioning.enums.KpsConstants;
import hr.aportolan.kps.provisioning.ws.FaultMessage;
import hr.aportolan.kps.provisioning.ws.ProvisioningState;
import hr.aportolan.kps.service.ErrorRespoderService;
import hr.aportolan.kps.util.KpsUtils;

public class ErrorResponderServiceImpl implements ErrorRespoderService {

	@Autowired
	private RequestRepository requestRepository;

	@Override
	public Message<FaultMessage> respondToError(ErrorMessage message) {

		String requestId = message.getHeaders().get(KpsConstants.PROVISIONING_REQUEST_ID.getValue()).toString();

		Requests request = requestRepository.findOne(requestId);
		if (request != null) {
			request.setProvisioningState(ProvisioningState.ERROR);
			requestRepository.save(request);
		}
		FaultMessage fm = new FaultMessage();
		fm.setErrorMessage(KpsUtils.getRootException(message.getPayload()).getMessage());
		return MessageBuilder.withPayload(fm).build();
	}

}
