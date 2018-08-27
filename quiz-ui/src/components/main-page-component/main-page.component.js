var app = angular.module("app");
app.controller('mainPageController', ['$scope', '$rootScope', '$location', '$uibModal', 'stompService',
    function ($scope, $rootScope, $location, $uibModal, stompService, $uibModalInstance, data) {
        var self = this;
        $rootScope.isInvited = false;
        self.sender;
        self.username = null;
        self.firstName = null;
        self.lastName = null;
        self.gameRequest = null;

        if (localStorage.getItem('user')) {
            $rootScope.loggedUser = JSON.parse(localStorage.getItem('user'));
            self.username = $rootScope.loggedUser.username;
            self.firstName = $rootScope.loggedUser.firstName;
            self.lastName = $rootScope.loggedUser.lastName;
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
        }
        else {
            $location.path('/');
        }

        self.redirectToActiveUsers = function () {
            $location.path('/activeUsers');
        };

        self.redirectToQuiz = function () {
            $location.path('/quizz');
        };


        
    self.logOut = function () {
        stompService.subscribe("/topic/logOut/" + $rootScope.loggedUser.username, function (logoutResponse) {
            localStorage.setItem("user", "");
            $location.path('/');
            $scope.$apply();
        });

        stompService.publish("/app/logoutRequest", {username: $rootScope.loggedUser.username});
    };

    }]);