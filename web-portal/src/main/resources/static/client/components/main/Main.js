var commonModule = angular.module('EProc.Common', []);

commonModule.controller("navController", ["$scope", function($scope) {
    $scope.user = "Marat Karakhanov";
}]);