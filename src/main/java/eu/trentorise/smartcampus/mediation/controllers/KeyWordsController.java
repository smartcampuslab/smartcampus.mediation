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
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.trentorise.smartcampus.mediation.model.KeyWord;

@Controller
public class KeyWordsController {

	@Autowired
	MongoTemplate db;

	@RequestMapping(method = RequestMethod.POST, value = "/rest/key/add")
	public @ResponseBody
	boolean addKey(HttpServletRequest request, @RequestParam("key")  String key) {
		
		KeyWord keyWord=new KeyWord(key);
		
		db.save(keyWord);

		
		
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rest/key/all")
	public @ResponseBody
	List<KeyWord> getKey(HttpServletRequest request) {

		Query query2 = new Query();		
		//query2.addCriteria(Criteria.where("parseApproved").is(false));
		

		return db.find(query2, KeyWord.class);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/rest/key/{key}")
	public @ResponseBody
	boolean deleteKey(HttpServletRequest request, @PathVariable String key) {

		Query query2 = new Query();
		
		query2.addCriteria(Criteria.where("key").is(key));
		KeyWord toDelete=(KeyWord) db.find(query2, KeyWord.class);
		toDelete.setEnabled(false);

		db.save(toDelete);
		return true;
	}
	
	
}
