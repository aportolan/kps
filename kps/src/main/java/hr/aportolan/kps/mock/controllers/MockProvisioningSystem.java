package hr.aportolan.kps.mock.controllers;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import hr.aportolan.kps.provisioning.rest.dto.Subscriber;
import hr.aportolan.kps.service.impl.MockNotifyClientServiceImpl;
import hr.aportolan.kps.util.KpsUtils;

@Controller
@RequestMapping("/provisioning")
public class MockProvisioningSystem {
	private static final Logger LOGGER = LoggerFactory.getLogger(MockNotifyClientServiceImpl.class);

	@RequestMapping(value = "/subscribers/{subscriber}", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
	public void insert(@PathVariable String subscriber, @RequestBody Subscriber request) {
		generateRandomException(subscriber);
		LOGGER.debug("INSERT");
	}

	@RequestMapping(value = "/subscribers/{subscriber}", method = RequestMethod.PUT, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
	public void modify(@PathVariable String subscriber, @RequestBody Subscriber request) {
		generateRandomException(subscriber);
		LOGGER.debug("UPDATE");
	}

	@RequestMapping(value = "/subscribers/{subscriber}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String subscriber) {
		generateRandomException(subscriber);
		LOGGER.debug("DELETE");
	}

	@RequestMapping(value = "/subscribers/{subscriber}", method = RequestMethod.GET)
	public @ResponseBody Subscriber find(@PathVariable String subscriber) {
		generateRandomException(subscriber);
		LOGGER.debug("INSERT");
		return new Subscriber("Subscriber", "IN", "Route1");
	}

	void generateRandomException(String subscriber) {
		int numericPart = Integer.parseInt(subscriber.replace("subscriber", ""));

		int margin1 = new Random().nextInt(10000);
		int margin2 = new Random().nextInt(10000);
		int tmp;

		if (margin1 > margin2) {
			tmp = margin2;
			margin1 = margin2;
			margin2 = tmp;
		}

		if (margin1 < numericPart && numericPart < margin2)
			throw new RuntimeException("Error!");

	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public Error handleException(HttpServletRequest request, Exception e) {

		return new Error(Integer.toString(HttpStatus.BAD_REQUEST.value()), KpsUtils.getRootException(e));
	}
}
