package hr.aportolan.kps.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;

import hr.aportolan.kps.provisioning.ws.FaultMessage;

public interface ErrorRespoderService {

	Message<FaultMessage> respondToError(ErrorMessage message);

}
