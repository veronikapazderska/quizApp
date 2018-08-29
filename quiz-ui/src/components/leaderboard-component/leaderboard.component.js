var app = angular.module("app");
app.controller('leaderboardController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService) {
    var self = this;   
    self.leaderboard = [];


    stompService.publish('/app/leaderboardRequest', {}); 

    stompService.subscribe('/topic/leaderboard', function(leaderboardResponseList){
       
        var bodyy = leaderboardResponseList.body;
        console.log(bodyy);
        var leaders = JSON.parse(bodyy)
        self.leaderboard = leaders.leaderboardResponseList;
        $scope.$apply();
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