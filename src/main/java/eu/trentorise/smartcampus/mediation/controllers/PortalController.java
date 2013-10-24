package eu.trentorise.smartcampus.mediation.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PortalController {

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String index(HttpServletRequest request) {

		return "index";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public String login(HttpServletRequest request) {

		return "index";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/exit")
	public String exit(HttpServletRequest request) {

		return "exit";
	}

}
