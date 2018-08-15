var app = angular.module("app");
app.controller('loginController',['$scope', 'stompService', function ($scope, stompService) {

    var self = this;
    self.login = function () {
        var user = {};
        user.username = $scope.username;
        user.password = $scope.password;
        stompService.publish('/app/loginRequest', user);
    };

    stompService.subscribe("/topic/test", function (message) {
        console.log(message);
    });

}]);