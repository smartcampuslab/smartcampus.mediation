angular.module('dev', [ 'ngResource','ngCookies','filters'])




function commentController($scope, $http, $location, $cookieStore){
	$scope.comments=[];
	 $scope.currentPage = 0;
	    $scope.pageSize = 8;	    
	    $scope.numberOfPages=function(){
	        return Math.ceil($scope.comments.length/$scope.pageSize);                
	    }
	   
	
	
	var init = function() {
		
		$http(
				{method:'GET',
				 url:'rest/comment/parseapproved/all',
				 params:{},
				 headers:{Authorization:'Bearer '+$scope.token}
				})
		.success(function(data) {
			$scope.comments=data;			
		//	$scope.info = 'Find latest comments inserted';
		//	$scope.error = '';
		}).error(function(data) {
		//	$scope.info = '';
		//	$scope.error = "No comments found";
		});
		
	};
	init();
	
	
	
	$scope.editNote = function(_id) {
		var n = prompt("Add note to this comment ", "..."+_id);
		if (n != null && n.trim().length > 0) {
				$http(
			{method:'POST',
				 url:'rest/comment/'+_id+'/note/add',
				 params:{"note":n},
				 headers:{Authorization:'Bearer '+$scope.token}
				})
		.success(function(data) {
			init();	
		//	$scope.info = 'Find latest comments inserted';
		//	$scope.error = '';
		}).error(function(data) {
		//	$scope.info = '';
		//	$scope.error = "No comments found";
		});
		}
	};
	
	$scope.changeParseApproved = function(_id) {
		var n = prompt("Attention ");
		
				$http(
			{method:'PUT',
				 url:'rest/comment/'+_id+'/parseapproved/change',				
				 headers:{Authorization:'Bearer '+$scope.token}
				})
		.success(function(data) {
			init();	
		//	$scope.info = 'Find latest comments inserted';
		//	$scope.error = '';
		}).error(function(data) {
		//	$scope.info = '';
		//	$scope.error = "No comments found";
		});
		
	};
	$scope.changeMediationApproved = function(_id) {
		var n = prompt("Attention ");
		
				$http(
			{method:'PUT',
				 url:'rest/comment/'+_id+'/mediationapproved/change',				
				 headers:{Authorization:'Bearer '+$scope.token}
				})
		.success(function(data) {
			init();	
		//	$scope.info = 'Find latest comments inserted';
		//	$scope.error = '';
		}).error(function(data) {
		//	$scope.info = '';
		//	$scope.error = "No comments found";
		});
		
	};
	
	
	
}






angular.module('filters', []).
filter('truncate', function () {
    return function (text, length, end) {
        if (isNaN(length))
            length = 60;

        if (end === undefined)
            end = "...";

        if (text.length <= length || text.length - end.length <= length) {
            return text;
        }
        else {
            return String(text).substring(0, length-end.length) + end;
        }

    };
}).filter('dateformat', function () {
    return function (text, length, end) {
        return new Date(text).toUTCString();
    };
}).filter('startFrom', function() {
    return function(input, start) {
        start = +start; //parse to int
        return input.slice(start);
    }
});