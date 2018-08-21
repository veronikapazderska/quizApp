var app = angular.module("app");
app.controller('mainPageController', ['$scope', '$rootScope', '$location', '$uibModal', 'stompService',
    function ($scope, $rootScope, $location, $uibModal, stompService, $uibModalInstance, data) {
        var self = this;
        self.isInvited = false;
        self.sender;
        self.username = $rootScope.loggedUser.username;
        self.firstName = $rootScope.loggedUser.firstName;
        self.lastName = $rootScope.loggedUser.lastName;

        if (localStorage.getItem('user')) {
            $rootScope.loggedUser = JSON.parse(localStorage.getItem('user'));
            stompService.subscribe('/topic/gameRequest/' + $rootScope.loggedUser.username, function (gameRequest) {
                self.sender = gameRequest.sender;
                console.log("Here is a request: " + gameRequest.sender + " and " + gameRequest.receiver);
                if (gameRequest.receiver == $rootScope.loggedUser.username) {
                    self.isInvited = true;
                    $scope.$apply();
                }                
            });
        }
        else {
            $location.path('/');
        }

        self.accept = function(){
            //TODO: send positive response
            $location.path('/takeQuiz');
        }

        self.decline = function(){
            //TODO: send negative response
            self.isInvited = false;
        };

        self.redirectToActiveUsers = function () {
            $location.path('/activeUsers');
        };
        self.redirectToQuiz = function () {
            $location.path('/quizz');
        };
    }]);