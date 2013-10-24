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
package eu.trentorise.smartcampus.mediation.controllers;

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

import eu.trentorise.smartcampus.mediation.model.KeyWord;
import eu.trentorise.smartcampus.mediation.model.MessageToMediationService;

@Controller
public class CommentController {

	@Autowired
	MongoTemplate db;

	@RequestMapping(method = RequestMethod.POST, value = "/rest/comment/add")
	public @ResponseBody
	boolean addCommento(HttpServletRequest request,
			@RequestBody String messageToMediationService) {
		MessageToMediationService mediationService = MessageToMediationService
				.valueOf(messageToMediationService);

		db.save(mediationService);

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("entityId").is(
				mediationService.getEntityId()));
		return db.findOne(query2, MessageToMediationService.class) != null;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/comment/parsenotapproved/{app}/all")
	public @ResponseBody
	List<MessageToMediationService> getCommentoPA(HttpServletRequest request,
			@PathVariable String app) {

		Query query2 = new Query();
		query2.sort().on("timestamp", Order.DESCENDING);
		query2.addCriteria(Criteria.where("parseApproved").is(false));
		query2.addCriteria(Criteria.where("webappname").is(app));

		return db.find(query2, MessageToMediationService.class);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/comment/parseapproved/{app}/all")
	public @ResponseBody
	List<MessageToMediationService> getCommentoMA(HttpServletRequest request,
			@PathVariable String app) {

		Query query2 = new Query();
		query2.sort().on("timestamp", Order.DESCENDING);
		query2.addCriteria(Criteria.where("parseApproved").is(true));
		query2.addCriteria(Criteria.where("webappname").is(app));

		return db.find(query2, MessageToMediationService.class);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/comment/parseapproved/application/{sort_by}")
	public @ResponseBody
	List<MessageToMediationService> getCommentoFileredByApp(
			HttpServletRequest request, @PathVariable String sort_by) {

		Query queryFilterApp = new Query();
		queryFilterApp.addCriteria(Criteria.where("app_name").is(sort_by));
		queryFilterApp.sort().on(sort_by, Order.DESCENDING);

		return db.find(queryFilterApp, MessageToMediationService.class);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/rest/comment/{_id}/note/add")
	public @ResponseBody
	boolean addNote(HttpServletRequest request, @PathVariable String _id,
			@RequestParam String note) {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("_id").is(_id));
		MessageToMediationService mediationService = db.findOne(query2,
				MessageToMediationService.class);

		mediationService.setNote(note);

		db.save(mediationService);

		return true;

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/rest/comment/{_id}/mediationapproved/change")
	public @ResponseBody
	boolean changeMediationApproved(HttpServletRequest request,
			@PathVariable String _id) {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("_id").is(_id));
		MessageToMediationService mediationService = db.findOne(query2,
				MessageToMediationService.class);

		mediationService.setMediationApproved(!mediationService
				.isMediationApproved());
		mediationService.setTimestamp(System.currentTimeMillis());

		db.save(mediationService);

		// controllo se il commento è stato approvato nei 2 livelli
		if (mediationService.isParseApproved()
				&& mediationService.isMediationApproved()) {

		}

		return true;

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/rest/comment/{app}/{data}")
	public @ResponseBody
	List<MessageToMediationService> exportComment(HttpServletRequest request,
			@PathVariable String app, @PathVariable long data) {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("timestamp").gte(data));
		query2.addCriteria(Criteria.where("apps").in(app));

		// pass all the key or only the reference?

		return db.find(query2, MessageToMediationService.class);
	}

}
