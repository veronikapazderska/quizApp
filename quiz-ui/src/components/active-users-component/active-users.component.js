var app = angular.module("app");
app.controller('activeUsersController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService) {
    var self = this;
    console.log("Active user is " + $rootScope.loggedUser.username);
    self.activeUsers = [];
    stompService.subscribe('/topic/activeUsers', function (activeUsers) {
        self.activeUsers = activeUsers.activeUsers;
        $scope.$apply();
    });

    stompService.subscribe('/topic/gameRequest/' + $rootScope.loggedUser.username, function (gameRequest) {
        if(gameRequest.body) {
            gameRequest = JSON.parse(gameRequest.body);
        }              
        self.sender = gameRequest.sender;
        console.log("Here is a request: " + gameRequest.sender + " and " + gameRequest.receiver);
        if (gameRequest.receiver == $rootScope.loggedUser.username) {
            self.gameRequest = gameRequest;
            $rootScope.isInvited = true;
            $scope.$apply();
        }
       if(gameRequest.sender == $rootScope.loggedUser.username){
            $rootScope.hasDeclined = true;
        } 
    });


    stompService.publish('/app/activeUsersRequest', {});

    self.redirectToQuiz = function() {
        $location.path('/takeQuiz');
    };

    self.logOut = function () {
        stompService.subscribe("/topic/logOut/" + $rootScope.loggedUser.username, function (logoutResponse) {
            localStorage.setItem("user", "");
            $location.path('/');
            $scope.$apply();
        });

        stompService.publish("/app/logoutRequest", {username: $rootScope.loggedUser.username});
    };

    self.inviteUser = function(username) {
        var gameRequest = {
            "sender": $rootScope.loggedUser.username,
            "receiver": username
        };

        stompService.subscribe('/topic/gameResponse/' + gameRequest.sender, function(gameResponse){
            //TODO: Handle negative response
        });

        stompService.subscribe(`/topic/gameStarts/${gameRequest.sender}-${gameRequest.receiver}`, function(gameInvitationResponse){
            $rootScope.topic = `${gameRequest.sender}-${gameRequest.receiver}`;
            $location.path('/takeQuiz');
            $scope.$apply();
        });

        stompService.publish("/app/gameRequest", gameRequest);
    };
    

}]);