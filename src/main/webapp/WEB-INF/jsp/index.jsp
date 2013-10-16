<!DOCTYPE html>
<html lang="en" ng-app="dev">
<head>
<meta charset="utf-8">
<title>SmartCampus Developers</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Le styles -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/bs-ext.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}
</style>
<link href="css/bootstrap-responsive.min.css" rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular-resource.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular-cookies.min.js"></script>
<script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="lib/bootstrap.min.js"></script>
<script src="js/services.js"></script>

<link
	href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.no-icons.min.css"
	rel="stylesheet">
<link
	href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css"
	rel="stylesheet">
</head>

<body>


	<div ng-controller="commentController" class="container"
		style="text-align: center; padding-top: 5%;">
		<h1>Comment</h1>
		<!-- 		<div class="alert alert-error" ng-show="error != ''">{{error}}</div> -->
		<!-- 		<div class="alert alert-success" ng-show="info != ''">{{info}}</div> -->

		<div class="row">
			<div class="span12">


				<div>
				<!-- 	<div class="btn-group">

						<button class="btn btn-medium">Filtra commenti per:</button>
						<button class="btn btn-medium dropdown-toggle"
							data-toggle="dropdown">
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a ng-click="filterByApplication(order_by)" href="#">Applicazione</a></li>
							<li><a ng-click="filterByDate(order_by)" href="#">Data inserimento</a></li>
							<li><a ng-click="filterByPortalApproved(order_by)" href="#">Approvato da portale</a></li>
							<li><a ng-click="filterByParsingApproved(order_by)" href="#">Approvato da parsing</a></li>
							<li><a ng-click="filterByPortalNotApproved(order_by)" href="#">Non approvato da portale</a></li>
							<li><a ng-click="filterByParsingNotApproved(order_by)" href="#">Non approvato da parsing</a></li>
						</ul>
					</div>
					
					
					 <select ng-model="filter_by" bs-selectbox >
						<option value="webappname" ng-click="filterByApplication(order_by)" selected="selected">Applicazione</option>
						<option value="timestamp">Data inserimento</option>
						<option value="entityTesto" selected="selected">Testo</option>
						<option value="mediationNotApproved">Approvato da portale</option>
						<option value="parseApproved">Approvato da parsing</option>
						<option value="mediationNotApproved">Non approvato da portale</option>
						<option value="parseNotApproved">Non approvato da parsing</option>
					</select>
					-->
					Filtra:
					<input type="search" ng-model="q" placeholder="filter apps..." />

				</div>




				<br></br>




			</div>

				<div class="btn-group">
					<select ng-model="order_by" bs-selectbox">
						<option value="webappname" selected>Applicazione</option>
						<option value="timestamp">Data inserimento</option>
						<option value="mediationNotApproved">Approvato da portale</option>
						<option value="parseApproved">Approvato da parsing</option>
						<option value="mediationNotApproved">Non approvato da portale</option>
						<option value="parseNotApproved">Non approvato da parsing</option>
					</select>

					<!-- <button class="btn btn-medium">Ordina i commenti per:</button>
					<button class="btn btn-medium dropdown-toggle" data-toggle="dropdown">
						<span class="caret"></span>
					</button>
					<ul class="dropdown-menu" data-ng-model="selectedOption">
						<li><a ng-model="webapp" value="webapp" selected="selected">Applicazione</option>
						<li><a ng-click="sortFalsePortal()" href="#">Data inserimento</a></li>
						<li><a ng-click="sortTruePortal()" href="#">Approvato da portale</a></li>
						<li><a ng-click="sortTrueParsing()" href="#">Approvato da parsing</a></li>
						<li><a ng-click="sortFalsePortal()" href="#">Non approvato da portale</a></li>
						<li><a ng-click="sortFalseParsing()" href="#">Non approvato da parsing</a></li>
					</ul>-->
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
						<th>Approvato da portale</th>
						<th>Note</th>

					</tr>
				</thead>
				<tbody class="animate-repeat"
					ng-repeat="comment in comments | filter:q  | startFrom:currentPage*pageSize | limitTo:pageSize">
					<tr>
						<td>{{comment.webappname}}</td>
						<td>{{comment.timestamp | dateformat}}</td>
						<td>{{comment.entityId}}</td>
						<td>{{comment.userid}}</td>
						<td>{{comment.entityTesto|truncate}}</td>
						<td ng-switch on="comment.parseApproved"><span
							ng-switch-when="true">


								<div class="btn-group ">
									<a class="btn btn-success" href="#"><i
										class="icon-comment-alt"></i> {{comment.parseApproved}}</a> <a
										class="btn btn-success dropdown-toggle" data-toggle="dropdown"
										href="#"> <span class="icon-caret-down"></span></a>
									<ul class="dropdown-menu">
										<li><a href="" ng-click="editNote(comment._id)"><i
												class="icon-fixed-width icon-pencil"></i> Edit</a></li>
										<li><a href=""
											ng-click="changeParseApproved(comment._id)"><i
												class="icon-fixed-width icon-ban-circle"></i> Block</a></li>
									</ul>
								</div>



						</span> <span name="theForm" ng-switch-when="false">
								<div class="btn-group ">
									<a class="btn btn-danger" href="#"><i
										class="icon-ban-circle"></i> {{comment.parseApproved}}</a> <a
										class="btn btn-danger dropdown-toggle" data-toggle="dropdown"
										href="#"> <span class="icon-caret-down"></span></a>
									<ul class="dropdown-menu">
										<li><a href="" ng-click="editNote(comment._id)"><i
												class="icon-fixed-width icon-pencil"></i> Edit</a></li>
										<li><a href=""
											ng-click="changeParseApproved(comment._id)"><i
												class="icon-fixed-width icon-comment-alt"></i> Approved</a></li>
									</ul>
								</div>




						</span></td>



						<td ng-switch on="comment.mediationApproved"><span
							ng-switch-when="true">
								<div class="btn-group ">
									<a class="btn btn-success" href="#"><i
										class="icon-comment-alt"></i> {{comment.mediationApproved}}</a> <a
										class="btn btn-success dropdown-toggle" data-toggle="dropdown"
										href="#"> <span class="icon-caret-down"></span></a>
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
										class="icon-ban-circle"></i> {{comment.mediationApproved}}</a> <a
										class="btn btn-danger dropdown-toggle" data-toggle="dropdown"
										href="#"> <span class="icon-caret-down"></span></a>
									<ul class="dropdown-menu">
										<li><a href="" ng-click="editNote(comment._id)"><i
												class="icon-fixed-width icon-pencil"></i> Edit</a></li>
										<li><a href=""
											ng-click="changeMediationApproved(comment._id)"><i
												class="icon-fixed-width icon-comment-alt"></i> Approved</a></li>
									</ul>
								</div>
						</span>
						<td>{{comment.note}}</td>
					</tr>




				</tbody>
			</table>

			<button class="btn btn-primary" ng-disabled="currentPage == 0"
				ng-click="currentPage=currentPage-1">Previous</button>
			{{currentPage+1}}/{{numberOfPages()}}
			<button class="btn btn-primary"
				ng-disabled="currentPage >= comments.length/pageSize - 1"
				ng-click="currentPage=currentPage+1">Next</button>
		</div>
	</div>


	<!-- the triggers -->
</body>
</html>

