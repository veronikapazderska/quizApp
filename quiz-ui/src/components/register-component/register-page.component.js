var app = angular.module("app");
app.controller('registerController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService) {

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

    self.registrationSucceeded = function() {
        stompService.subscribe("/topic/regSuccess/" + $scope.username, function (registerSuccessful) {
            $rootScope.registeredUser = registerSuccessful;
            $location.path('/mainPage');
        });
    }

    self.redirectToLogin = function () {
        $location.path('/');
    };
    stompService.subscribe("/topic/test", function (message) {
        console.log(message);
    });

}]);