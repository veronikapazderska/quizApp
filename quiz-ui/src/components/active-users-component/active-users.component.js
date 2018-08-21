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

    self.redirectToQuiz = function() {
        $location.path('/takeQuiz');
    };

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

    self.inviteUser = function(username) {
        var gameRequest = {};
        gameRequest.sender = $rootScope.loggedUser.username;
        gameRequest.receiver = username;

        stompService.subscribe('/topic/gameResponse/' + gameRequest.sender, function(gameResponse){
            if(gameResponse.hasConfirmed){
                //TODO: navigate to quiz page
            }
            else {
                //TODO: handle negative answer - pop-up or sth else
            }
        });

        stompService.publish("/app/gameRequest", gameRequest);
    };
    

}]);