var app = angular.module("app");
app.controller('mainPageController', ['$scope', '$rootScope', '$location', 'stompService',  function ($scope, $rootScope, $location, stompService) {
    var self = this;

    self.sender;

    if(localStorage.getItem('user')){
        $rootScope.loggedUser = JSON.parse(localStorage.getItem('user'));
        stompService.subscribe('/topic/gameRequest/' + $rootScope.loggedUser.username, function(gameRequest){
            self.sender = gameRequest.sender;
            console.log("Here is a request: " + gameRequest);
            //TODO: create pop up notification for a new game, send response
        })
    }
    else {
        $location.path('/');
    }
    self.username = $rootScope.loggedUser.username;
    self.firstName = $rootScope.loggedUser.firstName;
    self.lastName = $rootScope.loggedUser.lastName;

    self.redirectToActiveUsers = function(){
        $location.path('/activeUsers');
    };
    self.redirectToQuiz = function () {
        $location.path('/quizz');
    };



}]);