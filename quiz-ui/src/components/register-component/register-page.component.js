var app = angular.module("app");
app.controller('registerController', ['$scope', '$location', 'stompService', function ($scope, $location, stompService) {

    var self = this;
    self.register = function () {
        var user = {};
        user.username = $scope.username;
        user.password = $scope.password;
        user.firstName = $scope.firstName;
        user.lastName = $scope.lastName;
        user.age = $scope.age;
        stompService.publish('/app/registerRequest', user);
    };

    self.redirectToLogin = function () {
        console.log("Redirect to login");
        $location.path('/');
    };
    stompService.subscribe("/topic/test", function (message) {
        console.log(message);
    });

}]);