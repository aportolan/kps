package hr.aportolan.kps.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import hr.aportolan.kps.dao.ProvisioningSystemRepository;
import hr.aportolan.kps.dao.ProvisioningSystemRequestRepository;
import hr.aportolan.kps.dao.RoutesRepository;
import hr.aportolan.kps.dao.SubscribersRepository;
import hr.aportolan.kps.entity.ProvisioningSystem;
import hr.aportolan.kps.entity.ProvisioningSystemRequest;
import hr.aportolan.kps.entity.Requests;
import hr.aportolan.kps.entity.Routes;
import hr.aportolan.kps.entity.Subscribers;
import hr.aportolan.kps.provisioning.enums.HttpOperation;
import hr.aportolan.kps.provisioning.enums.KpsConstants;
import hr.aportolan.kps.provisioning.rest.dto.Subscriber;
import hr.aportolan.kps.provisioning.ws.ProvisionSubscriberRequest;

public class ProvisionSplitterServiceImpl {

	@Autowired
	private ProvisioningSystemRepository provisioningSystemRepository;
	@Autowired
	private SubscribersRepository subscribersRepository;
	@Autowired
	private ProvisioningSystemRequestRepository provisioningSystemRequestRepository;
	@Autowired
	private RoutesRepository routesRepository;

	private static final int LOCAL_ROUTE = 1;

	public List<Message<Subscriber>> split(Message<ProvisionSubscriberRequest> request) {

		List<Message<Subscriber>> messages = new ArrayList<>();

		Subscribers subscriberDB = subscribersRepository.findOne(request.getPayload().getSubscriber());
		if (subscriberDB == null)
			subscriberDB = subscribersRepository.findByName(request.getPayload().getSubscriber());
		Routes route = routesRepository.findOne(request.getPayload().getRoute());
		if (route == null)
			route = routesRepository.findByRoute(request.getPayload().getRoute());
		if (subscriberDB.getParentRoute().equals(request.getPayload().getRoute()) || route.getLocal() == LOCAL_ROUTE)
			return messages;

		String correlationId = Long.toString(new Random().nextLong());
		List<ProvisioningSystem> provisioningSystems = provisioningSystemRepository.findAll();
		for (ProvisioningSystem provisioningSystem : provisioningSystems) {

			Subscriber subscriber = new Subscriber();

			Message<Subscriber> message = setHeaders(subscriber, route,
					request.getHeaders().get(KpsConstants.PROVISIONING_REQUEST_ID.getValue()).toString()).build();

			message = MessageBuilder.fromMessage(message)
					.setHeader(KpsConstants.PROVISIONING_SYSTEM_ENDPOINT_URL_HEADER.getValue(),
							"http://" + provisioningSystem.getIpAddress() + "/" + request.getPayload().getSubscriber())
					.setHeader(KpsConstants.CORRELATION_IDENTIFIER.getValue(), correlationId)
					.setHeader(KpsConstants.PROVISIONING_REQUEST_ID.getValue(),
							request.getHeaders().get(KpsConstants.PROVISIONING_REQUEST_ID.getValue()).toString())
					.build();

			messages.add(message);
			ProvisioningSystemRequest provisioningSystemRequest = new ProvisioningSystemRequest();
			provisioningSystemRequest.setProvisioningSystem(provisioningSystem);
			Requests requestz = new Requests();
			requestz.setId(request.getHeaders().get(KpsConstants.PROVISIONING_REQUEST_ID.getValue()).toString());
			provisioningSystemRequest.setRequest(requestz);
			provisioningSystemRequestRepository.save(provisioningSystemRequest);
		}
		return messages;
	}

	private MessageBuilder<Subscriber> setHeaders(Subscriber subscriber, Routes route, String subscriberId) {

		MessageBuilder<Subscriber> messageBuilder;
		subscriber.setRoute(route.getRoute());
		subscriber.setSubscriber(subscriberId);
		switch (route.getHttpOperation()) {
		case DELETE:
			subscriber.setType("OUT");
			messageBuilder = MessageBuilder.withPayload(subscriber).setHeader(KpsConstants.REQUEST_METHOD.getValue(),
					HttpOperation.DELETE.name());
			break;
		case POST:
			subscriber.setType("IN");
			messageBuilder = MessageBuilder.withPayload(subscriber)
					.setHeader(KpsConstants.REQUEST_METHOD.getValue(), HttpOperation.POST.name())
					.setHeader(HttpHeaders.CONTENT_TYPE,
							MediaType.APPLICATION_JSON_VALUE + ";" + StandardCharsets.UTF_8.displayName());
			break;
		case PUT:
			subscriber.setType("OTHER");
			messageBuilder = MessageBuilder.withPayload(subscriber)
					.setHeader(KpsConstants.REQUEST_METHOD.getValue(), HttpOperation.PUT.name())
					.setHeader(HttpHeaders.CONTENT_TYPE,
							MediaType.APPLICATION_JSON_VALUE + ";" + StandardCharsets.UTF_8.displayName());

			break;

		default:
			subscriber.setType("OTHER");
			messageBuilder = MessageBuilder.withPayload(subscriber)
					.setHeader(KpsConstants.REQUEST_METHOD.getValue(), HttpOperation.PUT.name())
					.setHeader(HttpHeaders.ACCEPT,
							MediaType.APPLICATION_JSON_VALUE + ";" + StandardCharsets.UTF_8.displayName());
		}

		return messageBuilder;
	}
}
