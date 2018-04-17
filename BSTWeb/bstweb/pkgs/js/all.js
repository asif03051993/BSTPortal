bstApp.controller('bstCtrl', function ($scope, $rootScope, $window, $location, $sce, bstService, toaster) {
    
    $scope.init = function() {
    	$scope.printBinaryTree();
    }

    $scope.printBinaryTree = function() {
        var promise = bstService.printBinaryTree();
        promise.success(function (response) {
            $scope.binaryTree = $sce.trustAsHtml(response);
            console.log(response);
        });
        promise.error(function (err) {
            toaster.pop('error', "Print Binary Tree", "Service Failed");
        });
    }

    $scope.getStats = function() {
    	var promise = bstService.getStatsOfBinaryTree();
        promise.success(function (response) {
        	console.log(response);
            $scope.stat = response;
        });
        promise.error(function (err) {
            toaster.pop('error', "Stats", "Failed to get stats");
        });
    }

    $scope.clearInsert = function() {
    	$scope.dataToInsert = "";
    }

    $scope.clearRemove = function() {
    	$scope.dataToRemove = "";
    }

    $scope.clearSearch = function() {
    	$scope.dataToSearch = "";
    	$scope.resetPositions();
    }

    $scope.resetPositions = function() {
    	$scope.positions = null;
    }

    $scope.insertNode = function(data) {
    	$scope.dataToInsert = data;
    	var promise = bstService.insertNode($scope.dataToInsert);
        promise.success(function (response) {
            console.log(response);
            toaster.pop('success', 'Success', 'Successfully inserted data '+ $scope.dataToInsert + ' !');
        });
        promise.error(function (err, status) {
        	if (status == 409) {
            	toaster.pop('info', "Duplicate Node", "Node already exists with data "+ $scope.dataToInsert + ' !');
            } else {
            	toaster.pop('error', "Insert Node", "Failed to insert node");
           	}
        });
    }

    $scope.removeNode = function(data) {
    	$scope.dataToRemove = data;
    	var promise = bstService.removeNode($scope.dataToRemove);
        promise.success(function (response) {
            console.log(response);
            toaster.pop('success', 'Success', 'Successfully removed data '+ $scope.dataToRemove + '!');
        });
        promise.error(function (err, status) {
        	if (status == 409) {
            	toaster.pop('info', "Operation not allowed", "No such node exists with data "+ $scope.dataToRemove + ' !');
            } else {
            	toaster.pop('error', "Remove Node", "Failed to remove node");
            }
        });
    }

    $scope.searchNode = function(data) {
    	$scope.dataToSearch = data;
    	var promise = bstService.searchNode($scope.dataToSearch);
        promise.success(function (response) {
            console.log(response);
            $scope.positions = response;
            toaster.pop('success', 'Found', 'Successfully found data '+ $scope.dataToSearch + ' !');
        });
        promise.error(function (err, status) {
        	$scope.resetPositions();
        	if (status == 409) {
            	toaster.pop('info', "Not Found", "No such node exists with data "+ $scope.dataToSearch + ' !');
            } else {
            	toaster.pop('error', "Search Node", "Failed to search node");
        	}
        });
    }

});