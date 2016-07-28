package hr.aportolan.kps.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;

import hr.aportolan.kps.provisioning.ws.client.NotifyProvisioningStateRequest;

public interface ErrorRespoderService {

	Message<NotifyProvisioningStateRequest> respondToAsyncError(ErrorMessage message);

	Message<hr.aportolan.kps.provisioning.ws.MessageFault> respondToError(Message<Throwable> message);

	Message<NotifyProvisioningStateRequest> respondToStandardAsyncError(Message<Throwable> message);

}
