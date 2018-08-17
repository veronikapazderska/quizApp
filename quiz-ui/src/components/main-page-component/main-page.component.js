var app = angular.module("app");
app.controller('mainPageController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService) {
    var self = this;
    self.username = $rootScope.loggedUser.username;
    self.firstName = $rootScope.loggedUser.firstName;
    self.lastName = $rootScope.loggedUser.lastName;
}]);