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

import eu.trentorise.smartcampus.mediation.model.MessageToMediationService;

@Controller
public class MediationController {

	@Autowired
	MongoTemplate db;

	@RequestMapping(method = RequestMethod.POST, value = "/rest/comment/parseapproved/add")
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

	@RequestMapping(method = RequestMethod.GET, value = "/rest/comment/parsenotapproved//all")
	public @ResponseBody
	List<MessageToMediationService> getCommentoPA(HttpServletRequest request) {

		Query query2 = new Query();
		query2.sort().on("timestamp", Order.DESCENDING);
		query2.addCriteria(Criteria.where("parseApproved").is(false));
		

		return db.find(query2, MessageToMediationService.class);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/rest/comment/parseapproved/all")
	public @ResponseBody
	List<MessageToMediationService> getCommentoMA(HttpServletRequest request) {

		Query query2 = new Query();
		query2.sort().on("timestamp", Order.DESCENDING);
		query2.addCriteria(Criteria.where("parseApproved").is(true));
		

		return db.find(query2, MessageToMediationService.class);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/comment/parseapproved/application/{sort_by}")
	public @ResponseBody
	List<MessageToMediationService> getCommentoFileredByApp(
			HttpServletRequest request, @PathVariable String sort_by) {

		Query queryFilterApp = new Query();
		queryFilterApp.addCriteria(Criteria.where("app_name").is(sort_by));
		MessageToMediationService mediationService = db.findOne(queryFilterApp,
				MessageToMediationService.class);
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

		db.save(mediationService);

		// controllo se il commento è stato approvato nei 2 livelli
		if (mediationService.isParseApproved()
				&& mediationService.isMediationApproved()) {

		}

		return true;

	}

	private void setCommentApprovedTo(
			MessageToMediationService messageToMediationService) {

		try {
			// RemoteConnector.postJSON(
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// } catch (RemoteException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

}
