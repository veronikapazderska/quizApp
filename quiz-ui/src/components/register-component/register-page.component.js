var app = angular.module("app");
app.controller('registerController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService) {

    var self = this;
    //var errorMessage;
    self.register = function () {
        var user = {};
        user.username = $scope.username;
        user.password = $scope.password;
        user.firstName = $scope.firstName;
        user.lastName = $scope.lastName;
        user.age = $scope.age;

        self.registrationSucceeded();
        self.registrationFailed();
        stompService.publish('/app/registerRequest', user);
    };

    self.registrationFailed = function() {
        stompService.subscribe("/topic/regFailed/" + $scope.username, function (registerFailed) {
            $scope.errorMessage = registerFailed.message;
            $scope.$apply();
        });
    }

    self.registrationSucceeded = function () {
        stompService.subscribe("/topic/regSuccess/" + $scope.username, function (registerSuccessful) {
            $rootScope.registeredUser = registerSuccessful;
            $location.path('/main');
            $scope.$apply();
        });
    };

    self.redirectToLogin = function () {
        $location.path('/');
    };
}]);