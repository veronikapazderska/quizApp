var app = angular.module("app");
app.controller('quizController', ['$scope', '$location', 'stompService', function ($scope, $location, stompService) {
    var self = this;
    self.logOut = function () {
       $location.path('/');
    };
    stompService.subscribe("/topic/test", function (message) {
        console.log(message);
    });

}
]);