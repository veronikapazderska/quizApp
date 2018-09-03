var app = angular.module("app");
app.controller('activeUsersController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService) {
    var self = this;
    if(localStorage.getItem('user')){
        $rootScope.loggedUser = JSON.parse(localStorage.getItem('user'));
    }
    console.log("Active user is " + $rootScope.loggedUser.username);
    self.activeUsers = [];

    stompService.subscribe('/topic/activeUsers', function (activeUsers) {
        self.activeUsers = activeUsers.activeUsers;
        $scope.$apply();
    });

    stompService.publish('/app/activeUsersRequest', {});

    stompService.subscribe('/topic/gameRequest/' + $rootScope.loggedUser.username, function (gameRequest) {
        if(gameRequest.body) {
            gameRequest = JSON.parse(gameRequest.body);
        }              
        self.sender = gameRequest.sender;       
        if (gameRequest.receiver == $rootScope.loggedUser.username) {
            self.gameRequest = gameRequest;
            $rootScope.isInvited = true;
            $scope.$apply();
        }
       
    });
  

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

        stompService.subscribe('/topic/gameRefused/' + gameRequest.receiver, function(gameInvitationResponse){
            if(gameRequest.sender == $rootScope.loggedUser.username){
                $rootScope.hasDeclined = true;                
                $scope.$apply();
                setTimeout(function () {
                    $rootScope.hasDeclined = false;
                    $scope.$apply();
                }, 3000); 
            }
        });

        stompService.subscribe(`/topic/gameStarts/${gameRequest.sender}-${gameRequest.receiver}`, function(gameInvitationResponse){
            $rootScope.topic = `${gameRequest.sender}-${gameRequest.receiver}`;
            $location.path('/takeQuiz');
            $scope.$apply();
        });

        stompService.publish("/app/gameRequest", gameRequest);
    };
    

}]);