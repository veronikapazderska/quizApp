var app = angular.module("app");
app.controller('registerController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService) {

    var self = this;
    self.errorMessage;
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


    self.registrationSucceeded = function () {
        stompService.subscribe("/topic/regSuccess/" + $scope.username, function (registerSuccessful) {
            $rootScope.loggedUser = registerSuccessful;
            localStorage.setItem("user", JSON.stringify(registerSuccessful))
            $location.path('/main');
            $scope.$apply();
        });
    };

    self.registrationFailed = function() {
        console.log("Metoda se vika");
        stompService.subscribe("/topic/regFailed/" + $scope.username, function (registerFailed) {
            self.errorMessage = registerFailed.message;
            $scope.$apply();
        });
    };

    self.redirectToLogin = function () {
        $location.path('/');
    };
}]);