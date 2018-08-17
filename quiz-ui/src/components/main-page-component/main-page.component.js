var app = angular.module("app");
app.controller('mainPageController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService) {
    var self = this;
    if(localStorage.getItem('user')){
        $rootScope.loggedUser = JSON.parse(localStorage.getItem('user'));
    }
    self.username = $rootScope.loggedUser.username;
    self.firstName = $rootScope.loggedUser.firstName;
    self.lastName = $rootScope.loggedUser.lastName;

    self.redirectToActiveUsers = function(){
        $location.path('/activeUsers');
    };

}]);