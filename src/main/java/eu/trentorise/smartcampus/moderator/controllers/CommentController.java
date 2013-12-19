/**
 *    Copyright 2012-2013 Trento RISE
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package eu.trentorise.smartcampus.moderator.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.trentorise.smartcampus.moderator.model.ContentToModeratorService;
import eu.trentorise.smartcampus.moderator.model.State;

@Controller
public class CommentController {

	@Autowired
	MongoTemplate db;

	@RequestMapping(method = RequestMethod.POST, value = "/rest/comment/app/{app}/add")
	public @ResponseBody
	boolean addCommento(HttpServletRequest request,
			@RequestBody String messageToMediationService,
			@PathVariable String app) {

		ContentToModeratorService mediationService = valueOf(messageToMediationService);

		db.save(mediationService);

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("objectId").is(
				mediationService.getObjectId()));
		return db.findOne(query2, ContentToModeratorService.class) != null;
	}

	private ContentToModeratorService valueOf(String json) {
		try {
			JSONObject o = new JSONObject(json);
			String webapp = o.getString("webappname");
			String entityTesto = o.getString("objectText");
			String userid = o.getString("userid");
			String entityId = o.getString("objectId");

			ContentToModeratorService messageToMediationService = new ContentToModeratorService(
					webapp, entityId, entityTesto, userid);
			messageToMediationService.setManualApproved(State.valueOf(o
					.getString("manualApproved")));
			messageToMediationService.setKeywordApproved(o
					.getBoolean("keywordApproved"));
			messageToMediationService.setTimestamp(o.getLong("timestamp"));
			if (o.has("note"))
				messageToMediationService.setNote(o.optString("note", null));

			return messageToMediationService;
		} catch (JSONException e) {
			return null;
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/comment/local/{app}/all")
	public @ResponseBody
	List<ContentToModeratorService> getCommentoPA(HttpServletRequest request,
			@PathVariable String app) {

		Query query2 = new Query();
		query2.sort().on("timestamp", Order.DESCENDING);
		query2.addCriteria(Criteria.where("manualApproved").is(
				State.NOT_REQUEST));
		query2.addCriteria(Criteria.where("webappname").regex(app));

		return db.find(query2, ContentToModeratorService.class);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/comment/remote/{app}/all")
	public @ResponseBody
	List<ContentToModeratorService> getCommentoMA(HttpServletRequest request,
			@PathVariable String app) {

		Query query2 = new Query();
		query2.sort().on("timestamp", Order.DESCENDING);
		query2.addCriteria(Criteria.where("manualApproved").ne(
				State.NOT_REQUEST));
		query2.addCriteria(Criteria.where("webappname").regex(app));

		return db.find(query2, ContentToModeratorService.class);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/rest/comment/{_id}/app/{app}/note/add")
	public @ResponseBody
	boolean addNote(HttpServletRequest request, @PathVariable String _id,
			@PathVariable String app, @RequestBody String note) {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("_id").is(_id));
		ContentToModeratorService mediationService = db.findOne(query2,
				ContentToModeratorService.class);

		mediationService.setNote(note);

		db.save(mediationService);

		return true;

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/rest/comment/{_id}/app/{app}/mediationapproved/change/{stato}")
	public @ResponseBody
	boolean changeMediationApproved(HttpServletRequest request,
			@PathVariable String app, @PathVariable String _id,
			@PathVariable String stato) {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("_id").is(_id));
		ContentToModeratorService mediationService = db.findOne(query2,
				ContentToModeratorService.class);

		mediationService.setManualApproved(State.valueOf(stato));
		mediationService.setTimestamp(System.currentTimeMillis());

		db.save(mediationService);

		return true;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/comment/data/{data}/{app}")
	public @ResponseBody
	List<ContentToModeratorService> exportComment(HttpServletRequest request,
			@PathVariable String app, @PathVariable long data) {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("timestamp").gte(data));
		query2.addCriteria(Criteria.where("webappname").regex(app));

		// pass all the key or only the reference?

		return db.find(query2, ContentToModeratorService.class);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/content/id/{identity}/{app}")
	public @ResponseBody
	List<ContentToModeratorService> getContentById(HttpServletRequest request,
			@PathVariable String app, @PathVariable String identity) {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("objectId").is(identity));
		query2.addCriteria(Criteria.where("webappname").regex(app));
		query2.limit(1);

		return db.find(query2, ContentToModeratorService.class);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/content/from/{fromdata}/to/{todata}/{app}")
	public @ResponseBody
	List<ContentToModeratorService> getContentById(HttpServletRequest request,
			@PathVariable String app, @PathVariable long fromdata,
			@PathVariable long todata) {

		Query query2 = new Query(new Criteria().andOperator(
				Criteria.where("timestamp").gte(fromdata),
				Criteria.where("timestamp").lte(todata)));
		query2.addCriteria(Criteria.where("webappname").regex(app));

		return db.find(query2, ContentToModeratorService.class);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/rest/content/id/{identity}/{app}")
	public @ResponseBody
	void deleteContentById(HttpServletRequest request,
			@PathVariable String app, @PathVariable String identity) {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("objectId").is(identity));
		query2.addCriteria(Criteria.where("webappname").regex(app));

		db.remove(query2, ContentToModeratorService.class);

	}
}
