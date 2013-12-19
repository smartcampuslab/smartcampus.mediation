<!DOCTYPE html>
<html lang="en" ng-app="dev">


<head lang="en">
<meta charset="utf-8">
<title>AngularStrap - Tab directive</title>

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

<script >
var token="<%=request.getAttribute("token")%>";
var appsFromDb=<%=request.getAttribute("appsFromDb")%>;
var user_name=<%=request.getAttribute("user")%>;
document.getElementById("developer").innerHTML=user_name;
</script>

</head>



<body ng-controller="MainCtrl"  > 
	<div class="container"
		style="text-align: center; padding-top: 5%; height: 600px">
		
		
		<section id="tab" style="height: 550px">

			<div class="account">
			    Developer: <span id="developer"></span><br/>
				Application: <select ng-model="app"
					ng-options="opt.appId for opt in options.mstep" ng-change="init()"></select>
			</div>

			<div class="tab_menu" ng-model="tabs.index" bs-tabs>
				<div ng-repeat="tab in tabs" data-title="{{tab}}"></div>
			</div>

			<div ng-show="tabs.active() == 'To Approve'" >


				<div ng-controller="filtro2Controller" class="container"
					style="text-align: center; padding-top: 5%;">
					<h1>Content to approve</h1>

					<div class="row">
						<div class="span12">


							<div>

								Filtra: <input type="search" ng-model="q"
									placeholder="filter apps..." />

							</div>




							<br></br>




						</div>
						



						<br></br>

						<table class="table table-striped table-condensed">
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
													class="fa fa-exclamation-triangle"></i> Waiting valutation</a> <a
													class="btn btn-warning dropdown-toggle"
													data-toggle="dropdown" href="#"> <span
													class="icon-caret-down"></span></a>
												<ul class="dropdown-menu">
													<li><a href="" ng-click="changeMediationApproved(comment._id,'APPROVED',comment.note)"><i
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
													<li><a href="" ng-click="changeMediationApproved(comment._id,'APPROVED',comment.note)"><i
															class="fa fa-check"></i> Approved</a></li>
													<li><a href=""
														ng-click="changeMediationApproved(comment._id,'WAITING',comment.note)"><i
															class="fa fa-clock-o"></i> Sospend</a></li>
												</ul>
											</div>
									</span>
									<span name="theForm" ng-switch-when="APPROVED">
											<div class="btn-group ">
												<a class="btn btn-success" href="#"><i
													class="fa fa-check"></i> Approved</a> <a
													class="btn btn-success dropdown-toggle"
													data-toggle="dropdown" href="#"> <span
													class="icon-caret-down"></span></a>
												<ul class="dropdown-menu">
													<li><a href="" ng-click="changeMediationApproved(comment._id,'WAITING',comment.note)"><i
															class="fa fa-clock-o"></i> Sospend</a></li>
													<li><a href=""
														ng-click="changeMediationApproved(comment._id,'NOT_APPROVED',comment.note)"><i
															class="fa fa-minus-circle"></i> Block</a></li>
												</ul>
											</div>
									</span>
									</td>
									<td>{{comment.note | nullString}} </td><td><a href=""
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
					<h1>Keyword Filter Log</h1>

					<div class="row">
						<div class="span12">


							<div>

								Filtra: <input type="search" ng-model="q"
									placeholder="filter apps..." />

							</div>







						</div>



						<br></br>

						<table class="table table-striped table-condensed">
							<thead>
								<tr>
									
									<th>Sended date </th>
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
											<div >
												<a class="label label-danger" href="#"><i
													class="fa fa-minus-circle"></i> Blocked</a>
											</div>

									</span> <span name="theForm" ng-switch-when="true">
											<div >
												<a class="label label-success" href="#"><i
													class="fa fa-check"></i> Approved</a>
											</div>
									</span>
									
									</td>



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



		</section>
		<hr>
	</div>






</body>

</html>