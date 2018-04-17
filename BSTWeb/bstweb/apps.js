//Define an angular module for our App
var bstApp = angular.module('bstWeb', ['ngRoute','toaster','ngAnimate','ui.bootstrap', 'ngSanitize', 'BSTServices']);

//Define Routing for app
bstApp.config(['$routeProvider', '$locationProvider', '$httpProvider',function($routeProvider, $locationProvider, $httpProvider) {
    $routeProvider.
        when('/home', {
            templateUrl: './views/home.html',
            controller: 'bstCtrl'
        }).
        otherwise({
            redirectTo: '/home'
        });
        
        $locationProvider.html5Mode(true);
        $httpProvider.defaults.withCredentials = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];
}]);

