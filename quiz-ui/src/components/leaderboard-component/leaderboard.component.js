var app = angular.module("app");
app.controller('leaderboardController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService) {
    var self = this;   

    self.logOut = function () {
        stompService.subscribe("/topic/logOut/" + $rootScope.loggedUser.username, function (logoutResponse) {
            localStorage.setItem("user", "");
            $location.path('/');
            $scope.$apply();
        });

        stompService.publish("/app/logoutRequest", {username: $rootScope.loggedUser.username});
    };   

}]);