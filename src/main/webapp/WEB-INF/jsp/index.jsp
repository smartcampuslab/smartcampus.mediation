<!DOCTYPE html>
<html lang="en" ng-app="dev">


<head lang="en">
<meta charset="utf-8">
<title>Moderation Console</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/bootstrap-responsive.min.css" rel="stylesheet">
<link href="css/prettify.css" rel="stylesheet">
<link
	href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.no-icons.min.css"
	rel="stylesheet">
<link
	href="//netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css"
	rel="stylesheet">





<!-- required libraries -->

<script src="lib/jquery.min.js"></script>
<script src="lib/angular.js"></script>

<script src="lib/bootstrap.min.js"></script>
<script src="lib/angular-strap.js"></script>
<script src="js/services.js"></script>

<!-- optional libraries -->
<script src="lib/underscore-min.js"></script>
<script src="lib/moment.min.js"></script>
<script src="lib/fastclick.min.js"></script>
<script src="lib/prettify.js"></script>
<script src="lib/angular-resource.min.js"></script>
<script src="lib/angular-cookies.min.js"></script>
<script src="lib/moment.js"></script>



<script>
var token="<%=request.getAttribute("token")%>";
var appsFromDb=<%=request.getAttribute("appsFromDb")%>;
var user_name="<%=request.getAttribute("user")%>";


</script>



</head>



