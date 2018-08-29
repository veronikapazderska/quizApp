var app = angular.module("app");
app.controller('leaderboardController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService) {
    if(localStorage.getItem('user')){
        $rootScope.loggedUser = JSON.parse(localStorage.getItem('user'));
    }
    var self = this;
    self.leaderboard = null;    

    stompService.subscribe('/topic/leaderboard', function(leaderboardResponseList){
       self.leaderboard = leaderboardResponseList.leaderboardResponseList;       
       $scope.$apply();
    });

    stompService.publish('/app/leaderboardRequest', {}); 
      
    stompService.subscribe('/topic/gameRequest/' + $rootScope.loggedUser.username, function (gameRequest) {
        if(gameRequest.body) {
            gameRequest = JSON.parse(gameRequest.body);
        }              
        self.sender = gameRequest.sender;
        //console.log("Here is a request: " + gameRequest.sender + " and " + gameRequest.receiver);
        if (gameRequest.receiver == $rootScope.loggedUser.username) {
            self.gameRequest = gameRequest;
            $rootScope.isInvited = true;
            $scope.$apply();
        }       
    });


    self.logOut = function () {
        stompService.subscribe("/topic/logOut/" + $rootScope.loggedUser.username, function (logoutResponse) {
            localStorage.setItem("user", "");
            $location.path('/');
            $scope.$apply();
        });

        stompService.publish("/app/logoutRequest", {username: $rootScope.loggedUser.username});
    };


}]);