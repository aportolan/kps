package hr.aportolan.kps.mock.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	@RequestMapping("/")
	private String mainEntry() {
		return "forward:views/index.html";
	}
}
