'use strict';

myApp.factory('MovieService', ['$http', '$q', function ($http, $q) {
    return {
        createMovie: function(movie) {
            var promise = $http.post('http://localhost:8080/movie/create', movie);

            promise.success (function(response) {
                return response.data;
            });
            promise.error (function(errResponse) {
                return $q.reject(errResponse);
            });

            return promise;
        },
        deleteMovie: function(name) {
            var promise = $http.post('http://localhost:8080/movie/delete/' + name);

            promise.success (function(response) {
                return response;
            });
            promise.error (function(errResponse) {
                console.error('Error while deleting movie');
                return $q.reject(errResponse);
            });

            return promise;
        },
        updateMovie: function(movieName, movie) {
            var promise = $http.post('http://localhost:8080/movie/update', movie);

            promise.success (function(response) {
                return response;
            });
            promise.error (function(errResponse) {
                console.error('Error while deleting movie');
                return $q.reject(errResponse);
            });

            return promise;
        },
        findAll: function() {
            var promise = $http.get('http://localhost:8080/movie/findAll');

            promise.success (function(response) {
                return response;
            });
            promise.error (function(errResponse) {
                console.error('Error while deleting movie');
                return $q.reject(errResponse);
            });

            return promise;
        },
        findMovie: function(name) {
            var promise = $http.get('http://localhost:8080/movie/find/' + name);

            promise.success (function(response) {
                return response;
            });
            promise.error (function(errResponse) {
                console.error('Error while deleting movie');
                return $q.reject(errResponse);
            });

            return promise;
        }
    };
}]);