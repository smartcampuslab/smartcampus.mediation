<!DOCTYPE html>
<html lang="en" ng-app="dev">


<head lang="en">
<meta charset="utf-8">
<title>AngularStrap - Tab directive</title>

<link
	href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap-responsive.min.css"
	rel="stylesheet">
<link href="http://mgcrea.github.com/angular-strap/css/prettify.css"
	rel="stylesheet">

<!-- required libraries -->
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.0.6/angular.js"></script>
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
<script src="http://mgcrea.github.com/angular-strap/js/angular-strap.js"></script>

<!-- optional libraries -->
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.4.4/underscore-min.js"></script>
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.0.0/moment.min.js"></script>
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/fastclick/0.6.0/fastclick.min.js"></script>
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/prettify/r224/prettify.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.6/angular-resource.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.6/angular-cookies.min.js"></script>

<link rel="stylesheet" href="style.css">
<script src="js/services.js"></script>

<link
	href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.no-icons.min.css"
	rel="stylesheet">
<link
	href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css"
	rel="stylesheet">
</head>

<body ng-controller="MainCtrl">
	<div class="container">
		<section id="tab">




			<div ng-model="tabs.index" bs-tabs>
				<div ng-repeat="tab in tabs" data-title="{{tab}}"></div>
			</div>

			<div ng-show="tabs.active() == 'Da approvare'">


				<div ng-controller="filtro2Controller" class="container"
					style="text-align: center; padding-top: 5%;">
					<h1>Commenti approvati da controllo automatico</h1>

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
									<th>Applicazione</th>
									<th>Data inserimento</th>
									<th>ID commento</th>
									<th>ID user</th>
									<th>Testo</th>
									<th>Approvato da portale</th>
									<th>Note</th>

								</tr>
							</thead>
							<tbody class="animate-repeat"
								ng-repeat="comment in commentsParseApproved | filter:q  | startFrom:currentPage*pageSize | limitTo:pageSize">
								<tr>
									<td>{{comment.webappname}}</td>
									<td>{{comment.timestamp | dateformat}}</td>
									<td>{{comment.entityId}}</td>
									<td>{{comment.userid}}</td>
									<td>{{comment.entityTesto|truncate}}</td>



									<td ng-switch on="comment.mediationApproved"><span
										ng-switch-when="true">
											<div class="btn-group ">
												<a class="btn btn-success" href="#"><i
													class="icon-comment-alt"></i> Approved</a> <a
													class="btn btn-success dropdown-toggle"
													data-toggle="dropdown" href="#"> <span
													class="icon-caret-down"></span></a>
												<ul class="dropdown-menu">
													<li><a href="" ng-click="editNote(comment._id)"><i
															class="icon-fixed-width icon-pencil"></i> Edit</a></li>
													<li><a href=""
														ng-click="changeMediationApproved(comment._id)"><i
															class="icon-fixed-width icon-ban-circle"></i> Block</a></li>
												</ul>
											</div>

									</span> <span name="theForm" ng-switch-when="false">
											<div class="btn-group ">
												<a class="btn btn-danger" href="#"><i
													class="icon-ban-circle"></i> Blocked</a> <a
													class="btn btn-danger dropdown-toggle"
													data-toggle="dropdown" href="#"> <span
													class="icon-caret-down"></span></a>
												<ul class="dropdown-menu">
													<li><a href="" ng-click="editNote(comment._id)"><i
															class="icon-fixed-width icon-pencil"></i> Edit</a></li>
													<li><a href=""
														ng-click="changeMediationApproved(comment._id)"><i
															class="icon-fixed-width icon-comment-alt"></i> Approved</a></li>
												</ul>
											</div>
									</span></td>
									<td>{{comment.note}}</td>
								</tr>




							</tbody>
						</table>

						<button class="btn btn-primary"
							ng-disabled="currentPageFiltro2 == 0"
							ng-click="currentPage=currentPage-1">Previous</button>
						{{currentPageFiltro2+1}}/{{numberOfPagesFiltro2()}}
						<button class="btn btn-primary"
							ng-disabled="currentPageFiltro2 >= commentsParseApproved.length/pageSize - 1"
							ng-click="currentPageFiltro2=currentPageFiltro2+1">Next</button>
					</div>
				</div>

			</div>

			<div ng-show="tabs.active() == 'Approvati'">

				<div ng-controller="filtro1Controller" class="container"
					style="text-align: center; padding-top: 5%;">
					<h1>Commenti da controllo automatico gia' bloccati</h1>

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
									<th>Applicazione</th>
									<th>Data inserimento</th>
									<th>ID commento</th>
									<th>ID user</th>
									<th>Testo</th>
									<th>Approvato da controllo automatico</th>

									<th>Note</th>

								</tr>
							</thead>
							<tbody class="animate-repeat"
								ng-repeat="comment in commentsNotParseApproved | filter:q  | startFrom:currentPage*pageSize | limitTo:pageSize">
								<tr>
									<td>{{comment.webappname}}</td>
									<td>{{comment.timestamp | dateformat}}</td>
									<td>{{comment.entityId}}</td>
									<td>{{comment.userid}}</td>
									<td>{{comment.entityTesto|truncate}}</td>
									<td><span>
											<div class="btn-group ">
												<a class="btn btn-danger" href="#"><i
													class="icon-ban-circle"></i> Blocked</a>
											</div>




									</span></td>



									<td>{{comment.note}}</td>
								</tr>




							</tbody>
						</table>

						<button class="btn btn-primary"
							ng-disabled="currentPageFiltro1 == 0"
							ng-click="currentPageFiltro1=currentPageFiltro1-1">Previous</button>
						{{currentPageFiltro1+1}}/{{numberOfPagesFiltro1()}}
						<button class="btn btn-primary"
							ng-disabled="currentPageFiltro1 >= commentsNotParseApproved.length/pageSize - 1"
							ng-click="currentPageFiltro1=currentPageFiltro1+1">Next</button>
					</div>
				</div>



			</div>


			<div ng-show="tabs.active() == 'Parole chiavi'">


				<div ng-controller="keyController" class="container"
					style="text-align: center; padding-top: 5%;">

						<div class="span2" style="height:300px;overflow:scroll;">
					<table>
						<tr ng:repeat="artist in keyList" ><td >
							<span ng:click="select(artist)">{{artist.key}}</span>
							</td> <td ng-switch on="artist.enabled"><span ng-switch-when="false">
							<a href="" ng-click=""><i class="icon-fixed-width icon-ban-circle"></i> </a></span>
							<span  ng-switch-when="true"><a href="" ng-click=""><i class="icon-fixed-width icon-check"></i> </a>
							</span>
							
							
							
						</td></tr>
					</table></div>
						
<div class="span4">
					<form name="myForm">
						key : <input type="text" name="key" ng-model="key" required>
						<span class="error" ng-show="myForm.$error.required">Required!</span>
</div>

						
					</form>
<button ng-click="add(key)">Add</button>


				</div>




			</div>



			<hr>
	</div>

	</section>
</body>

</html>