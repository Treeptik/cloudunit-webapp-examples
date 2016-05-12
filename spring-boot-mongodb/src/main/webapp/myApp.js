var myApp = angular.module('myApp', []);
myApp.controller('CreateController', ['$scope', '$http', function($scope, $http) {

    $scope.name = '';
    $scope.directors = [];
    $scope.genres = [];
    $scope.nationalities = [];
    $scope.actors = [];

    $scope.create = function($http) {
      return $http.post('movieApi/create', $scope).then(successCallback, errorCallback);
    }

    $scope.addDirector = function() {
        $scope.directors.push($scope.director);
    }

    $scope.addGenre = function() {
        $scope.genres.push($scope.genre);
    }

    $scope.addNationality = function() {
        $scope.nationalities.push($scope.nationality);
    }

    $scope.addActor = function() {
        $scope.actors.push($scope.actor);
    }
}]);