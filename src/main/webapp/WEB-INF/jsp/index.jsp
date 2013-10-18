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
	href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css"
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




</head>

<body ng-controller="MainCtrl">
	<div class="container"
		style="text-align: center; padding-top: 5%; height: 600px">
		<section id="tab" style="height: 550px">


			<div>
				Application: <select ng-model="app"
					ng-options="opt for opt in options.mstep" ng-change="init()"></select>
			</div>

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

					<div class="span3" style="height: 350px; padding-right: 20px;">
						Chiavi presenti nel sistema <br /> <br />
						<div>

							Filtra: <input type="search" ng-model="f"
								placeholder="filter apps..." />

						</div>
						<div class="span3" style="height: 300px; overflow-x: hidden;">

							<table>
								<tr ng:repeat="allkeyword in allkeyList | filter:f">
									<td><span ng:click="select(allkeyword)">{{allkeyword.key}}</span>
									</td>
									<td><span> <a href=""
											ng-click="change(allkeyword.key)"><i
												class="icon-fixed-width icon-plus"></i> </a></span></td>
								</tr>
							</table>
						</div>
					</div>

					<div class="span3"
						style="height: 350px; padding-right: 20px; border-left: 1px solid #eeeeee;">
						chiavi attive su questa app <br /> <br />
						<div>

							Filtra: <input type="search" ng-model="p"
								placeholder="filter apps..." />

						</div>
						<div class="span3" style="height: 300px; overflow-x: hidden;">

							<table>
								<tr ng:repeat="keyword in keyList | filter:p">
									<td><span ng:click="select(keyword)">{{keyword.key}}</span>
									</td>
									<td><span> <a href=""
											ng-click="change(keyword.key)"><i
												class="icon-fixed-width icon-remove"></i> </a></span></td>
								</tr>
							</table>
						</div>
					</div>

					<div class="span3"
						style="border-left: 1px solid #eeeeee; height: 350px">
						Inserisci nuova chiave nel sistema/app <br /> <br />
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