var app = angular.module("app");
app.controller('mainPageController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService) {
    var self = this;
    self.username = $rootScope.registeredUser.username;
    self.firstName = $rootScope.registeredUser.firstName;
    self.lastName = $rootScope.registeredUser.lastName;
}]);