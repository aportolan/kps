package hr.aportolan.kps.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import hr.aportolan.kps.dao.ProvisioningSystemRepository;
import hr.aportolan.kps.dao.RoutesRepository;
import hr.aportolan.kps.dao.SubscribersRepository;
import hr.aportolan.kps.entity.ProvisioningSystem;
import hr.aportolan.kps.entity.Routes;
import hr.aportolan.kps.entity.Subscribers;
import hr.aportolan.kps.provisioning.enums.KpsConstants;
import hr.aportolan.kps.provisioning.rest.dto.Subscriber;
import hr.aportolan.kps.provisioning.ws.ProvisionSubscriberRequestType;

public class ProvisionSplitterServiceImpl {
	private static final int LOCAL_ROUTE = 1;
	@Autowired
	private ProvisioningSystemRepository provisioningSystemRepository;
	@Autowired
	private SubscribersRepository subscribersRepository;
	@Autowired
	private RoutesRepository routesRepository;
	private static final String PROVISIONING = "/provisioning";

	public List<Message<Subscriber>> split(Message<ProvisionSubscriberRequestType> request) {

		List<Message<Subscriber>> messages = new ArrayList<>();

		Subscribers subscriberDB = subscribersRepository.findOne(request.getPayload().getSubscriber());
		Routes route = routesRepository.findOne(request.getPayload().getRoute());
		if (subscriberDB.getParentRoute().equals(request.getPayload().getRoute()) || route.getLocal() == LOCAL_ROUTE)
			return messages;

		String correlationId = Long.toString(new Random().nextLong());
		List<ProvisioningSystem> provisioningSystems = provisioningSystemRepository.findAll();
		for (ProvisioningSystem provisioningSystem : provisioningSystems) {

			Subscriber subscriber = new Subscriber(request.getPayload().getSubscriber(), "IN",
					request.getPayload().getRoute());
			Message<Subscriber> message = MessageBuilder.withPayload(subscriber)
					.setHeader(HttpHeaders.CONTENT_TYPE,
							MediaType.APPLICATION_JSON_VALUE + ";" + StandardCharsets.UTF_8.displayName())
					.setHeader(HttpHeaders.ACCEPT,
							MediaType.APPLICATION_JSON_VALUE + ";" + StandardCharsets.UTF_8.displayName())
					.setHeader(KpsConstants.PROVISIONING_SYSTEM_ENDPOINT_URL_HEADER.getValue(),
							provisioningSystem.getIpAddress() + PROVISIONING + request.getPayload().getSubscriber())
					.setHeader(KpsConstants.CORRELATION_IDENTIFIER.getValue(), correlationId)
					.setHeader(KpsConstants.REQUEST_METHOD.getValue(), HttpMethod.POST.name())
					.setHeader(KpsConstants.PROVISIONING_REQUEST_ID.getValue(),
							request.getHeaders().get(KpsConstants.PROVISIONING_REQUEST_ID.getValue()))
					.build();

			messages.add(message);
		}
		return messages;
	}
}
