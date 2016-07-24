package hr.aportolan.kps.mock.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/provisioning")
public class MockProvisioningSystem {

	@RequestMapping(value = "/subscribers/{subscriber}", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
	public void insert() {

	}

	@RequestMapping(value = "/subscribers/{subscriber}", method = RequestMethod.PUT, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
	public void modify() {

	}

	@RequestMapping(value = "/subscribers/{subscriber}", method = RequestMethod.DELETE, consumes =

	{ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })

	public void delete() {

	}

	@RequestMapping(value = "/subscribers/{subscriber}", method = RequestMethod.GET, consumes =

	{ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })

	public void find() {

	}
}
