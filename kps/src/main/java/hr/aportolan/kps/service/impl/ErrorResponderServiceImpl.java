package hr.aportolan.kps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;

import hr.aportolan.kps.dao.RequestRepository;
import hr.aportolan.kps.entity.Requests;
import hr.aportolan.kps.provisioning.enums.KpsConstants;
import hr.aportolan.kps.provisioning.ws.MessageFault;
import hr.aportolan.kps.provisioning.ws.ProvisioningState;
import hr.aportolan.kps.provisioning.ws.client.NotifyProvisioningStateRequest;
import hr.aportolan.kps.service.ErrorRespoderService;
import hr.aportolan.kps.util.KpsUtils;

public class ErrorResponderServiceImpl implements ErrorRespoderService {

	@Autowired
	private RequestRepository requestRepository;

	@Override
	public Message<MessageFault> respondToError(Message<Throwable> message) {

		if (message.getHeaders().containsKey(KpsConstants.PROVISIONING_REQUEST_ID.getValue())) {
			String requestId = message.getHeaders().get(KpsConstants.PROVISIONING_REQUEST_ID.getValue()).toString();

			Requests request = requestRepository.findOne(requestId);
			if (request != null) {
				request.setProvisioningState(ProvisioningState.ERROR);
				requestRepository.save(request);
			}
		}

		MessageFault fm = new MessageFault();
		fm.setErrorMessage(KpsUtils.getRootException(message.getPayload()).getMessage());

		return MessageBuilder.withPayload(fm).copyHeaders(message.getHeaders()).build();
	}

	@Override
	public Message<NotifyProvisioningStateRequest> respondToStandardAsyncError(Message<Throwable> message) {

		NotifyProvisioningStateRequest notifyProvisioningStateRequest = new NotifyProvisioningStateRequest();
		notifyProvisioningStateRequest.setState(ProvisioningState.ERROR);
		if (message.getHeaders().containsKey(KpsConstants.PROVISIONING_REQUEST_ID.getValue())) {
			notifyProvisioningStateRequest
					.setRequestId(message.getHeaders().get(KpsConstants.PROVISIONING_REQUEST_ID.getValue()).toString());
			String requestId = message.getHeaders().get(KpsConstants.PROVISIONING_REQUEST_ID.getValue()).toString();

			Requests request = requestRepository.findOne(requestId);
			if (request != null) {
				request.setProvisioningState(ProvisioningState.ERROR);
				requestRepository.save(request);
			}
		}

		return MessageBuilder.withPayload(notifyProvisioningStateRequest).copyHeaders(message.getHeaders()).build();

	}

	@Override
	public Message<NotifyProvisioningStateRequest> respondToAsyncError(ErrorMessage message) {
		String requestId;
		NotifyProvisioningStateRequest notifyProvisioningStateRequest = new NotifyProvisioningStateRequest();
		notifyProvisioningStateRequest.setState(ProvisioningState.ERROR);
		if (message.getHeaders().containsKey(KpsConstants.PROVISIONING_REQUEST_ID.getValue())) {
			notifyProvisioningStateRequest
					.setRequestId(message.getHeaders().get(KpsConstants.PROVISIONING_REQUEST_ID.getValue()).toString());
			requestId = message.getHeaders().get(KpsConstants.PROVISIONING_REQUEST_ID.getValue()).toString();

			Requests request = requestRepository.findOne(requestId);
			if (request != null) {
				request.setProvisioningState(ProvisioningState.ERROR);
				requestRepository.save(request);
			}
		}

		return MessageBuilder.withPayload(notifyProvisioningStateRequest).copyHeaders(message.getHeaders()).build();
	}

}
