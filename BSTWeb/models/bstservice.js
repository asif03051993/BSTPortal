var bstAppServices = angular.module('BSTServices',[]);

bstAppServices.service("bstService",['$http', function($http){
    
    this.printBinaryTree= function(){
        var serviceURL = config.apiUrl+"/view"; 
        return  $http.get(serviceURL);
    }
    
    this.getStatsOfBinaryTree = function(){
        var serviceURL = config.apiUrl+"/stats"; 
		return  $http.get(serviceURL);
    }

    this.insertNode = function(data){
        var serviceURL = config.apiUrl+"/add/node"; 
        return  $http.post(serviceURL, {"data":data});
    }

    this.removeNode = function(data){
        var serviceURL = config.apiUrl+"/delete/node/"+data; 
        return  $http.delete(serviceURL);
    }

    this.searchNode = function(data){
        var serviceURL = config.apiUrl+"/find/node"; 
        return  $http.post(serviceURL, {"data":data});
    }
}]);

