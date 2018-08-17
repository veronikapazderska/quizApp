var app = angular.module("app");
app.controller('loginController',['$scope', '$rootScope','$location', 'stompService', function ($scope, $rootScope, $location, stompService) {

    var self = this;
    self.login = function () {
        var user = {};
        user.username = $scope.username;
        user.password = $scope.password;

        self.loginSucceeded();
        self.loginFailed();

        stompService.publish('/app/loginRequest', user);
    };

    self.loginSucceeded = function () {
        stompService.subscribe("/topic/logSuccess/" + $scope.username, function (loginSuccess) {
            $rootScope.loggedUser = loginSuccess;
            $location.path('/main');
            $scope.$apply();
        })
    };

    self.loginFailed = function () {
        stompService.subscribe("/topic/logFailed/" + $scope.username, function (loginFailed) {
            console.log(loginFailed);
            self.errorMessage = loginFailed.message;
            $scope.$apply();
        });
    };


    self.redirectToRegister = function(){
        $location.path('/register');
    };

    stompService.subscribe("/topic/test", function (message) {
        console.log(message);
    });

}]);