var app = angular.module('dev', [ 'ngResource', 'ngCookies', 'filters',
		'$strap.directives' ]);

app.controller('MainCtrl',
		function($scope, $http, $window, $location) {

			// The tab directive will use this data
			$scope.tabs = [ 'KeyWord', 'To Approve', 'Keyword Filter Log' ];
			$scope.tabs.index = 0;
			$scope.tabs.active = function() {
				return $scope.tabs[$scope.tabs.index];
			}
			$scope.remoteComment = [];
			$scope.currentPageFiltro2 = 0;
			$scope.pageSize = 8;
			$scope.numberOfPagesFiltro2 = function() {
				return Math.ceil($scope.remoteComment.length
						/ $scope.pageSize);
			}
			$scope. localComment = [];
			$scope.currentPageFiltro1 = 0;
			$scope.pageSize = 8;
			$scope.numberOfPagesFiltro1 = function() {
				return Math.ceil($scope. localComment.length
						/ $scope.pageSize);
			}
			$scope.keyList = [];
			$scope.allkeyList = [];

			$scope.app ;
			

			$scope.options = {
				mstep : appsFromDb
			};
			
			

			$scope.init = function() {
			
				$http({
					method : 'GET',
					url : 'rest/key/' + $scope.app.appId + '/all',
					params : {},
					headers : {
						Authorization : 'Bearer ' + $scope.app.appToken
					}
				}).success(function(data) {
					$scope.keyList = data;
					// $scope.info = 'Find latest comments inserted';
					// $scope.error = '';
				}).error(function(data) {
					// $scope.info = '';
					// $scope.error = "No comments found";
				});
				$http({
					method : 'GET',
					url : 'rest/key/all',
					params : {},
					headers : {
						Authorization : 'Bearer ' + $scope.app.appToken
					}
				}).success(function(data) {
					$scope.allkeyList = data;
					// $scope.info = 'Find latest comments inserted';
					// $scope.error = '';
				}).error(function(data) {
					// $scope.info = '';
					// $scope.error = "No comments found";
				});

				$http({
					method : 'GET',
					url : 'rest/comment/remote/' + $scope.app.appId + '/all',
					params : {},
					headers : {
						Authorization : 'Bearer ' + $scope.app.appToken
					}
				}).success(function(data) {
					$scope.remoteComment = data;
					// $scope.info = 'Find latest comments inserted';
					// $scope.error = '';
				}).error(function(data) {
					// $scope.info = '';
					// $scope.error = "No comments found";
				});

				$http({
					method : 'GET',
					url : 'rest/comment/local/' + $scope.app.appId + '/all',
					params : {},
					headers : {
						Authorization : 'Bearer ' + $scope.app.appToken
					}
				}).success(function(data) {
					$scope. localComment = data;
					// $scope.info = 'Find latest comments inserted';
					// $scope.error = '';
				}).error(function(data) {
					// $scope.info = '';
					// $scope.error = "No comments found";
				});

			};
			
			if($scope.app!=undefined)
			$scope.init();
			
			

		});


