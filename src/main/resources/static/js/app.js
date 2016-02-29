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
    }).when('/caminhao', {
        templateUrl : 'caminhao.html',
        controller : 'caminhao',
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
.controller('caminhao',

	function($rootScope, $scope, $http, $location) {

	    $http({method: 'GET', url: '/caminhao?'})
	      .success(function(data) {
	    	  
	        $scope.caminhoes = data.caminhao;

	        console.log(data);    
	      });
		
	    
	    
	}

);