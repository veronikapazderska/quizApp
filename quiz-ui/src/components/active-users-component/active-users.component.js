var app = angular.module("app");
app.controller('activeUsersController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService) {
    var self = this;
    console.log("Active user is " + $rootScope.loggedUser.username);
    self.activeUsers = [];
    stompService.subscribe('/topic/activeUsers', function (activeUsers) {
        self.activeUsers = activeUsers.activeUsers;
        $scope.$apply();
    });

    stompService.publish('/app/activeUsersRequest', {});

    self.logOut = function () {
        stompService.subscribe("/topic/logOut/" + $rootScope.loggedUser.username, function (logoutResponse) {
            console.log("Local Storage");
            console.log(logoutResponse);
            localStorage.setItem("user", "");
            $location.path('/');
            $scope.$apply();
        });

        stompService.publish("/app/logoutRequest", {username: $rootScope.loggedUser.username});
    };

}]);