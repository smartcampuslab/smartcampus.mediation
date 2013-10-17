var app = angular.module('dev', [ 'ngResource', 'ngCookies', 'filters',
		'$strap.directives' ]);

app.controller('MainCtrl', function($scope, $window, $location) {

	// The tab directive will use this data
	$scope.tabs = [ 'Parole chiavi', 'Da approvare', 'Approvati' ];
	$scope.tabs.index = 1;
	$scope.tabs.active = function() {
		return $scope.tabs[$scope.tabs.index];
	}

});

function filtro2Controller($scope, $http, $location, $cookieStore) {
	$scope.commentsParseApproved = [];
	$scope.currentPageFiltro2 = 0;
	$scope.pageSize = 8;
	$scope.numberOfPagesFiltro2 = function() {
		return Math.ceil($scope.commentsParseApproved.length / $scope.pageSize);
	}

	var init = function() {

		$http({
			method : 'GET',
			url : 'rest/comment/parseapproved/all',
			params : {},
			headers : {
				Authorization : 'Bearer ' + $scope.token
			}
		}).success(function(data) {
			$scope.commentsParseApproved = data;
			// $scope.info = 'Find latest comments inserted';
			// $scope.error = '';
		}).error(function(data) {
			// $scope.info = '';
			// $scope.error = "No comments found";
		});

	};
	init();

	$scope.editNote = function(_id) {
		var n = prompt("Add note to this comment ", "..." + _id);
		if (n != null && n.trim().length > 0) {
			$http({
				method : 'POST',
				url : 'rest/comment/' + _id + '/note/add',
				params : {
					"note" : n
				},
				headers : {
					Authorization : 'Bearer ' + $scope.token
				}
			}).success(function(data) {
				init();
				// $scope.info = 'Find latest comments inserted';
				// $scope.error = '';
			}).error(function(data) {
				// $scope.info = '';
				// $scope.error = "No comments found";
			});
		}
	};

	$scope.changeParseApproved = function(_id) {
		var n = prompt("Attention ");

		$http({
			method : 'PUT',
			url : 'rest/comment/' + _id + '/parseapproved/change',
			headers : {
				Authorization : 'Bearer ' + $scope.token
			}
		}).success(function(data) {
			init();
			// $scope.info = 'Find latest comments inserted';
			// $scope.error = '';
		}).error(function(data) {
			// $scope.info = '';
			// $scope.error = "No comments found";
		});

	};
	$scope.changeMediationApproved = function(_id) {
		var n = prompt("Attention ");

		$http({
			method : 'PUT',
			url : 'rest/comment/' + _id + '/mediationapproved/change',
			headers : {
				Authorization : 'Bearer ' + $scope.token
			}
		}).success(function(data) {
			init();
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
						Authorization : 'Bearer ' + $scope.token
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
	init();

}
function filtro1Controller($scope, $http, $location, $cookieStore) {
	$scope.commentsNotParseApproved = [];

	$scope.currentPageFiltro1 = 0;
	$scope.pageSize = 8;
	$scope.numberOfPagesFiltro1 = function() {
		return Math.ceil($scope.commentsNotParseApproved.length
				/ $scope.pageSize);
	}

	var init = function() {

		$http({
			method : 'GET',
			url : 'rest/comment/parsenotapproved/all',
			params : {},
			headers : {
				Authorization : 'Bearer ' + $scope.token
			}
		}).success(function(data) {
			$scope.commentsNotParseApproved = data;
			// $scope.info = 'Find latest comments inserted';
			// $scope.error = '';
		}).error(function(data) {
			// $scope.info = '';
			// $scope.error = "No comments found";
		});

	};
	init();

	$scope.editNote = function(_id) {
		var n = prompt("Add note to this comment ", "..." + _id);
		if (n != null && n.trim().length > 0) {
			$http({
				method : 'POST',
				url : 'rest/comment/' + _id + '/note/add',
				params : {
					"note" : n
				},
				headers : {
					Authorization : 'Bearer ' + $scope.token
				}
			}).success(function(data) {
				init();
				// $scope.info = 'Find latest comments inserted';
				// $scope.error = '';
			}).error(function(data) {
				// $scope.info = '';
				// $scope.error = "No comments found";
			});
		}
	};

	$scope.changeParseApproved = function(_id) {
		var n = prompt("Attention ");

		$http({
			method : 'PUT',
			url : 'rest/comment/' + _id + '/parseapproved/change',
			headers : {
				Authorization : 'Bearer ' + $scope.token
			}
		}).success(function(data) {
			init();
			// $scope.info = 'Find latest comments inserted';
			// $scope.error = '';
		}).error(function(data) {
			// $scope.info = '';
			// $scope.error = "No comments found";
		});

	};
	$scope.changeMediationApproved = function(_id) {
		var n = prompt("Attention ");

		$http({
			method : 'PUT',
			url : 'rest/comment/' + _id + '/mediationapproved/change',
			headers : {
				Authorization : 'Bearer ' + $scope.token
			}
		}).success(function(data) {
			init();
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
						Authorization : 'Bearer ' + $scope.token
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
	init();

}

function keyController($scope, $http, $location, $cookieStore) {
	$scope.keyList = [];

	var init = function() {

		$http({
			method : 'GET',
			url : 'rest/key/all',
			params : {},
			headers : {
				Authorization : 'Bearer ' + $scope.token
			}
		}).success(function(data) {
			$scope.keyList = data;
			// $scope.info = 'Find latest comments inserted';
			// $scope.error = '';
		}).error(function(data) {
			// $scope.info = '';
			// $scope.error = "No comments found";
		});

	};
	init();

	$scope.change = function(key) {
		$http({
			method : 'DELETE',
			url : '/rest/key/' + key + '/',
			params : {},
			headers : {
				Authorization : 'Bearer ' + $scope.token
			}
		}).success(function(data) {

			$scope.info = 'Disabled ' + key;
			$scope.error = '';
		}).error(function(data) {
			// $scope.info = '';
			// $scope.error = "No comments found";
		});

	}

	$scope.add = function(key) {
		
			$http({
				method : 'POST',
				url : 'rest/key/add',
				params : {
					key : key
				},
				headers : {
					Authorization : 'Bearer ' + $scope.token
				}
			}).success(function(data) {
				init();
			}).error(function(data) {

			});
		

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
});
