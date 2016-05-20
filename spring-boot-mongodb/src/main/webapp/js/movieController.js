'use strict';

myApp.controller('MovieController', ['$scope', '$timeout', '$location', 'MovieService', function($scope, $timeout, $location, MovieService){
    // For create, delete, update, find
    $scope.name='';
    $scope.directors=[];
    $scope.genres=[];
    $scope.nationalities=[];
    $scope.actors=[];
    $scope.picture='';

    // For create, delete and update
    $scope.status='';

    // For update only
    $scope.movies=[];
    $scope.selected='';

    // For find only
    $scope.movie=null;

    $scope.init = function() {
        $scope.name='';
        $scope.directors=[];
        $scope.genres=[];
        $scope.nationalities=[];
        $scope.actors=[];
        $scope.picture='';

        $scope.status='';

        $scope.movies=[];
        $scope.selected='';

        $scope.movie=null;
    }

    $scope.addDirector = function(director) {
       $scope.directors.push(director);
    };

    $scope.addGenre = function(genre) {
       $scope.genres.push(genre);
    };

    $scope.addNationality = function(nationality) {
       $scope.nationalities.push(nationality);
    };

    $scope.addActor = function(actor) {
        $scope.actors.push(actor);
    };

    $scope.createMovie = function(name, directors, genres, nationalities, actors, picture) {
        var movie = {
            name : name,
            directors : directors,
            genres : genres,
            nationalities : nationalities,
            actors : actors,
            picture : picture
        };

        var movieString = JSON.stringify(movie);

        MovieService.createMovie(movieString).then(function(data) {
             $scope.status = data.data;
             $timeout(function() {$scope.status = ''}, 3000);
        });
    };

    $scope.updateMovie = function(name, directors, genres, nationalities, actors) {
       var movie = {
           name : name,
           directors : directors,
           genres : genres,
           nationalities : nationalities,
           actors : actors
           };

       var movieString = JSON.stringify(movie);

       MovieService.updateMovie(movie.name, movieString).then(function(data) {
             $scope.status = data.data;
             $timeout(function() {$scope.status = ''}, 3000);
       });
    };

    $scope.deleteMovie = function(name) {
        MovieService.deleteMovie(name).then(function(data) {
            $scope.status = data.data;
            $timeout(function() {$scope.status = ''}, 3000);
        });
    };

    $scope.findMovie = function(name) {
        MovieService.findMovie(name).then(function(data) {
            $scope.movie = data.data;
            if($scope.movie.name == null) {
                $scope.status = 'Not found';
                $timeout(function() {$scope.status = ''}, 3000);
            }
        });
    };

    $scope.findAll = function() {
        MovieService.findAll().then(function(data) {
            $scope.movies = data.data;
        });
    };

    $scope.initFind = function() {
        var path = $location.absUrl();
        var start = path.indexOf("=");
        var res = path.substring(start+1, path.length);
        MovieService.findMovie(res).then(function(data) {
            $scope.movie = data.data;
            if($scope.movie.name == null) {
                $scope.status = 'Not found';
                $timeout(function() {$scope.status = ''}, 3000);
            }
        });
    }
}]);