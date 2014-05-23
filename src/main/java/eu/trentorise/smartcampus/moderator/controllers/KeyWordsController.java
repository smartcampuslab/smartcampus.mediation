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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.trentorise.smartcampus.moderator.model.KeyWord;

@Controller
public class KeyWordsController {

	@Autowired
	MongoTemplate db;

	@RequestMapping(method = RequestMethod.POST, value = "/rest/key/{app}/add")
	public @ResponseBody
	boolean addKey(HttpServletRequest request, @RequestParam("key") String key,
			@PathVariable String app) {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("keyword").is(key));
		KeyWord toDelete = (KeyWord) db.findOne(query2, KeyWord.class);

		// base case not in db
		if (toDelete == null) {
			KeyWord keyWord = new KeyWord(key, app);
			db.save(keyWord);
			return true;
		} else {
			// in db
			if (toDelete.getApps().contains(app)) {
				// in db and for this app
				return true;
			} else {
				// in db but not for this app
				toDelete.getApps().add(app);
				return true;
			}
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/key/{app}/all")
	public @ResponseBody
	List<KeyWord> getKey(HttpServletRequest request, @PathVariable String app) {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("apps").in(app));

		return db.find(query2, KeyWord.class);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/key/all")
	public @ResponseBody
	List<KeyWord> getAllKey(HttpServletRequest request) {

		Query query2 = new Query();

		return db.find(query2, KeyWord.class);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/rest/key/{app:.*}")
	public @ResponseBody
	boolean deleteKey(HttpServletRequest request, @RequestBody String key,
			@PathVariable String app) {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("keyword").is(key));

		KeyWord toDelete = (KeyWord) db.findOne(query2, KeyWord.class);

		Query query3 = new Query();
		query3.addCriteria(Criteria.where("keyword").is(key));
		query3.addCriteria(Criteria.where("apps").in(app));

		if (db.findOne(query3, KeyWord.class) != null) {
			toDelete.getApps().remove(app);
		} else {
			toDelete.getApps().add(app);
		}
		toDelete.setTimeupdate(System.currentTimeMillis());

		db.save(toDelete);
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/key/{app}/{data}")
	public @ResponseBody
	List<KeyWord> getDeltaKey(HttpServletRequest request,
			@PathVariable String app, @PathVariable long data) {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("timeupdate").gte(data));
		query2.addCriteria(Criteria.where("apps").in(app));

		// pass all the key or only the reference?

		return db.find(query2, KeyWord.class);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/key/data/{data}")
	public @ResponseBody
	List<KeyWord> getDeltaKey(HttpServletRequest request,
			@PathVariable long data) {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("timeupdate").gte(data));

		// pass all the key or only the reference?

		return db.find(query2, KeyWord.class);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/key/app/{app:.*}")
	public @ResponseBody
	List<KeyWord> getDAllKeyByApp(HttpServletRequest request,
			@PathVariable String app) {

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("apps").in(app));

		// pass all the key or only the reference?

		return db.find(query2, KeyWord.class);
	}

}
