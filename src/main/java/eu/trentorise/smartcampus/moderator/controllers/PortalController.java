package eu.trentorise.smartcampus.moderator.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.trentorise.smartcampus.aac.AACException;
import eu.trentorise.smartcampus.aac.AACService;
import eu.trentorise.smartcampus.network.JsonUtils;
import eu.trentorise.smartcampus.profileservice.BasicProfileService;
import eu.trentorise.smartcampus.profileservice.ProfileServiceException;
import eu.trentorise.smartcampus.profileservice.model.BasicProfile;
import eu.trentorise.smartcampus.resourceprovider.controller.SCController;
import eu.trentorise.smartcampus.resourceprovider.model.AuthServices;

@Controller
public class PortalController extends SCController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	@Value("${smartcampus.mediator.url}")
	private String mainURL;

	@Autowired
	@Value("${aacURL}")
	private String aacURL;

	@Autowired
	@Value("${smartcampus.client.id}")
	private String client_id;

	@Autowired
	@Value("${smartcampus.client.secret}")
	private String client_secret;

	@Autowired
	private AuthServices services;

	// //
	// // @RequestMapping(method = RequestMethod.GET, value = "/")
	// // public String index(HttpServletRequest request) {
	// //
	// // return "web/index";
	// // }
	//
	// @RequestMapping(method = RequestMethod.GET, value = "/web")
	// public String web(HttpServletRequest request) {
	//
	// return "index";
	// }
	//
	// @RequestMapping(method = RequestMethod.GET, value = "/web/login")
	// public String login(HttpServletRequest request) {
	//
	// return "index";
	// }
	//
	// @RequestMapping(method = RequestMethod.GET, value = "/exit")
	// public String exit(HttpServletRequest request) {
	//
	// return "exit";
	// }
	//

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ModelAndView web(HttpServletRequest request) {

		return new ModelAndView("redirect:/web");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/web")
	public ModelAndView index(HttpServletRequest request) throws SecurityException, ProfileServiceException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("token", getToken(request));
		model.put("appsFromDb", getApps(request));
		return new ModelAndView("index", model);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/check")
	public ModelAndView securePage(HttpServletRequest request,
			@RequestParam(required = false) String code)
			throws SecurityException, AACException {
		String redirectUri = mainURL + "/check";
		String userToken = aacService.exchngeCodeForToken(code, redirectUri)
				.getAccess_token();
		List<GrantedAuthority> list = Collections
				.<GrantedAuthority> singletonList(new SimpleGrantedAuthority(
						"ROLE_USER"));
		Authentication auth = new PreAuthenticatedAuthenticationToken(
				userToken, "", list);
		auth = authenticationManager.authenticate(auth);
		SecurityContextHolder.getContext().setAuthentication(auth);
		request.getSession()
				.setAttribute(
						HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
						SecurityContextHolder.getContext());
		request.getSession().setAttribute("token", userToken);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public ModelAndView secure(HttpServletRequest request) {
		String redirectUri = mainURL + "/check";
		return new ModelAndView(
				"redirect:"
						+ aacService
								.generateAuthorizationURIForCodeFlow(
										redirectUri,
										null,
										"smartcampus.profile.basicprofile.me,smartcampus.profile.accountprofile.me",
										null));
	}

	@Override
	protected AuthServices getAuthServices() {
		return services;
	}

	protected String getToken(HttpServletRequest request) {

		// String fromCtx = (String)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String fromCtx = (String) request.getSession().getAttribute("token");

		System.err.println("TOKEN: " + fromCtx);
		return fromCtx;
	}

	protected AACService aacService;
	protected BasicProfileService profileService;

	@PostConstruct
	public void init() {
		aacService = new AACService(aacURL, client_id, client_secret);
		profileService = new BasicProfileService(aacURL);
	}
	
	
	private String getApps(HttpServletRequest request) throws SecurityException, ProfileServiceException{
		BasicProfile x=profileService.getBasicProfile(getToken(request));
		List<String> lstApps=services.loadAppByUserId(x.getUserId());		
		return JsonUtils.toJSON(lstApps);
	}

}