function filtro2Controller($scope, $http, $location, $cookieStore) {
	
	$scope.viewtext=function(text){
		if(text=="null")
			text= "";
		var mess=confirm(text);
	}

	$scope.editNote = function(_id,note) {
		var n = prompt("Add note to this comment ", ""+note);
		if (n != null && n.trim().length > 0) {
			$http({
				method : 'POST',
				url : 'rest/comment/' + _id + '/app/' + $scope.app.appId + '/note/add',
				params : {
					"note" : n
				},
				headers : {
					Authorization : 'Bearer ' + $scope.app.appToken
				}
			}).success(function(data) {
				$scope.init();
				// $scope.info = 'Find latest comments inserted';
				// $scope.error = '';
			}).error(function(data) {
				// $scope.info = '';
				// $scope.error = "No comments found";
			});
		}
	};

	
	$scope.changeMediationApproved = function(_id,stato,note) {
		$scope.editNote(_id,note);

		$http({
			method : 'PUT',
			url : 'rest/comment/' + _id +  '/app/' + $scope.app.appId + '/mediationapproved/change/'+stato,
			headers : {
				Authorization : 'Bearer ' + $scope.app.appToken
			}
		}).success(function(data) {
			$scope.init();
			// $scope.info = 'Find latest comments inserted';
			// $scope.error = '';
		}).error(function(data) {
			// $scope.info = '';
			// $scope.error = "No comments found";
		});

	};

	$scope.filterByApplication = function(sort_by) {

		$http(
				{
					method : 'GET',
					url : '/rest/comment/parseapproved/application/application/'
							+ sort_by,
					params : {},
					headers : {
						Authorization : 'Bearer ' + $scope.app.appToken
					}
				}).success(function(data) {
			$scope.comments = data;
			// $scope.info = 'Find latest comments inserted';
			// $scope.error = '';
		}).error(function(data) {
			// $scope.info = '';
			// $scope.error = "No comments found";
		});

	};
	if($scope.app!=undefined)
		$scope.init();

}
function filtro1Controller($scope, $http, $location, $cookieStore) {
	$scope.viewtext=function(text){
		if(text=="null")
			text= "";
		
		var mess=confirm(text);
	}

	

	$scope.changeParseApproved = function(_id) {
		

		$http({
			method : 'PUT',
			url : 'rest/comment/' + _id + '/parseapproved/change',
			headers : {
				Authorization : 'Bearer ' + $scope.app.appToken
			}
		}).success(function(data) {
			$scope.init();
			// $scope.info = 'Find latest comments inserted';
			// $scope.error = '';
		}).error(function(data) {
			// $scope.info = '';
			// $scope.error = "No comments found";
		});

	};
	

	$scope.filterByApplication = function(sort_by) {

		$http(
				{
					method : 'GET',
					url : '/rest/comment/parseapproved/application/application/'
							+ sort_by,
					params : {},
					headers : {
						Authorization : 'Bearer ' + $scope.app.appToken
					}
				}).success(function(data) {
			$scope.comments = data;
			// $scope.info = 'Find latest comments inserted';
			// $scope.error = '';
		}).error(function(data) {
			// $scope.info = '';
			// $scope.error = "No comments found";
		});

	};
	if($scope.app!=undefined)
		$scope.init();

}

function keyController($scope, $http, $location, $cookieStore) {

	$scope.change = function(key) {
		$http({
			method : 'PUT',
			url : 'rest/key/' + $scope.app.appId,
			data : key,
			headers : {
				Authorization : 'Bearer ' + $scope.app.appToken
			}
		}).success(function(data) {

			$scope.info = 'Disabled ' + key;
			$scope.error = '';
			$scope.init();
		}).error(function(data) {
			// $scope.info = '';
			// $scope.error = "No comments found";
		});

	}

	$scope.add = function(key) {

		$http({
			method : 'POST',
			url : 'rest/key/' + $scope.app.appId + '/add',
			params : {
				key : key
			},
			headers : {
				Authorization : 'Bearer ' + $scope.app.appToken
			}
		}).success(function(data) {
			$scope.init();
		}).error(function(data) {

		});

	}

	$scope.enabled = function(keyword) {
		return ($.inArray($scope.app, keyword.apps) == 0);

	}

}



angular.module('filters', []).filter('truncate', function() {
	return function(text, length, end) {
		if (isNaN(length))
		length = 60;

		if (end === undefined)
			end = "...";

		if (text.length <= length || text.length - end.length <= length) {
			return text;
		} else {
			return String(text).substring(0, length - end.length) + end;
		}		

	};
}).filter('dateformat', function() {
	return function(text, length, end) {
		return new Date(text).toLocaleString();
	};
}).filter('startFrom', function() {
	return function(input, start) {
		start = +start; // parse to int
		return input.slice(start);
	}
}).filter('nullString', function() {
	return function(input) {		
		if(input=="null")
			return "";
		else
			return input;
	}
});