<body ng-controller="MainCtrl">
	<div class="container" style="width: 65%;">
		<div class="row" style="height: 120px">
			<h1>Moderation Console</h1>
		</div>
		<div class="row">
			<div class="span6 "></div>
			<div class="span4 well">
				<div class="row">
					<div class="span1" style="text-align:center"><i class="fa fa-user fa-5x"></i>
					
					</div>
					<div class="span3">
						<p>
							Developer :<strong><span id="developer"></span></strong>
						</p>
						<p>
							Application: <select style="width: 120px" ng-model="app"
								ng-options="opt.appId for opt in options.mstep"
								ng-change="init()"></select> 
						</p>
						<p>
							Qualify:  <strong><span > {{qualify}}</span></strong>
						</p>
						<span class=" badge badge-warning" id="manualCount">manualCount</span>
						<span class=" badge badge-info" id="keywordCount">keywordCount</span>
						<p style="margin-top: 10px;">
						<button type="button" class="btn btn-success " ng-disabled="app==undefined" ng-click="reload();"><i class="fa fa-refresh"></i>  Refresh</button>
						</p>
					</div>
				</div>
			</div>
		</div>

		<div class="row">

			<section id="tab" style="height: 550px">


				<div class="tab_menu" ng-model="tabs.index" bs-tabs>
					<div ng-repeat="tab in tabs" data-title="{{tab}}"></div>
				</div>

				<div ng-show="tabs.active() == 'To Approve'">


					<div ng-controller="filtro2Controller" class="container"
						style="text-align: center; padding-top: 5%;">


						<div class="row">
							<div class="span12">
								<div class="span8"></div>

								<div class="span3">

									Filtra: <input type="search" ng-model="q"
										placeholder="filter apps..." />

								</div>




								<br></br>




							</div>




							<br></br>

							<table class="table table-striped table-bordered">
								<thead>
									<tr>
										<th>Sended date</th>
										<th>ID Content</th>
										<th>ID user</th>
										<th>Content text</th>
										<th>Approved by manual</th>
										<th>Note</th>

									</tr>
								</thead>
								<tbody class="animate-repeat"
									ng-repeat="comment in remoteComment | filter:q  | startFrom:currentPageFiltro2*pageSize | limitTo:pageSize">
									<tr>
										<td>{{comment.timestamp | dateformat}}</td>
										<td>{{comment.objectId}}</td>
										<td>{{comment.userid}}</td>
										<td><a href="" ng-click="viewtext(comment.objectText)">{{comment.objectText|truncate}}</a></td>



										<td ng-switch on="comment.manualApproved"><span
											ng-switch-when="WAITING">
												<div class="btn-group ">
													<a class="btn btn-warning" href="#"><i
														class="fa fa-exclamation-triangle"></i> Waiting valutation</a>
													<a class="btn btn-warning dropdown-toggle"
														data-toggle="dropdown" href="#"> <span
														class="icon-caret-down"></span></a>
													<ul class="dropdown-menu">
														<li><a href=""
															ng-click="changeMediationApproved(comment._id,'APPROVED',comment.note)"><i
																class="fa fa-check"></i> Approve</a></li>
														<li><a href=""
															ng-click="changeMediationApproved(comment._id,'NOT_APPROVED',comment.note)"><i
																class="fa fa-minus-circle"></i> Block</a></li>
													</ul>
												</div>

										</span> <span name="theForm" ng-switch-when="NOT_APPROVED">
												<div class="btn-group ">
													<a class="btn btn-danger" href="#"><i
														class="icon-ban-circle"></i> Blocked</a> <a
														class="btn btn-danger dropdown-toggle"
														data-toggle="dropdown" href="#"> <span
														class="icon-caret-down"></span></a>
													<ul class="dropdown-menu">
														<li><a href=""
															ng-click="changeMediationApproved(comment._id,'APPROVED',comment.note)"><i
																class="fa fa-check"></i> Approved</a></li>
														<li><a href=""
															ng-click="changeMediationApproved(comment._id,'WAITING',comment.note)"><i
																class="fa fa-clock-o"></i> Sospend</a></li>
													</ul>
												</div>
										</span> <span name="theForm" ng-switch-when="APPROVED">
												<div class="btn-group ">
													<a class="btn btn-success" href="#"><i
														class="fa fa-check"></i> Approved</a> <a
														class="btn btn-success dropdown-toggle"
														data-toggle="dropdown" href="#"> <span
														class="icon-caret-down"></span></a>
													<ul class="dropdown-menu">
														<li><a href=""
															ng-click="changeMediationApproved(comment._id,'WAITING',comment.note)"><i
																class="fa fa-clock-o"></i> Sospend</a></li>
														<li><a href=""
															ng-click="changeMediationApproved(comment._id,'NOT_APPROVED',comment.note)"><i
																class="fa fa-minus-circle"></i> Block</a></li>
													</ul>
												</div>
										</span></td>
										<td>{{comment.note | nullString}}</td>
										<td><a href=""
											ng-click="editNote(comment._id,comment.note)"><i
												class="fa fa-pencil"></i> Edit</a></td>
									</tr>




								</tbody>
							</table>

							<button class="btn btn-primary"
								ng-disabled="currentPageFiltro2 == 0"
								ng-click="currentPageFiltro2=currentPageFiltro2-1">Previous</button>
							{{currentPageFiltro2+1}}/{{numberOfPagesFiltro2()}}
							<button class="btn btn-primary"
								ng-disabled="currentPageFiltro2 >= remoteComment.length/pageSize - 1"
								ng-click="currentPageFiltro2=currentPageFiltro2+1">Next</button>
						</div>
					</div>

				</div>

				<div ng-show="tabs.active() == 'Keyword Filter Log'">

					<div ng-controller="filtro1Controller" class="container"
						style="text-align: center; padding-top: 5%;">


						<div class="row">
							<div class="span12">
								<div class="span8"></div>

								<div class="span3">
									Filtra: <input type="search" ng-model="q"
										placeholder="filter apps..." />

								</div>







							</div>



							<br></br>

							<table class="table table-striped table-bordered">
								<thead>
									<tr>

										<th>Sended date</th>
										<th>ID Content</th>
										<th>ID user</th>
										<th>Content Text</th>
										<th>Approved by keyword filter</th>

										<th>Note</th>

									</tr>
								</thead>
								<tbody class="animate-repeat"
									ng-repeat="comment in  localComment | filter:q  | startFrom:currentPageFiltro1*pageSize | limitTo:pageSize">
									<tr>

										<td>{{comment.timestamp | dateformat}}</td>
										<td>{{comment.objectId}}</td>
										<td>{{comment.userid}}</td>
										<td><a href="" ng-click="viewtext(comment.objectText)">{{comment.objectText|truncate}}</a></td>

										<td ng-switch on="comment.keywordApproved"><span
											ng-switch-when="false">
												<div>
													<a class="label label-danger" href="#"><i
														class="fa fa-minus-circle"></i> Blocked</a>
												</div>

										</span> <span name="theForm" ng-switch-when="true">
												<div>
													<a class="label label-success" href="#"><i
														class="fa fa-check"></i> Approved</a>
												</div>
										</span></td>



										<td>{{comment.note |nullString}}</td>
									</tr>




								</tbody>
							</table>

							<button class="btn btn-primary"
								ng-disabled="currentPageFiltro1 == 0"
								ng-click="currentPageFiltro1=currentPageFiltro1-1">Previous</button>
							{{currentPageFiltro1+1}}/{{numberOfPagesFiltro1()}}
							<button class="btn btn-primary"
								ng-disabled="currentPageFiltro1 >=  localComment.length/pageSize - 1"
								ng-click="currentPageFiltro1=currentPageFiltro1+1">Next</button>
						</div>
					</div>



				</div>


				<div ng-show="tabs.active() == 'KeyWord'">


					<div ng-controller="keyController" class="container"
						style="text-align: center; padding-top: 5%;">

						<div class="span3" style="height: 350px; padding-right: 20px;">
							keyword in system <br /> <br />
							<div>

								Filter: <input type="search" ng-model="f"
									placeholder="filter apps..." />

							</div>
							<div class="span3" style="height: 300px; overflow-x: hidden;">

								<table>
									<tr ng:repeat="allkeyword in allkeyList | filter:f">
										<td><span ng:click="select(allkeyword)">{{allkeyword.keyword}}</span>
										</td>
										<td><span> <a href=""
												ng-click="change(allkeyword.keyword)"><i
													class="icon-fixed-width icon-plus"></i> </a></span></td>
									</tr>
								</table>
							</div>
						</div>

						<div class="span3"
							style="height: 350px; padding-right: 20px; border-left: 1px solid #eeeeee;">
							active keyword in this app <br /> <br />
							<div>

								Filter: <input type="search" ng-model="p"
									placeholder="filter apps..." />

							</div>
							<div class="span3" style="height: 300px; overflow-x: hidden;">

								<table>
									<tr ng:repeat="keyword in keyList | filter:p">
										<td><span ng:click="select(keyword)">{{keyword.keyword}}</span>
										</td>
										<td><span> <a href=""
												ng-click="change(keyword.keyword)"><i
													class="icon-fixed-width icon-remove"></i> </a></span></td>
									</tr>
								</table>
							</div>
						</div>

						<div class="span3"
							style="border-left: 1px solid #eeeeee; height: 350px">
							Insert keyword in app filter <br /> <br />
							<form name="myForm">
								key : <input type="text" name="key" ng-model="key" required>
								<span class="error" ng-show="myForm.$error.required">Required!</span>
						</div>


						</form>
						<button ng-click="add(key)">Add</button>


					</div>




				</div>

				<div ng-show="tabs.active() == 'Moderator List for this app' && app.owner == true">

					<div ng-controller="ModeratorsController" class="container"
						style="text-align: center; padding-top: 5%;">
						<div class="row-fluid">


							
							<div class="row-fluid">

								<div class="span4" style="height: 350px; border-right: 1px solid #eeeeee; padding-right: 20px;text-align:left;">
									Profile in system <br /> <br />
									<div>

										Filter: <input type="search" ng-model="f"
											placeholder="filter profiles..." />

									</div>
									<div class="span12" style="height: 300px; overflow: auto;">
										<ul>
											<li style="list-style-type: none; text-decoration: none"
												ng:repeat="profile in profiles | filter:f"><a href=""
												style="text-decoration: none"
												ng-click="loadModerator(profile)"><i
													class="icon-fixed-width icon-user"></i>
													{{profile.name}},{{profile.surname}}</a></li>

										</ul>
									</div>
								</div>

								<div class="span4"
									style="height: 350px; padding-right: 20px;text-align:left;">
Add new moderator<br /> <br />
	<div class="span12" style="height: 300px; overflow: auto;">
									User: <strong><span>{{possibleModerator.name}}  {{possibleModerator.surname}}</span></strong>
									<br/>
								
									<br/>
									<br/>
								    <button ng-click="addModerator()">Add Moderator</button>
								    
</div>
								</div>
								
								<div class="span4" style="min-height: 100px;text-align:left;">

Active moderator<br /> <br />


								<table class="table table-striped table-bordered">
									<thead>
										<tr>

										
											<th>Name</th>
											<th>Surname</th>
											<th>Manage</th>



										</tr>
									</thead>
									<tbody class="animate-repeat" ng-repeat="mod in moderators">
										<tr>

										
											<td>{{mod.name}}</td>
											<td>{{mod.surname}}</td>
											<td><span ng-if="isOwner(mod)"> <a href="" ng-show="!app.owner"
													ng-click="deleteModerator(mod._id)"><i
														class="icon-fixed-width icon-remove"></i></a></span></td>

										</tr>




									</tbody>
								</table>

							</div>




							</div>
						</div>

					</div>
					</div>
			</section>
			<hr>
		</div>





	</div>
</body>

</html>