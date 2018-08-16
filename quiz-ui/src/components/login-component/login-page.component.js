var app = angular.module("app");
app.controller('loginController',['$scope', '$location', 'stompService', function ($scope, $location, stompService) {

    var self = this;
    self.login = function () {
        var user = {};
        user.username = $scope.username;
        user.password = $scope.password;
        stompService.publish('/app/loginRequest', user);
    };
    self.redirectToRegister = function(){
        console.log("Redirect to register");
        $location.path('/register');
    };

    stompService.subscribe("/topic/test", function (message) {
        console.log(message);
    });

}]);