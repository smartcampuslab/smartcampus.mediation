package eu.trentorise.smartcampus.moderator.controllers;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.trentorise.smartcampus.aac.AACException;
import eu.trentorise.smartcampus.aac.AACService;
import eu.trentorise.smartcampus.moderator.model.ModeratorForApps;
import eu.trentorise.smartcampus.moderator.utils.EasyTokenManger;
import eu.trentorise.smartcampus.profileservice.BasicProfileService;
import eu.trentorise.smartcampus.profileservice.ProfileServiceException;
import eu.trentorise.smartcampus.profileservice.model.BasicProfile;
import eu.trentorise.smartcampus.resourceprovider.controller.SCController;
import eu.trentorise.smartcampus.resourceprovider.model.AuthServices;
import eu.trentorise.smartcampus.resourceprovider.model.ResourceParameter;

@Controller
public class ModeratorForAppsController extends SCController {

	private static final String MODERATOR_SERVICE_ID = "smartcampus.moderation";
	private static final String MODERATOR_RESOURCE_PARAMETER_ID = "app";

	@Autowired
	MongoTemplate db;

	@Autowired
	@Value("${aacExtURL}")
	private String aacExtURL;

	@Autowired
	@Value("${aacURL}")
	private String aacURL;

	@Autowired
	@Value("${platform.clientId}")
	private String client_id;

	@Autowired
	@Value("${platform.clientSecret}")
	private String client_secret;

	@Autowired
	private AuthServices services;

	protected AACService aacService;
	protected BasicProfileService profileService;

	@PostConstruct
	public void init() {
		aacService = new AACService(aacExtURL, client_id, client_secret);
		profileService = new BasicProfileService(aacURL);
	}

	@Override
	protected AuthServices getAuthServices() {
		return services;
	}

	protected String getToken(HttpServletRequest request) {

		// String fromCtx = (String)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String fromCtx = (String) request.getSession().getAttribute("token");

		return fromCtx;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/web/moderator/app/{app}/add")
	public @ResponseBody
	String addModerator(HttpServletRequest request, @PathVariable String app,
			@RequestBody List<HashMap> userIds) throws ProfileServiceException,
			AACException {
		BasicProfile ownerProfile = profileService
				.getBasicProfile(getToken(request));

		String clientToken = new EasyTokenManger(aacURL, client_id,
				client_secret).getClientSmartCampusToken();

		String clientIdOfApp = "";

		List<ResourceParameter> lstResourceParameters = services
				.loadResourceParameterByUserId(ownerProfile.getUserId());
		for (ResourceParameter rp : lstResourceParameters) {
			if (!MODERATOR_SERVICE_ID.equals(rp.getServiceId())
					&& !MODERATOR_RESOURCE_PARAMETER_ID.equals(rp
							.getParameter()))
				continue;
			if (rp.getValue().compareTo(app) == 0) {
				clientIdOfApp = rp.getClientId();
				break;
			}
		}

		ModeratorForApps newModeratorIstance = null;
		if (!userIds.isEmpty())
			for (int i = 0; i < userIds.size(); i++) {
				String userId = String.valueOf(userIds.get(i).get("userId"));
				Query insert = Query.query(Criteria.where("userId").is(userId)
						.and("appId").is(app));
				newModeratorIstance = db
						.findOne(insert, ModeratorForApps.class);
				if (newModeratorIstance == null) {
					BasicProfile profileToAdd = profileService.getBasicProfile(
							userId, clientToken);
					newModeratorIstance = new ModeratorForApps(profileToAdd,
							app, ownerProfile.getUserId(), clientIdOfApp);
				} else {
					// if was moderator for this app,update the parent and the
					// time
					newModeratorIstance.setParentUserId(ownerProfile
							.getUserId());
				}

				db.save(newModeratorIstance);

			}

		return "true";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/web/moderator/app/{app}/all")
	public @ResponseBody
	List<ModeratorForApps> getAllModerator(HttpServletRequest request,
			@PathVariable String app) throws SecurityException {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("appId").is(app));

		List<ModeratorForApps> list = db.find(query2, ModeratorForApps.class);
		System.out.println(list.size());

		return list;

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/web/moderator/app/{app:.*}")
	public @ResponseBody
	ModeratorForApps updateModerator(HttpServletRequest request,
			@PathVariable String app,
			@RequestBody ModeratorForApps moderatorToUpdate)
			throws SecurityException {

		Query insert = new Query();
		insert.addCriteria(Criteria.where("userId").is(
				moderatorToUpdate.getUserId()));
		insert.addCriteria(Criteria.where("appId").is(
				moderatorToUpdate.getAppId()));
		ModeratorForApps newModeratorIstance = db.findOne(insert,
				ModeratorForApps.class);
		if (newModeratorIstance == null) {
			return null;
		} else {
			db.remove(newModeratorIstance);
		}

		db.save(moderatorToUpdate);

		return db.findOne(insert, ModeratorForApps.class);

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/web/moderator/app/{app}/{id}")
	public @ResponseBody
	String deleteModerator(HttpServletRequest request,
			@PathVariable String app, @PathVariable String id)
			throws SecurityException {

		db.remove(db.findById(id, ModeratorForApps.class));

		return "true";

	}

}
