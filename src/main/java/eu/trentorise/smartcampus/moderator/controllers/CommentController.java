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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.trentorise.smartcampus.moderator.model.ContentToModeratorService;
import eu.trentorise.smartcampus.moderator.model.LogContentToModeratorService;
import eu.trentorise.smartcampus.moderator.model.State;

@Controller
public class CommentController {

	@Autowired
	MongoTemplate db;

	/**
	 * 
	 * @param request
	 * @param app
	 * @return true if the comment is saved correctly.
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/rest/comment/app/{app}/add")
	public @ResponseBody
	boolean addCommento(HttpServletRequest request,
			@RequestBody ContentToModeratorService messageToMediationService,
			@PathVariable String app) {

		
		ContentToModeratorService mediationService = messageToMediationService;
		Query query = Query.query(Criteria.where("objectId").is(mediationService.getObjectId()));
		
		ContentToModeratorService oldComment = db.findOne(query, ContentToModeratorService.class);
		ContentToModeratorService newComment = null;
		newComment = mediationService;
		if (oldComment != null) {
			if (!newComment.getManualApproved().equals(State.NOT_REQUEST)) {
				newComment.setKeywordApproved(oldComment.isKeywordApproved());
			}
			newComment.setObjectId(oldComment.getObjectId());
			newComment.set_id(oldComment.get_id());
		}

		LogContentToModeratorService deletedmessage = new LogContentToModeratorService(
				newComment, app, LogContentToModeratorService.ACTION_ADD);
		db.save(deletedmessage);

		return db.findOne(query, ContentToModeratorService.class) != null;
	}

	/**
	 * 
	 * @param request
	 * @param app
	 * @return all comments with state of approvation NOT_REQUEST of an app
	 */
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

	/**
	 * 
	 * @param request
	 * @param app
	 * @return all comments with state of approvation is different to
	 *         NOT_REQUEST of an app
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/rest/comment/remote/{app}/all")
	public @ResponseBody
	List<ContentToModeratorService> getCommentoMA(HttpServletRequest request,
			@PathVariable String app) {

		Query query2 = new Query();
		query2.sort().on("timestamp", Order.DESCENDING);
		query2.addCriteria(Criteria.where("manualApproved").ne(
				State.NOT_REQUEST));
		query2.addCriteria(Criteria.where("webappname").regex(app));

		List<ContentToModeratorService> listComments = db.find(query2, ContentToModeratorService.class);
		return listComments;
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

	/**
	 * 
	 * @param request
	 * @param app
	 * @return true if the state of the comment is changed correctly.
	 */
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
		LogContentToModeratorService deletedmessage = new LogContentToModeratorService(
				mediationService, app, stato);
		db.save(deletedmessage);

		db.save(mediationService);

		return true;

	}

	/**
	 * 
	 * @param request
	 * @param app
	 * @return all comments greater than a date or equal, of an app
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/rest/comment/data/{data}/{app:.*}")
	public @ResponseBody
	List<ContentToModeratorService> exportComment(HttpServletRequest request,
			@PathVariable String app, @PathVariable long data) {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("timestamp").gte(data));
		query2.addCriteria(Criteria.where("webappname").regex(app));

		// pass all the key or only the reference?

		return db.find(query2, ContentToModeratorService.class);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/content/id/{identity}/{app:.*}")
	public @ResponseBody
	List<ContentToModeratorService> getContentById(HttpServletRequest request,
			@PathVariable String app, @PathVariable String identity) {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("objectId").is(identity));
		query2.addCriteria(Criteria.where("webappname").regex(app));
		query2.limit(1);

		return db.find(query2, ContentToModeratorService.class);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/content/from/{fromdata}/to/{todata}/{app:.*}")
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

	@RequestMapping(method = RequestMethod.DELETE, value = "/rest/content/id/{identity}/{app:.*}")
	public @ResponseBody
	void deleteContentById(HttpServletRequest request,
			@PathVariable String app, @PathVariable String identity) {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("objectId").is(identity));
		query2.addCriteria(Criteria.where("webappname").regex(app));

		ContentToModeratorService toDelete = db.findOne(query2,
				ContentToModeratorService.class);
		LogContentToModeratorService deletedmessage = new LogContentToModeratorService(
				toDelete, app, LogContentToModeratorService.ACTION_DELETE);
		db.save(deletedmessage);
		db.remove(toDelete);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/web/massive/import")
	public @ResponseBody
	void massive_import(HttpServletRequest request,
			@RequestBody ArrayList<LinkedHashMap<String, Object>> messages) {

		for (LinkedHashMap<String, Object> index : messages) {
			// {"_id":"52ce5c9d975a22c092435ca4","parseApproved":true,"mediationApproved":"NOT_REQUEST","timestamp":1389255837563,"webappname":"ifame","entityId":167,"entityTesto":"Davvero buona","note":"null","userid":"232"}
			ContentToModeratorService newIndex = new ContentToModeratorService(
					String.valueOf(index.get("webappname")),
					String.valueOf(index.get("entityId")), String.valueOf(index
							.get("entityTesto")), index.get("userid")
							.toString());
			newIndex.setTimestamp(Long.valueOf(index.get("timestamp")
					.toString()));
			newIndex.setKeywordApproved(Boolean.valueOf(index.get(
					"parseApproved").toString()));
			newIndex.setManualApproved(State.valueOf(index.get(
					"mediationApproved").toString()));
			newIndex.setNote(index.get("note").toString());
			db.save(newIndex);

		}

	}
}
