angular.module('eventz', [ 'ngRoute' ])
  .config(function($routeProvider, $httpProvider) {

    $routeProvider.when('/', {
      templateUrl : 'home.html',
      controller : 'home',
      controllerAs: 'controller'
    }).when('/login', {
      templateUrl : 'login.html',
      controller : 'navigation',
      controllerAs: 'controller'
    }).when('/caminhoes', {
        templateUrl : 'caminhoes.html',
        controller : 'caminhoes',
        controllerAs: 'controller'
    }).otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

  })
  .controller('home', function($http) {
    var self = this;
    $http.get('/resource/').success(function(data) {
      self.greeting = data;
    })
  })
.controller('navigation',

  function($rootScope, $http, $location) {

  var self = this

  var authenticate = function(credentials, callback) {

    var headers = credentials ? {authorization : "Basic "
        + btoa(credentials.username + ":" + credentials.password)
    } : {};

    $http.get('user', {headers : headers}).success(function(data) {
      if (data.name) {
        $rootScope.authenticated = true;
      } else {
        $rootScope.authenticated = false;
      }
      callback && callback();
    }).error(function() {
      $rootScope.authenticated = false;
      callback && callback();
    });

  }

  authenticate();
  self.credentials = {};
  self.login = function() {
      authenticate(self.credentials, function() {
        if ($rootScope.authenticated) {
          $location.path("/");
          self.error = false;
        } else {
          $location.path("/login");
          self.error = true;
        }
      });
  };
  
  self.logout = function() {
	  $http.post('logout', {}).finally(function() {
	    $rootScope.authenticated = false;
	    $location.path("/");
	  });
	};

})
.controller('caminhoes',

	function($rootScope, $scope, $http, $location) {

		$scope.list = function() {
	
			$http({method: 'GET', url: '/caminhoes?'}).success(function(data) {
		        $scope.caminhoes = data._embedded.caminhoes;
			});
		    
		}
	 
	    $scope.save = function(caminhao) {
	    	
			$http.post('/caminhoes', $scope.caminhao).success(function(data) {
		        $scope.list();
			});
	    	
	    }
	    
	    $scope.list();
	    
	}

);