package eu.trentorise.smartcampus.moderator.controllers;

import java.lang.ProcessBuilder.Redirect;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PortalController {

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String index(HttpServletRequest request) {

		return "web/index";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/web")
	public String web(HttpServletRequest request) {

		return "index";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/web/login")
	public String login(HttpServletRequest request) {

		return "index";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/exit")
	public String exit(HttpServletRequest request) {

		return "exit";
	}

}
